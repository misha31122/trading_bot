package ru.tinkoff.telegrambot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tinkoff.telegrambot.entity.ChatData;
import ru.tinkoff.telegrambot.properties.BotProperties;
import ru.tinkoff.trade.telegrambot.dto.TradingSignalDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingTelegramBot extends TelegramLongPollingBot {

  private final BotProperties botProperties;
  private final ChatDataService chatDataService;
  private final ObjectMapper objectMapper;

  @Override
  public String getBotUsername() {
    return botProperties.getName();
  }

  @Override
  public String getBotToken() {
    return botProperties.getToken();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String messageText = update.getMessage().getText();
      String userName = Optional.ofNullable(update.getMessage().getChat())
          .map(Chat::getUserName)
          .orElseThrow(() -> new RuntimeException("can not get userName"));
      ChatData chatData = chatDataService
          .addChatDataIfNotPresent(update.getMessage());

      if ("/start".equals(messageText)) {
        startCommandReceived(chatData.getChatId(),userName);
      }
    }
  }

  public void sendSignal(TradingSignalDto signalDto) {
    Optional.of(chatDataService.getAllCahtData())
        .filter(CollectionUtils::isNotEmpty)
        .ifPresent(chatDatas -> chatDatas
            .forEach(chat -> {
              try {
                sendMessage(chat.getChatId(), objectMapper.writeValueAsString(signalDto));
              } catch (JsonProcessingException e) {
                log.error("Can not map object to string", e);
                throw new RuntimeException(e);
              }
            }));
  }

  private void startCommandReceived(Long chatId, String name) {
    String answer = "Привет, " + name + ", рад тебя видеть!" + "\n" +
        "Сейчас ты начьнешь получать сигналы для торговли" + "\n" +
        "Получая сигналы, проверь их корректность в терминале, глядя на OBV, RSI, MACD, Fibonacci" + "\n" +
        " и читай новости по компании, оценивая возможность для ее покупки";
    sendMessage(chatId, answer);
  }

  public void sendMessage(Long chatId, String textToSend) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chatId));
    sendMessage.setText(textToSend);
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      log.error("Can not send massage", e);
    }
  }
}
