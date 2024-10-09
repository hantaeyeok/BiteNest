package com.bn.biteNest.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bn.biteNest.response.model.vo.ResponseData;
import com.bn.biteNest.user.model.service.UserService;
import com.bn.biteNest.user.model.vo.Signup;
import com.bn.biteNest.user.model.vo.User;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService; 

	//JPA
	// 회원 가입
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody Signup signup) {
	    System.out.println("회원가입 요청 들어옴: " + signup);
	    User user = userService.regist(signup);
	    System.out.println("회원가입 처리 완료: " + user);
	    return ResponseEntity.ok(user);
	}
	
	
	// 모든 사용자 조회
	@GetMapping
	public ResponseEntity<ResponseData> findAll() {
		
		List<User> users = userService.findAll();
		
		ResponseData rd = ResponseData.builder()
													   .responseData(users)
													   .build();
		
		return ResponseEntity.ok(rd);
		
		
	}
	
	// 특정 사용자 조회(USER_NO 기준)
	@GetMapping("/{id}")
	public ResponseEntity<ResponseData> findById(@PathVariable("id") int id) {
		
		Optional<User> user = userService.findById(id);
		
		User searchedUser = user.get();
		
		ResponseData rd = ResponseData.builder()
													   .responseData(searchedUser)
													   .build();

		return ResponseEntity.ok(rd);
		
	}
	
	
	// 정보 변경
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable int id,
															  @RequestBody User userDetail) {
		Optional<User> userOptional = userService.findById(id);
		if(userOptional.isPresent()) {
			User existingUser = userOptional.get();
			
			User updatedUser = User.builder()
								               .userNo(existingUser.getUserNo())
								               .userId(userDetail.getUserId())
								               .loginType(userDetail.getLoginType())
								               .build();

	        return ResponseEntity.ok(userService.update(updatedUser));
	    } else {
			return ResponseEntity.notFound()
										  .build();
		}
		
	}
	
	//회원 탈퇴
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable int id) {
		
		Optional<User> user = userService.findById(id);
		
		if(user.isPresent()) {
			userService.delete(user.get());
			return ResponseEntity.noContent()
										  .build();
		} else {
			return ResponseEntity.notFound()
										  .build();
		}
	}
	
	
	// Mybatis
	/*
	@PostMapping
	public ResponseEntity<ResponseData> enroll(@RequestParam("user") String user) throws JsonMappingException, JsonProcessingException {
	
		log.info("User Table에 INSERT할 내용 : {} ", user);
		
		User vo = new ObjectMapper().readValue(user, User.class);
		
		log.info("VO형태로 가공 : {} ", vo);
		
		User enrollUser = userService.enroll(vo); 
		
		log.info("반환받은 User : {} ", enrollUser);
		
		return null;
	}
	*/
	
	
	
	
	
}
