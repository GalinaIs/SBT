package com.sbt.service.reserve;

import com.sbt.model.entity.Reserve;
import com.sbt.model.entity.User;
import com.sbt.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReserveService {
    @Autowired
    private ReserveRepository reserveRepository;

    private LocalDateTime getDateFrom(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }

    private LocalDateTime getDateTo(LocalDateTime dateFrom, Integer duration) {
        return dateFrom.plusHours(duration);
    }

    private boolean isAllowedDuration(LocalDateTime dateTo) {
        int hourTo = dateTo.getHour();
        return ReserveDayInfoService.isAllowedHour(hourTo);
    }

    private boolean isAllowedDate(LocalDateTime dateFrom) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return dateFrom.isAfter(dateTimeNow);
    }

    public ResultReserve getResultReserve(String dateValue, String timeFrom, Integer duration, User user) {
        LocalDateTime dateFrom = getDateFrom(dateValue + " " + timeFrom);
        LocalDateTime dateTo = getDateTo(dateFrom, duration);

        if (!isAllowedDuration(dateTo))
            return ResultReserve.TooLongDuration;

        if (!isAllowedDate(dateFrom))
            return ResultReserve.LastDate;

        Timestamp timestampFrom = Timestamp.valueOf(dateFrom);
        Timestamp timestampTo = Timestamp.valueOf(dateTo);

        if (!reserveRepository.findByDateStartLessThanEqualAndDateEndGreaterThan(timestampFrom, timestampFrom)
                .isEmpty() ||
                !reserveRepository.findByDateStartLessThanAndDateEndGreaterThanEqual(timestampTo, timestampTo)
                        .isEmpty() ||
                !reserveRepository.findByDateStartLessThanEqualAndDateEndGreaterThanEqual(timestampFrom, timestampTo).
                        isEmpty())
            return ResultReserve.Cancel;

        Reserve reserve = new Reserve(timestampFrom, timestampTo, user);
        reserveRepository.save(reserve);
        return ResultReserve.OK;
    }

    public ResultReserve getResultCancelReserve(String dateValue, String timeFrom, User user) {
        LocalDateTime dateFrom = getDateFrom(dateValue + " " + timeFrom);
        LocalDateTime dateTo = getDateTo(dateFrom, 1);

        List<Reserve> listReserve = reserveRepository.findByDateStartLessThanEqualAndDateEndGreaterThanEqualAndUserId
                (Timestamp.valueOf(dateFrom), Timestamp.valueOf(dateTo), user.getId());
        return deleteReserve(listReserve);
    }

    private ResultReserve deleteReserve(List<Reserve> listReserve) {
        if (listReserve.isEmpty())
            return ResultReserve.Cancel;

        for (Reserve reserve : listReserve) {
            reserveRepository.delete(reserve);
        }
        return ResultReserve.OK;
    }
}
