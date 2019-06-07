package com.sbt.service;

import com.sbt.model.Message;

import java.time.LocalDateTime;
import java.util.Map;

public interface MessageService {
	void sendMessage(Message message);
	
	Map<LocalDateTime, Message> getAllMessages();
}
