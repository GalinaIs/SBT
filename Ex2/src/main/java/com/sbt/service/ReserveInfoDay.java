package com.sbt.service;

import com.sbt.model.ReserveInfo;
import com.sbt.model.entity.Reserve;
import com.sbt.model.entity.User;
import com.sbt.repository.ReserveRepository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReserveInfoDay {
    private static final int START_DAY = 9;
    private static final int END_DAY = 18;

    public static List<ReserveInfo> getReserveDate(ReserveRepository reserveRepository, LocalDateTime date,
                                                   User userAuth) {
        LocalDateTime tomorrow = date.plusDays(1);

        List<Reserve> listReserve = reserveRepository.findByDateStartBetween(Timestamp.valueOf(date),
                Timestamp.valueOf(tomorrow));
        listReserve.sort(Comparator.comparing(Reserve::getDateStart));

        return fillListReserveInfo(listReserve, date, userAuth);
    }

    private static boolean timeIsBusy(int i, Reserve reserve) {
        return i >= reserve.getDateStart().getHours() && (i + 1) <= reserve.getDateEnd().getHours();
    }

    private static int incrementIndexList(int i, Reserve reserve) {
        return (i + 1) == reserve.getDateEnd().getHours() ? 1 : 0;
    }

    private static List<ReserveInfo> fillListReserveInfo(List<Reserve> listReserve, LocalDateTime date, User userAuth) {
        List<ReserveInfo> result = new ArrayList<>();

        int indexList = 0;
        for (int i = START_DAY; i < END_DAY; i++) {
            if (indexList < listReserve.size()) {
                Reserve reserve = listReserve.get(indexList);
                if (timeIsBusy(i, reserve)) {
                    addReserveTime(result, date, i, reserve.getUser(), userAuth);
                    indexList += incrementIndexList(i, reserve);
                } else {
                    addNoReserveTime(result, date, i);
                }
            } else {
                addNoReserveTime(result, date, i);
            }
        }

        return result;
    }

    private static void setStateNoReserveUser(ReserveInfo reserveInfo, LocalDateTime date, LocalTime time) {
        LocalTime timeNow = LocalTime.now();
        LocalDate dateNow = LocalDate.now();
        if (dateNow.isBefore(date.toLocalDate()) || (dateNow.isEqual(date.toLocalDate()) && timeNow.isBefore(time)))
            reserveInfo.getState().setAllowedReserve(true);
        else
            reserveInfo.getState().setIllegalReserve(true);
    }

    private static void setStateReserveUser(ReserveInfo reserveInfo, LocalDateTime date, LocalTime time, User userReserve,
                                            User userAuth) {
        setStateNoReserveUser(reserveInfo, date, time);

        if (reserveInfo.getState().isAllowedReserve() && userAuth.equals(userReserve)) {
            reserveInfo.getState().setCancelReserve(true);
        }

        reserveInfo.getState().setAllowedReserve(false);
    }

    private static String getHourString(int i) {
        String result = i + ":00";
        if (result.length() < 5)
            return "0" + result;

        return result;
    }

    private static void addReserveTime(List<ReserveInfo> result, LocalDateTime date, int i, User userReserve,
                                       User userAuth) {
        ReserveInfo reserveInfo = new ReserveInfo(date.toLocalDate(), getHourString(i), getHourString(i + 1),
                userReserve);
        setStateReserveUser(reserveInfo, date, LocalTime.of(i, 0), userReserve, userAuth);
        result.add(reserveInfo);
    }

    private static void addNoReserveTime(List<ReserveInfo> result, LocalDateTime date, int i) {
        ReserveInfo reserveInfo = new ReserveInfo(date.toLocalDate(), getHourString(i), getHourString(i + 1));
        setStateNoReserveUser(reserveInfo, date, LocalTime.of(i, 0));
        result.add(reserveInfo);
    }

    public static boolean isAllowedHour(int hour) {
        return hour <= END_DAY;
    }

}
