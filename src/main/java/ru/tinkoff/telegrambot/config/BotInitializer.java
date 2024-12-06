package ru.tinkoff.telegrambot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.tinkoff.telegrambot.service.TradingTelegramBot;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BotInitializer {
  private final TradingTelegramBot tradingTelegramBot;

  @EventListener({ContextRefreshedEvent.class})
  public void init()throws TelegramApiException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    try{
      telegramBotsApi.registerBot(tradingTelegramBot);
    } catch (TelegramApiException e){
      log.error("Can not initialize telegramBotsApi", e);
    }
  }
}
