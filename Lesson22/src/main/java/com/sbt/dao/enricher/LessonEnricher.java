package com.sbt.dao.enricher;

import com.sbt.dao.exception.DaoSystemException;
import com.sbt.dao.exception.NoSuchEntityException;
import com.sbt.entity.Lesson;

public class LessonEnricher implements Enricher<Lesson> {
    @Override
    public void enrich(Lesson record) throws DaoSystemException, NoSuchEntityException {
        /*NOP*/
    }
}
