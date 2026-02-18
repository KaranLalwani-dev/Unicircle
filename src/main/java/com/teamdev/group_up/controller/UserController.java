package com.teamdev.group_up.controller;


import com.teamdev.group_up.dto.auth.UpdateProfileRequest;
import com.teamdev.group_up.dto.auth.UserProfileResponse;
import com.teamdev.group_up.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile(){
        return ResponseEntity.ok(userService.getUserProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request){
        return ResponseEntity.ok(userService.updateUserProfile(request));
    }
}
