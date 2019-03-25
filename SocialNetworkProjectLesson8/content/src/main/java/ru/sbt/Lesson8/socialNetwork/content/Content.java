package ru.sbt.Lesson8.socialNetwork.content;

import java.io.File;

public abstract class Content {
    private static long count = 0;
    private final long idContent;
    private final long idUser;
    private String name;
    private final File fileContent;

    public Content(long idUser, String name, File fileContent) {
        count++;
        idContent = count;
        this.idUser = idUser;
        this.name = name;
        this.fileContent = fileContent;
    }

    public long getIdContent() {
        return idContent;
    }

    public long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public File getFileContent() {
        return fileContent;
    }

    public void setName(String name) {
        this.name = name;
    }
}
