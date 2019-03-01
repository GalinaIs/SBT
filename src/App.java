import com.sbt.javaschool.lesson2.Person;

public class App {
    public static void main(String[] args) {
        Person person = new Person(true, "Tom");
        Person person1 = new Person(false, "Mary");

        if (person.marry(person1)) {
            System.out.print(person.infoAboutSpouse());
            System.out.print(person1.infoAboutSpouse());
        }

        Person person2 = new Person(true, "Tomi");
        if (!person2.marry(person)) {
            System.out.println("People don't married");
            System.out.print(person2.infoAboutSpouse());
            System.out.print(person.infoAboutSpouse());
        }

        Person person3 = new Person(false, "Ann");

        person3.marry(person2);
        System.out.print(person2.infoAboutSpouse());
        System.out.print(person3.infoAboutSpouse());

        person3.marry(person);
        System.out.print(person.infoAboutSpouse());
        System.out.print(person1.infoAboutSpouse());
        System.out.print(person2.infoAboutSpouse());
        System.out.print(person3.infoAboutSpouse());
    }
}
