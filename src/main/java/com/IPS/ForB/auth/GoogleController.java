package com.IPS.ForB.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IPS.ForB.dto.TokenDto;
import com.IPS.ForB.service.AuthService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/google")
public class GoogleController {
	@Autowired
	AuthService authService;

	/* ResponseEntity 사용해서 헤더에 status code 담는 버전 */
	@PostMapping(value = "/login")
	public ResponseEntity<TokenDto> googleLogin(@RequestBody GoogleLoginResponse googleLoginResponse) throws IOException {
		String accessToken = googleLoginResponse.getAccess_token();
		String idToken = googleLoginResponse.getId_token();
		System.out.println("========== user's accessToken: " + accessToken + " ==========");

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authService.googleLogin(accessToken));
	}
	/* ResponseEntity 사용해서 헤더에 status code 담는 버전 */
//	@PostMapping(value = "/login")
//	public ResponseEntity<TokenDto> googleLogin(@RequestBody GoogleLoginResponse googleLoginResponse) throws IOException {
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Status-Code", String.valueOf(HttpStatus.OK));
//
//		String accessToken = googleLoginResponse.getAccess_token();
//		String idToken = googleLoginResponse.getId_token();
//		System.out.println(accessToken);
//
//		return ResponseEntity
//				.status(HttpStatus.OK)
//				.headers(headers)
//				.body(authService.googleLogin(accessToken));
//	}
}