package com.sbt.schedule;

import com.sbt.dao.*;
import com.sbt.entity.Lesson;
import com.sbt.entity.Student;
import com.sbt.entity.StudentVisit;
import com.sbt.jdbc.connectionFactory.ConnectionFactory;
import com.sbt.jdbc.connectionFactory.ConnectionFactoryProxool;
import com.sbt.jdbc.transactionManager.TransactionManager;
import com.sbt.jdbc.transactionManager.TransactionManagerImpl;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactoryProxool();
        TransactionManager txManager = new TransactionManagerImpl(connectionFactory);

        StudentDao studentDao = new StudentDaoImpl(txManager);
        LessonDao lessonDao = new LessonDaoImpl(txManager);
        StudentVisitDao studentVisitDao = new StudentVisitDaoImpl(txManager, studentDao, lessonDao);

        List<StudentVisit> result = txManager.doInTransaction(() -> {
            /*Student student = studentDao.selectByFirstName("Galina").get(0);
            return studentVisitDao.selectByStudent(student);*/

            Lesson lesson = lessonDao.selectById(1);
            return studentVisitDao.selectByLesson(lesson);

            /*Student student = studentDao.selectById(3);
            Lesson lesson = lessonDao.selectById(1);
            StudentVisit studentVisit = new StudentVisit(student, lesson);
            return studentVisitDao.insert(studentVisit);*/
        });
        System.out.println(result);
    }
}
