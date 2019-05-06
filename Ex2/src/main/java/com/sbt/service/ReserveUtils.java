package com.sbt.service;

import com.sbt.model.entity.Reserve;
import com.sbt.model.entity.User;
import com.sbt.repository.ReserveRepository;
import com.sbt.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReserveUtils {
    private static LocalDateTime getDateFrom(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateString, formatter);
    }

    private static LocalDateTime getDateTo(LocalDateTime dateFrom, Integer duration) {
        return dateFrom.plusHours(duration);
    }

    private static boolean isAllowedDuration(LocalDateTime dateTo) {
        int hourTo = dateTo.getHour();
        return ReserveInfoDay.isAllowedHour(hourTo);
    }

    private static boolean isAllowedDate(LocalDateTime dateFrom) {
        LocalTime timeNow = LocalTime.now();
        LocalTime timeFrom = LocalTime.of(dateFrom.getHour(), 0);
        LocalDate dateNow = LocalDate.now();

        return (dateNow.isBefore(dateFrom.toLocalDate()) ||
                (dateNow.isEqual(dateFrom.toLocalDate()) && timeNow.isBefore(timeFrom)));
    }

    public static ResultReserve getResultReserve(String dateValue, String timeFrom, Integer duration,
                                                 ReserveRepository reserveRepository, UserRepository userRepository) {
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

        User user = UserSecurity.getCurrentAuthUser(userRepository);
        Reserve reserve = new Reserve(timestampFrom, timestampTo, user);
        reserveRepository.save(reserve);
        return ResultReserve.OK;
    }

    public static ResultReserve getResultCancel(String dateValue, String timeFrom, ReserveRepository reserveRepository,
                                                UserRepository userRepository) {
        LocalDateTime dateFrom = getDateFrom(dateValue + " " + timeFrom);
        LocalDateTime dateTo = getDateTo(dateFrom, 1);

        Timestamp timestampFrom = Timestamp.valueOf(dateFrom);
        Timestamp timestampTo = Timestamp.valueOf(dateTo);

        long userId = UserSecurity.getCurrentAuthUser(userRepository).getId();
        List<Reserve> listReserve = reserveRepository.findByDateStartLessThanEqualAndDateEndGreaterThanEqualAndUserId
                (timestampFrom, timestampTo, userId);
        deleteReserve(listReserve, reserveRepository);

        return ResultReserve.OK;
    }

    private static void deleteReserve(List<Reserve> listReserve, ReserveRepository reserveRepository) {
        if (!listReserve.isEmpty()) {
            for (Reserve reserve : listReserve) {
                reserveRepository.delete(reserve);
            }
        }
    }
}
