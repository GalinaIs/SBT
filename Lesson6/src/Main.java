import com.sbt.javaschool.lesson6.*;
import com.sbt.javaschool.lesson6.classTest.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //testReflection();
        testProxy();
        //testBeanUtils();
        String s = "" + false;
        System.out.println(s);
    }

    private static void testReflection() {
        Reflection reflection = new Reflection(Person.class);
        Reflection reflection1 = new Reflection(new Person());
        System.out.println(reflection.getAllMethods());
        System.out.print(reflection1.getAllGetters());
        System.out.println(reflection.checkAllConstants());
    }

    private static void testProxy() {
        Person person = new Person();
        IPerson personProxy = (IPerson) Proxy.newProxyInstance(Person.class.getClassLoader(),
                Person.class.getInterfaces(), new CacheProxy(person));

        personProxy.setName("Anna");
        personProxy.getName();
        personProxy.setName("Tom");
        personProxy.getName();//опять получем Anna.
    }

    private static void testBeanUtils() {
        Person person1 = new Person();
        Person person2 = new Person("Anna", 25, false);
        /*System.out.println("Person1\n" + person1);
        System.out.println("Person2\n" + person2);*/
        BeanUtils.assign(person1, person2);
        /*System.out.println("Person1\n" + person1);
        System.out.println("Person2\n" + person2);*/

        /*Emlpoyee emlpoyee = new Emlpoyee();
        System.out.println("Employee\n" + emlpoyee);
        System.out.println("Person2\n" + person2);
        BeanUtils.assign(emlpoyee, person2);
        System.out.println("Employee\n" + emlpoyee);
        System.out.println("Person2\n" + person2);*/

        /*Test test = new Test();
        System.out.println("Test\n" + test);
        System.out.println("Person2\n" + person2);
        BeanUtils.assign(person2, test);
        System.out.println("Test\n" + test);
        System.out.println("Person2\n" + person2);*/

        EmployeeTest emlpoyeeTest = new EmployeeTest(new Emlpoyee("Anna", 15, false, 0));
        PersonTest personTest = new PersonTest();
        System.out.println("EmployeeTest\n" + emlpoyeeTest);
        System.out.println("PersonTest\n" + personTest);
        BeanUtils.assign(personTest, emlpoyeeTest);//все ОК
        //BeanUtils.assign(emlpoyeeTest, personTest);//невозможно записать данные из Person в Employee - не достаточно данных
        System.out.println("EmployeeTest\n" + emlpoyeeTest);
        System.out.println("PersonTest\n" + personTest);
    }
}



