package ru.sbt.Lesson8.socialNetwork.profile;

import org.junit.Test;

public class UserProfileTest {
    private UserProfile userProfile;

    @Test
    public void testAllSuitableParameters() {
        userProfile = new UserProfile("John", 28, "john",City.Moscow);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgeNotSuitable() {
        userProfile = new UserProfile("John", 0, "john",City.Moscow);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameNotSuitable() {
        userProfile = new UserProfile("", 2, "john",City.Moscow);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeAge() {
        userProfile = new UserProfile("John", 28, "john",City.Moscow);
        userProfile.setAge(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeName() {
        userProfile = new UserProfile("John", 28, "john",City.Moscow);
        userProfile.setName(null);
    }
}