package com.sbt.dao;

import com.sbt.entity.CountUnit;

public interface CountUnitDao extends Dao<CountUnit> {
    CountUnit findCountUnitByName(String name);
}
