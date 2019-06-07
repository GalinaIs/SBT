package com.sbt.controller;

import com.sbt.model.Message;
import com.sbt.model.User;
import com.sbt.service.MessageService;
import com.sbt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes(value = "userAuth")
public class AppController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String main(@ModelAttribute("userAuth") User user, ModelMap model) {
        System.out.println(user);
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping(value = {"/message"}, method = RequestMethod.GET)
    public String prepareMessage(@ModelAttribute("userAuth") User user, ModelMap model) {
        if (user.getUsername() == null) return "redirect:/login";

        Message message = new Message();
        model.addAttribute("message", message);
        return "message";
    }

    @RequestMapping(value = {"/message"}, method = RequestMethod.POST)
    public String sendMessage(@ModelAttribute("userAuth") User user, @Valid Message message,
                              BindingResult result, ModelMap model) {
        if (result.hasErrors()) return "message";

        message.setFrom(user);
        messageService.sendMessage(message);
        model.addAttribute("success", "Сообщение отправлено");
        return "messageSuccess";
    }

    @RequestMapping(value = {"/chat"}, method = RequestMethod.GET)
    public String getChat(@ModelAttribute("userAuth") User user, ModelMap model) {
        System.out.println(user);
        if (user.getUsername() == null) return "redirect:/login";

        System.out.println(messageService.getAllMessages());
        model.addAttribute("orders", messageService.getAllMessages());
        return "chat";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(ModelMap model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String checkUser(@Valid User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) return "login";

        if (userService.checkUser(user)) {
            model.addAttribute("userAuth", user);
            return "redirect:/chat";
        }
        model.addAttribute("error", "Пользователь с таким именем уже существует");
        return "login";
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(@ModelAttribute("userAuth") User user, ModelMap model) {
        userService.deleteUser(user);
        model.addAttribute("userAuth", createUser());
        return "redirect:/home";
    }

    @ModelAttribute("userAuth")
    private User createUser(){
        return new User();
    }
}
