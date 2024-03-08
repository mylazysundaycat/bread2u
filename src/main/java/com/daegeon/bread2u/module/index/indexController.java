package com.daegeon.bread2u.module.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/index")
    public String showIndex(){
        return "/index";
    }
}
