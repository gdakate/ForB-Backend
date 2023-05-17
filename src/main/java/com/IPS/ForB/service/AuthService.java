package com.IPS.ForB.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.IPS.ForB.User.Entity.Role;
import com.IPS.ForB.User.Entity.User;
import com.IPS.ForB.User.UserRepository;
import com.IPS.ForB.auth.JwtTokenUtil;
import com.IPS.ForB.dto.GoogleUserInfoDto;
import com.IPS.ForB.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    @Transactional
    public TokenDto googleLogin(String accessToken) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        User user = null;
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("https://www.googleapis.com/oauth2/v1/userinfo", HttpMethod.GET, request,String.class);
        System.out.println("response.getBody() = " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleUserInfoDto googleUser = objectMapper.readValue(response.getBody(), GoogleUserInfoDto.class);

        if (userRepository.findByEmail(googleUser.getEmail()).orElse(null) == null) {
            System.out.println("------------userRepository에 없음, 새로 만들기: "+googleUser.getEmail());
            user = User.builder()
                    .name(googleUser.getName())
                    .email(googleUser.getEmail())
                    .picture(googleUser.getPicture())
                    .role(Role.USER)
                    .build();

            userRepository.save(user);
        }
        user = userRepository.findByEmail(googleUser.getEmail()).get();
        String jwt = jwtTokenUtil.generateToken(user);
        System.out.println("googleUser email: "+googleUser.getEmail());
        System.out.println("token: "+jwt+", googleUserName: "+googleUser.getName());

        User currentUser = userRepository.findByEmail(googleUser.getEmail()).get();

        TokenDto tokenDto = TokenDto.builder()
                .token(jwt)
                .user_id(currentUser.getId())
                .build();

        return tokenDto;

    }
}
