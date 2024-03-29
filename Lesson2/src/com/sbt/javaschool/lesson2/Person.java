package com.sbt.javaschool.lesson2;

public class Person {
    private final boolean man;
    private final String name;
    private Person spouse;

    public Person(boolean man, String name) {
        this.man = man;
        this.name = name;
    }

    public boolean isMan() {
        return this.man;
    }

    public String getName() {
        return this.name;
    }

    public Person getSpouse() {
        return this.spouse;
    }

    public void setSpouse(Person person) {
        this.spouse = person;
    }

    /**
     * This method checks gender of persons. If genders are not equal - tries to marry.
     * If one of them has another spouse - execute divorce(sets spouse = null for husband and wife. Example: if both persons have spouses - then divorce will set 4 spouse to null) and then executes marry().
     *
     * @param person - new husband/wife for this person.
     * @return - returns true if this person has another gender than passed person and they are not husband and wife, false otherwise
     */
    public boolean marry(Person person) {
        if (person == null || person.isMan() == this.man || this.spouse == person)
            return false;

        person.divorce();
        this.divorce();
        this.spouse = person;
        person.setSpouse(this);
        return true;
    }

    /**
     * Sets spouse = null if spouse is not null
     *
     * @return true - if person status has been changed
     */
    public boolean divorce() {
        if (this.spouse == null)
            return false;

        this.spouse.setSpouse(null);
        this.spouse = null;
        return true;
    }

    public String infoAboutSpouse() {
        if (this.spouse != null) {
            return String.format("Person %s has the spouse: %s \n", this.name, this.spouse.getName());
        }
        return String.format("Person %s doesn't have any spouse\n", this.name);
    }
}