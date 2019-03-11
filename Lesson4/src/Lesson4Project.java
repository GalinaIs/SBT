import com.sbt.javaschool.lesson4.*;

import java.util.*;

public class Lesson4Project {
    public static void main(String[] args) {
        //testCountMap();
        //testCollections();
        testMyLinkedList();
    }

    private static void testCountMap() {
        CountMap<String> words = new MyCountMap<>();
        words.add("123");
        words.add("123");
        words.add("123");
        System.out.printf("Size CountMap %d \n", words.size());
        System.out.printf("Count '123' %d before remove \n", words.remove("123"));
        System.out.printf("Count '123' %d \n", words.getCount("123"));

        Map<String, Integer> hashMap = words.toMap();
        hashMap.put("123", 5);
        words.addMap(hashMap);
        System.out.println(words.toString());

        HashMap<String, Integer> map = new HashMap<>();
        map.put("5", 2);
        CountMap<String> newWords = new MyCountMap<>(map);
        words.addAll(newWords);
        System.out.println(words.toString());
        map.put("3", 5);

        System.out.println("To map + add all:");
        newWords.toMap(map);
        CountMap<String> myWords = new MyCountMap<>(map);
        System.out.println(myWords.toString());
        words.addAll(myWords);
        System.out.println(words.toString());
    }

    private static void testMyLinkedList() {
        MyLinkedList<String> myList = new MyLinkedList<>();
        myList.add("Java");
        myList.add("PHP");
        myList.add("Pascal");
        myList.add(1, "C++");
        myList.add(0, "JS");
        System.out.println(myList.toString());
        System.out.println("Element of 2 index is " + myList.get(2));
        myList.remove(1);
        myList.remove("JS");
        System.out.println(myList.remove(new Employee("Anna", 50000)));
        System.out.println(myList.toString());

        ArrayList<String> list = new ArrayList<>();
        list.add("Scala");
        myList.addAll(list);
        System.out.println("Collection after add");
        System.out.println(myList.toString());
        myList.copy(list);
        System.out.println("Collection after copy");
        System.out.println(myList.toString());
    }

    private static void testCollections() {
        Person person = new Person("Tom");
        Person person2 = new Person("Inna");
        Employee employee = new Employee("Nick", 50000);
        Manager manager = new Manager("Ivan", 75000, 50000);

        List<Person> listPerson = CollectionUtils.newArrayList();
        List<Employee> listEmployee;
        List<Manager> listManager = CollectionUtils.newArrayList();
        Manager managerAnna = new Manager("Anna", 50000, 500000);
        listManager.add(managerAnna);

        CollectionUtils.add(listPerson, employee);
        System.out.println(listPerson);

        CollectionUtils.addAll(listManager, listPerson);
        System.out.println(listPerson);

        System.out.println(CollectionUtils.indexOf(listPerson, employee));

        listEmployee = CollectionUtils.limit(listManager, 1);
        System.out.println(listEmployee);

        /*CollectionUtils.removeAll(listPerson, listManager);
        System.out.println(listPerson);*/

        System.out.println(CollectionUtils.containsAll(listPerson, listManager));
        System.out.println(CollectionUtils.containsAny(listPerson, listManager));

        List<Person> newPersonList = CollectionUtils.range(listPerson, person2, person);
        System.out.println(newPersonList);

        listEmployee.add(new Employee("Anna", 100000));
        listEmployee.add(new Employee("Anna", 70000));
        System.out.println(listEmployee);
        List<Employee> newEmployeeList = CollectionUtils.range(listEmployee, new Employee("Anna", 60000),
                new Employee("Anna", 120000), Comparator.comparing(Employee::getSalary));
        System.out.println(newEmployeeList);
    }
}

class Person implements Comparable<Person> {
    private String name;

    Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Person o) {
        return name.compareTo(o.getName());
    }
}

class Employee extends Person {
    private int salary;

    Employee(String name, int salary) {
        super(name);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "salary=" + salary +
                '}';
    }
}

class Manager extends Employee {
    private int bonus;

    Manager(String name, int salary, int bonus) {
        super(name, salary);
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "bonus=" + bonus +
                '}';
    }
}
