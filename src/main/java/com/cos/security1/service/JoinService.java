package com.cos.security1.service;

import com.cos.security1.dto.JoinDto;
import com.cos.security1.entity.UserEntity;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDto joinDto){
        //db에 이미 동일한 username가진 회원이 존재하는지?
        boolean isUser = userRepository.existsByUsername(joinDto.getUsername());
        if(isUser){
            return;
        }


        UserEntity data = new UserEntity();
        data.setUsername(joinDto.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);

    }
}
