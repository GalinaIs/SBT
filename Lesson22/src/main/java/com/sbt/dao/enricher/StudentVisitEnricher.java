package com.sbt.dao.enricher;

import com.sbt.dao.LessonDao;
import com.sbt.dao.StudentDao;
import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Lesson;
import com.sbt.entity.Student;
import com.sbt.entity.StudentVisit;

public class StudentVisitEnricher implements Enricher<StudentVisit> {
    private final StudentDao studentDao;
    private final LessonDao lessonDao;

    public StudentVisitEnricher(StudentDao studentDao, LessonDao lessonDao) {
        this.studentDao = studentDao;
        this.lessonDao = lessonDao;
    }

    @Override
    public void enrich(StudentVisit record) throws DaoSystemException, NoSuchEntityException {
        Student student = studentDao.selectById(record.getStudent().getId());
        record.setStudent(student);

        Lesson lesson = lessonDao.selectById(record.getLesson().getId());
        record.setLesson(lesson);
    }
}
