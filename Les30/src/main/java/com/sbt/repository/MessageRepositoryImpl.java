package com.sbt.repository;

import com.sbt.model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

@Service("messageRepository")
public class MessageRepositoryImpl implements MessageRepository {
	private static final Map<LocalDateTime, Message> messages = new ConcurrentSkipListMap<>();

	@Override
	public void putMessage(Message message) {
		messages.put(LocalDateTime.now(), message);
	}

	@Override
	public Map<LocalDateTime, Message> getAllMessages() {
		return messages;
	}
}
