package ru.tinkoff.telegrambot.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.tinkoff.telegrambot.entity.ChatData;
import ru.tinkoff.telegrambot.repository.ChatDataRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatDataService {

  private final ChatDataRepository chatDataRepository;

  public ChatData addChatDataIfNotPresent(Message message) {
    Long chatId = Optional.ofNullable(message).map(Message::getChatId)
        .orElseThrow(() -> new RuntimeException("can not get chatId"));
    ChatData chatData = chatDataRepository.findByChatId(chatId).orElse(null);
    if(chatData != null) {
      return chatData;
    }
    chatData = new ChatData();
    chatData.setChatId(chatId);
    return chatDataRepository.save(chatData);
  }

  public List<ChatData> getAllCahtData() {
    return chatDataRepository.findAll();
  }

}
