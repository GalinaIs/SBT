package ru.sbt.Lesson8.socialNetwork.content;

import java.util.List;

public interface ActionContent<T extends Content> {
    boolean addContent(T content);
    boolean deleteContent(T content);
    List<T> getAllPhoto();
}