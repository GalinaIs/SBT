package com.sbt.controller;

import com.sbt.model.entity.User;
import com.sbt.repository.UserRepository;
import com.sbt.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String homePage(@AuthenticationPrincipal User userAuth, Model model) {
        System.out.println(userAuth);
        UserRepositoryImpl.getUsers();
        model.addAttribute("userAuth", userAuth);
        return "home";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User userAuth, Model model) {
        System.out.println(userAuth);
        UserRepositoryImpl.getUsers();
        model.addAttribute("userAuth", userAuth);
        return "chat";
    }

    /*@RequestMapping("/main/ajax")
    public @ResponseBody
    Map<String, Object> mainAjax(@AuthenticationPrincipal User userAuth,
                                 @RequestParam Integer day, @RequestParam Integer month, @RequestParam Integer year) {
        LocalDateTime date = LocalDateTime.of(year, month + 1, day, 0, 0);
        List<ReserveInfo> reserveDate = reserveDayInfo.getReserveInfoDate(date, userAuth);
        Map<String, Object> model = new HashMap<>();
        model.put("reserveDate", reserveDate);
        return model;
    }*/
}
