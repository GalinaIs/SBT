package ru.sbt.Lesson8.socialNetwork.content.photo;

import ru.sbt.Lesson8.socialNetwork.content.*;
import java.io.File;

public class Photo extends Content {
    public Photo(long idUser, String name, File filePhoto) {
        super(idUser, name, filePhoto);
    }
}