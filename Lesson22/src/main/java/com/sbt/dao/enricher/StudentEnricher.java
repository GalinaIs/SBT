package com.sbt.dao.enricher;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Student;

public class StudentEnricher implements Enricher<Student> {
    @Override
    public void enrich(Student record) throws DaoSystemException, NoSuchEntityException {
        /*NOP*/
    }
}
