package ru.sbt.Lesson8.socialNetwork.profile;

public enum City {
    Moscow("Москва"), Rostov("Ростов-на-Дону"), Petersburg("Санкт-Петербург");

    private String name;

    public String getName(){
        return this.name;
    }

    City(String action){
        this.name = action;
    }
}