package ru.sbt.Lesson8.socialNetwork.profile;

public class UserProfile {
    private static long count = 0;
    private final long idUser;
    private String name;
    private int age;
    private String nickName;
    private City city;

    public UserProfile(String name, int age, String nickName, City city) {
        count++;
        idUser = count;
        if (age <= 0 || age >= 100)
            throw new IllegalArgumentException("Age must be more than 0 and less than 100. Age: " + age);
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Users name can't be empty. Name: " + name);

        this.name = name;
        this.age = age;
        this.nickName = nickName;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals(""))
            throw new IllegalArgumentException("Users name can't be empty. Name: " + name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setAge(int age) {
        if (age <= 0 || age >= 100)
            throw new IllegalArgumentException("Age must be more than 0 and less than 100. Age: " + age);
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "idUser=" + idUser +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                ", city=" + city +
                '}';
    }
}

