package com.bn.biteNest.profile.controller;

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

import com.bn.biteNest.profile.model.service.ProfileService;
import com.bn.biteNest.profile.model.vo.Profile;
import com.bn.biteNest.response.model.vo.ResponseData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

	private final ProfileService profileService;
	
	// 새로운 프로필 생성
    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profileDetails) {
        
    	Profile newProfile = Profile.builder()
							                .user(profileDetails.getUser())    
							                .nickname(profileDetails.getNickname())
							                .imageUrl(profileDetails.getImageUrl())
							                .introduction(profileDetails.getIntroduction())
							                .build();

        Profile savedProfile = profileService.save(newProfile);

        return ResponseEntity.status(201)
        							  .body(savedProfile);
    }
	
	// 프로필 조회
	@GetMapping("/{id}")
	public ResponseEntity<ResponseData> getProfile(@PathVariable int id) {
		
		Optional<Profile> profile = profileService.findById(id);
		
		Profile searchedProfile = profile.get();
		
		ResponseData rd = ResponseData.builder()
													   .responseData(searchedProfile)
													   .build();

		return ResponseEntity.ok(rd);
		
	}
	
	
	// 프로필 수정
	@PutMapping("/{id}")
	public ResponseEntity<Profile> updateProfile(@PathVariable int id,
																   @RequestBody Profile profileDetail) {
		Optional<Profile> profileOptional = profileService.findById(id);
		
		if(profileOptional.isPresent()) {
			
			Profile existingProfile = profileOptional.get();
			
			Profile updatedProfile = Profile.builder()
													  .profileId(existingProfile.getProfileId())
													  .nickname(profileDetail.getNickname())
													  .imageUrl(profileDetail.getImageUrl())
													  .introduction(profileDetail.getIntroduction())
													  .build();
													  
			return ResponseEntity.ok(profileService.save(updatedProfile));
		
		} else {
			return ResponseEntity.notFound()
										  .build();
		}
	}
	
	// 프로필 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProfile(@PathVariable int id) {
		
		Optional<Profile> profileOptional = profileService.findById(id);
		
		if(profileOptional.isPresent()) {
			profileService.deleteById(id);
			return ResponseEntity.noContent()
										  .build();
		} else {
			return ResponseEntity.notFound()
										  .build();
		}
	}
	
}
