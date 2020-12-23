package com.example.databasedemo.rest;

import com.example.databasedemo.models.Account;
import com.example.databasedemo.rest.dto.AccountDto;
import com.example.databasedemo.service.StaticDataService;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Objects;

@RestController
public class StaticDataController {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(StaticDataController.class);
    private final StaticDataService staticDataService;

    public StaticDataController(StaticDataService staticDataService) {
        this.staticDataService = Objects.requireNonNull(staticDataService);
    }


    @RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    private Flux<Account> getAllAccounts() {
        logger.info("getAllAccount");
        return Flux.fromIterable(staticDataService.getAccounts());
    }

    @RequestMapping(value = {"/create", "/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AccountDto dto) {
        logger.info(dto.toString());
        Account acc = new Account();
        acc.setAccountName(dto.getName());
        acc.setAccountNumber(dto.getNumber());
        acc.setCurrency(dto.getCurrency());
        acc.setAssetLocation(dto.getLoco());
        acc.setNote(dto.getNote());
        staticDataService.addAccount(acc);
    }
}
