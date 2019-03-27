import com.sbt.javaschool.Lesson9.*;
import com.sbt.javaschool.Lesson9.Services.*;

import java.io.NotSerializableException;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy("D:\\JavaSBTGit\\SBT\\Lesson9\\src\\main\\resources\\cache");
        Service service = (Service) cacheProxy.cache(new ServiceImpl());

        /*System.out.println(service.doHardWork("work1", 10));
        System.out.println(service.doHardWork("work2", 5));
        System.out.println(service.doHardWork("work1", 10));*/

        /*System.out.println("result " + service.doHardWork("work1", 10, new Date()));
        System.out.println("result " + service.doHardWork("work1", 5, new Date()));
        System.out.println("result " + service.doHardWork("work1", 10, new Date()));*/

        System.out.println("result " + service.doHardWork("Hello"));
        System.out.println("result " + service.doHardWork("Hello"));
        System.out.println("result " + service.doHardWork("Hello"));
        System.out.println("result " + service.doHardWork("Hello"));

        //System.out.println("result " + service.getPeople());
    }
}
