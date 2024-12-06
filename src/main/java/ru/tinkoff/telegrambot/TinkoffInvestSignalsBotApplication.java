package ru.tinkoff.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TinkoffInvestSignalsBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinkoffInvestSignalsBotApplication.class, args);
    }

}
