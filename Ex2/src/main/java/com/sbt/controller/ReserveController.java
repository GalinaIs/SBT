package com.sbt.controller;

import com.sbt.repository.ReserveRepository;
import com.sbt.repository.UserRepository;
import com.sbt.service.ResultReserve;
import com.sbt.service.ReserveUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ReserveController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    @RequestMapping("/reserve")
    public @ResponseBody
    Map<String, Object> reserveAjax(@RequestParam String dateValue, @RequestParam String timeFrom,
                                 @RequestParam Integer duration) {
        Map<String, Object> model = new HashMap<>();
        ResultReserve answer = ReserveUtils.getResultReserve(dateValue, timeFrom, duration, reserveRepository,
                    userRepository);
        model.put("answer", answer);

        return model;
    }

    @RequestMapping("/cancelReserve")
    public @ResponseBody
    Map<String, Object> cancelAjax(@RequestParam String dateValue, @RequestParam String timeFrom) {
        Map<String, Object> model = new HashMap<>();
        ResultReserve answer = ReserveUtils.getResultCancel(dateValue, timeFrom, reserveRepository, userRepository);
        model.put("answer", answer);

        return model;
    }
}
