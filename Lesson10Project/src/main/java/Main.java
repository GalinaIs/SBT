import myStream.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        testString();
        //testPerson();
    }

    private static void testString() {
        List<String> stringList = new ArrayList<>();
        stringList.add("1234567890");
        stringList.add("22222222222222");
        stringList.add("3");
        stringList.add(0, "5");

        MyStream<String> stream1 = MyStream.of(stringList).filter(string -> string.length() >= 10);
        stream1.forEach(System.out::println);

        MyStream<Integer> stream2 = MyStream.of(stringList).transform(String::length);
        stream2.forEach(System.out::println);

        Map<Integer, String> stringMap = MyStream.of(stringList).toMap(String::length, String::toString);
        System.out.println(stringMap.toString());
    }

    private static void testPerson() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person());
        personList.add(new Person("Anna", 25));
        personList.add(new Person("Ivan", 45));
        personList.add(0, new Person("Sergey", 20));

        MyStream<Person> stream1 = MyStream.of(personList).filter(person -> person.getAge() >= 25);
        stream1.forEach(System.out::println);

        MyStream<String> stream2 = MyStream.of(personList).transform(Person::getName);
        stream2.forEach(System.out::println);

        Map<String, Integer> personMap = MyStream.of(personList).toMap(Person::getName, Person::getAge);
        System.out.println(personMap.toString());
    }
}
