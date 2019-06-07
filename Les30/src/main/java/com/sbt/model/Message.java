package com.sbt.model;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
	private String messageId;
	private User from;
	private String message;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message1 = (Message) o;
		return Objects.equals(messageId, message1.messageId) &&
				Objects.equals(from, message1.from) &&
				Objects.equals(message, message1.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(messageId, from, message);
	}

	@Override
	public String toString() {
		return "Message{" +
				"messageId='" + messageId + '\'' +
				", from=" + from +
				", message='" + message + '\'' +
				'}';
	}
}