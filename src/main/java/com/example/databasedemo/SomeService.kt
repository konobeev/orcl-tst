package com.example.databasedemo

import com.example.databasedemo.config.ApplicationProperties
import org.springframework.stereotype.Service
import java.time.LocalTime
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class SomeService(private val applicationProperties: ApplicationProperties) {
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    @PostConstruct
    fun postConstruct(){
        scheduler.schedule(this::doWork,1000, TimeUnit.MILLISECONDS)
    }

    private fun doWork() {
        println("working ${LocalTime.now()}")
        println(applicationProperties)
        // simulate long work
        Thread.sleep(5000)
        when (scheduler.isShutdown) {
            false -> scheduler.schedule(this::doWork, 1000, TimeUnit.MILLISECONDS)
            else -> println("final work")
        }
    }

    @PreDestroy
    fun dispose(){
        scheduler.shutdown()
    }
}