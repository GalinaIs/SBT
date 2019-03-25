package ru.sbt.Lesson8.socialNetwork.content.photo;

import ru.sbt.Lesson8.socialNetwork.content.ActionContent;

import java.util.*;

public class ActionUserPhoto implements ActionContent<Photo> {
    private final List<Photo> photosUser;
    private final long idUser;

    public ActionUserPhoto(long idUser) {
        this.idUser = idUser;
        photosUser = new ArrayList<>();
    }

    @Override
    public boolean addContent(Photo photo) {
        if (photosUser.contains(photo)) return false;

        return photosUser.add(photo);
    }

    @Override
    public boolean deleteContent(Photo photo) {
        return photosUser.remove(photo);
    }

    @Override
    public List<Photo> getAllPhoto() {
        return photosUser;
    }
}