package com.flx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "html/index";
    }
    @RequestMapping("/index")
    public String index1(){
        return "html/index";
    }

    @RequestMapping("/restaurant")
    public String restaurant(String foodName,String foodArea,String foodTime,Model model){
        model.addAttribute("foodName",foodName);
        model.addAttribute("foodArea",foodArea);
        model.addAttribute("foodTime",foodTime);
        return "html/restaurant";
    }

}
