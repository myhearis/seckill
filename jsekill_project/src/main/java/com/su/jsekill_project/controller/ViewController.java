package com.su.jsekill_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/setDate/")
    public String getSetDateView(){
        return "SekillDate";
    }
}
