package com.sbt.service;

import com.sbt.messaging.MessageSender;
import com.sbt.model.Message;
import com.sbt.repository.MessageRepository;
import com.sbt.util.BasicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service("orderService")
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageSender messageSender;
	
	@Autowired
	MessageRepository messageRepository;

	@Override
	public void sendMessage(Message message) {
		message.setMessageId(BasicUtil.getUniqueId());
		messageRepository.putMessage(message);
		messageSender.sendMessage(message);
	}

	@Override
	public Map<LocalDateTime, Message> getAllMessages() {
		return messageRepository.getAllMessages();
	}
}
