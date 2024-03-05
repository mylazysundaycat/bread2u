package com.daegeon.bread2u.module.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @GetMapping("/index")
    public String home(){
        return "/index";
    }
}
