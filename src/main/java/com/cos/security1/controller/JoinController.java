package com.cos.security1.controller;

import com.cos.security1.dto.JoinDto;
import com.cos.security1.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP(){
        return "join";
    }
    @PostMapping("/joinProc")
    public String joinProcess(JoinDto joinDto){

        System.out.println(joinDto.getUsername());
        joinService.joinProcess(joinDto);

        return "redirect:/login";
    }
}
