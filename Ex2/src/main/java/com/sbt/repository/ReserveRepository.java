package com.sbt.repository;

import com.sbt.model.entity.Reserve;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ReserveRepository extends CrudRepository<Reserve, Integer> {
    List<Reserve> findByDateStartLessThanEqualAndDateEndGreaterThan(Timestamp dateFrom, Timestamp dateTo);

    List<Reserve> findByDateStartLessThanAndDateEndGreaterThanEqual(Timestamp dateFrom, Timestamp dateTo);

    List<Reserve> findByDateStartLessThanEqualAndDateEndGreaterThanEqual(Timestamp dateFrom, Timestamp dateTo);

    List<Reserve> findByDateStartBetween(Timestamp dateFrom, Timestamp dateTo);

    List<Reserve> findByDateStartLessThanEqualAndDateEndGreaterThanEqualAndUserId(Timestamp dateFrom, Timestamp dateTo,
                                                                                  long userId);

}
