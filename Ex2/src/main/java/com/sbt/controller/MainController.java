package com.sbt.controller;

import com.sbt.model.ReserveInfo;
import com.sbt.model.entity.User;
import com.sbt.service.reserve.ReserveDayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private ReserveDayInfoService reserveDayInfo;

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User userAuth, Model model) {
        System.out.println(userAuth);
        model.addAttribute("userAuth", userAuth);
        return "home";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User userAuth, Model model) {
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        List<ReserveInfo> reserveDate = reserveDayInfo.getReserveInfoDate(date, userAuth);
        model.addAttribute("reserveDate", reserveDate);
        model.addAttribute("userAuth", userAuth);
        return "main";
    }

    @RequestMapping("/main/ajax")
    public @ResponseBody
    Map<String, Object> mainAjax(@AuthenticationPrincipal User userAuth,
                                 @RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year) {
        LocalDateTime date = LocalDateTime.of(year, month + 1, day, 0, 0);
        List<ReserveInfo> reserveDate = reserveDayInfo.getReserveInfoDate(date, userAuth);
        Map<String, Object> model = new HashMap<>();
        model.put("reserveDate", reserveDate);
        return model;
    }
}
