package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class mainController {
    @GetMapping("/")
    @ResponseBody
    public int home() {
        System.out.println("home");
        return 22;
    }

    @GetMapping("/about")
    @ResponseBody
    public String about() {
        System.out.println("about");
        return "안녕하세요. 저는 홍길동입니다.";
    }

    public void contact() {
        System.out.println("contact");
    }
}


