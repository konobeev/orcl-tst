package com.example.databasedemo.rest.dto;

public class AccountDto {
    private String name;
    private String number;
    private String currency;
    private String loco;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLoco() {
        return loco;
    }

    public void setLoco(String loco) {
        this.loco = loco;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
