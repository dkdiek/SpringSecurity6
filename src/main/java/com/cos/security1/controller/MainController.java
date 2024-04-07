package com.cos.security1.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainP(Model model) {
        //세션 현재 사용자 아이디
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        //세션 현재 사용자 role
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        /*이 부분은 Spring Security의 SecurityContextHolder를 사용하여 현재 사용자의 인증 정보를 가져옵니다.
        getAuthentication() 메서드를 호출하여 현재 사용자의 인증 객체를 반환합니다.*/
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        /*getAuthorities() 메서드를 통해 현재 사용자가 가지고 있는 권한 목록을 가져옵니다.
        이 권한들은 GrantedAuthority 인터페이스의 구현체인 객체들의 컬렉션으로 반환됩니다.*/
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        /*컬렉션에 대한 반복자(iterator)를 얻습니다. 이것을 사용하여 컬렉션의 요소를 반복적으로 접근할 수 있습니다.*/
        GrantedAuthority auth = iter.next();
        /*iter를 사용하여 다음 권한(요소)을 가져옵니다. 이 권한은 GrantedAuthority 타입의 객체입니다.*/
        String role = auth.getAuthority();
        /*auth 객체에서 실제 권한 문자열을 가져옵니다. getAuthority() 메서드는 GrantedAuthority 인터페이스에서 구현해야 하는 메서드 중 하나로
        권한을 나타내는 문자열을 반환합니다. 이것이 현재 사용자의 권한을 나타내는 문자열이므로, role 변수에 할당됩니다.*/

        //클라이언트로 전달
        model.addAttribute("id", id);
        model.addAttribute("role", role);
 
        return "main";
    }
}
