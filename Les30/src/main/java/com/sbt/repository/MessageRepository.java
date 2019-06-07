package com.sbt.repository;

import com.sbt.model.Message;

import java.time.LocalDateTime;
import java.util.Map;

public interface MessageRepository {

	void putMessage(Message message);
	
	Map<LocalDateTime, Message> getAllMessages();
}
