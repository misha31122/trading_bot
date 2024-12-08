package ru.tinkoff.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.telegrambot.service.TradingTelegramBot;
import ru.tinkoff.trade.dto.TradingSignalDto;

@RestController
@RequestMapping(value = "/telegram-bot")
@RequiredArgsConstructor
public class TelegramBotHelpController {

  private final TradingTelegramBot tradingTelegramBot;


  @Operation(
      summary = "Получение сигнала на покупку в лонг или продажу в шорт",
      description = "signal")
  @PostMapping(value = "/signal", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> initSignal(@RequestBody TradingSignalDto tradingSignalDto) {
    tradingTelegramBot.sendSignal(tradingSignalDto);
    return ResponseEntity.ok("Сигнал отправлен");
  }

}
