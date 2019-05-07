package com.sbt.service.reserve;

import com.sbt.model.ReserveInfo;
import com.sbt.model.entity.Reserve;
import com.sbt.model.entity.User;
import com.sbt.repository.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReserveDayInfoService {
    private static final int START_DAY = 9;
    private static final int END_DAY = 18;

    @Autowired
    private ReserveRepository reserveRepository;

    public List<ReserveInfo> getReserveInfoDate(LocalDateTime date, User userAuth) {
        LocalDateTime tomorrow = date.plusDays(1);

        List<Reserve> listReserve = reserveRepository.findByDateStartBetween(Timestamp.valueOf(date),
                Timestamp.valueOf(tomorrow));
        listReserve.sort(Comparator.comparing(Reserve::getDateStart));

        return fillListReserveInfo(listReserve, date, userAuth);
    }

    private boolean timeIsBusy(int i, Reserve reserve) {
        return i >= reserve.getDateStart().getHours() && (i + 1) <= reserve.getDateEnd().getHours();
    }

    private int incrementIndexList(int i, Reserve reserve) {
        return (i + 1) == reserve.getDateEnd().getHours() ? 1 : 0;
    }

    private List<ReserveInfo> fillListReserveInfo(List<Reserve> listReserve, LocalDateTime date, User userAuth) {
        List<ReserveInfo> result = new ArrayList<>();

        int indexList = 0;
        for (int i = START_DAY; i < END_DAY; i++) {
            if (indexList < listReserve.size()) {
                Reserve reserve = listReserve.get(indexList);
                if (timeIsBusy(i, reserve)) {
                    addReserveTime(result, date, i, reserve.getUser(), userAuth);
                    indexList += incrementIndexList(i, reserve);
                } else {
                    addFreeTime(result, date, i);
                }
            } else {
                addFreeTime(result, date, i);
            }
        }

        return result;
    }

    private boolean dateInFuture(LocalDateTime date, LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(date.toLocalDate(), time);
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return dateTime.isAfter(dateTimeNow);
    }

    private void setActionFreeTime(ReserveInfo reserveInfo, LocalDateTime date, LocalTime time) {
        if (dateInFuture(date, time))
            reserveInfo.getAction().setAllowedReserve(true);
    }

    private void setActionReserveUser(ReserveInfo reserveInfo, LocalDateTime date, LocalTime time, User userReserve,
                                      User userAuth) {
        if (dateInFuture(date, time) && userAuth.equals(userReserve))
            reserveInfo.getAction().setCancelReserve(true);
    }

    private String getHourString(int i) {
        String result = i + ":00";
        if (result.length() == 4)
            return "0" + result;

        return result;
    }

    private void addReserveTime(List<ReserveInfo> result, LocalDateTime date, int i, User userReserve, User userAuth) {
        ReserveInfo reserveInfo = new ReserveInfo(date.toLocalDate(), getHourString(i), getHourString(i + 1),
                userReserve);
        setActionReserveUser(reserveInfo, date, LocalTime.of(i, 0), userReserve, userAuth);
        result.add(reserveInfo);
    }

    private void addFreeTime(List<ReserveInfo> result, LocalDateTime date, int i) {
        ReserveInfo reserveInfo = new ReserveInfo(date.toLocalDate(), getHourString(i), getHourString(i + 1));
        setActionFreeTime(reserveInfo, date, LocalTime.of(i, 0));
        result.add(reserveInfo);
    }

    static boolean isAllowedHour(int hour) {
        return hour <= END_DAY;
    }
}
