package com.sbt.controller;

import com.sbt.model.entity.User;
import com.sbt.service.reserve.ReserveService;
import com.sbt.service.reserve.ResultReserve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ReserveController {
    @Autowired
    private ReserveService reserveService;

    @RequestMapping("/reserve")
    public @ResponseBody
    Map<String, Object> reserveAjax(@AuthenticationPrincipal User userAuth, @RequestParam String dateValue,
                                    @RequestParam String timeFrom, @RequestParam Integer duration) {
        Map<String, Object> model = new HashMap<>();
        ResultReserve answer = reserveService.getResultReserve(dateValue, timeFrom, duration, userAuth);
        model.put("answer", answer);

        return model;
    }

    @RequestMapping("/cancelReserve")
    public @ResponseBody
    Map<String, Object> cancelAjax(@AuthenticationPrincipal User userAuth, @RequestParam String dateValue,
                                   @RequestParam String timeFrom) {
        Map<String, Object> model = new HashMap<>();
        ResultReserve answer = reserveService.getResultCancelReserve(dateValue, timeFrom, userAuth);
        model.put("answer", answer);

        return model;
    }
}
