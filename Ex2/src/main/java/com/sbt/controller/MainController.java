package com.sbt.controller;

import com.sbt.service.ReserveInfoDay;
import com.sbt.model.ReserveInfo;
import com.sbt.service.UserSecurity;
import com.sbt.model.entity.User;
import com.sbt.repository.ReserveRepository;
import com.sbt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    @GetMapping("/")
    public String homePage(Map<String, Object> model) {
        User user = UserSecurity.getCurrentAuthUser(userRepository);
        model.put("user", user);
        return "home";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        getReserveDateInfo(date, model, true);
        return "main";
    }

    private void getReserveDateInfo(LocalDateTime date, Map<String, Object> model, boolean necessaryUser) {
        User user = UserSecurity.getCurrentAuthUser(userRepository);
        List<ReserveInfo> reserveDate = ReserveInfoDay.getReserveDate(reserveRepository, date, user);
        model.put("reserveDate", reserveDate);
        if (necessaryUser)
            model.put("user", user);
    }

    @RequestMapping("/main/ajax")
    public @ResponseBody
    Map<String, Object> mainAjax(@RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year) {
        LocalDateTime date = LocalDateTime.of(year, month + 1, day, 0, 0);
        Map<String, Object> model = new HashMap<>();
        getReserveDateInfo(date, model, false);
        return model;
    }
}
