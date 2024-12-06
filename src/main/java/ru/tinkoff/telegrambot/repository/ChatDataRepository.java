package ru.tinkoff.telegrambot.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.ListCrudRepository;
import ru.tinkoff.telegrambot.entity.ChatData;

public interface ChatDataRepository extends ListCrudRepository<ChatData, UUID> {

  Optional<ChatData> findByChatId(long chatId);
}
