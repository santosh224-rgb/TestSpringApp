package com.example.demo.application.demo.Controller;

import com.example.demo.application.demo.Model.AuthRequest;
import com.example.demo.application.demo.Model.User;
import com.example.demo.application.demo.Service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/user/details")
    private ResponseEntity<User> getUserDetails(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{id}")
    private ResponseEntity<User> getUserDetailsById(@PathVariable String id){
        return ResponseEntity.ok(userService.getUser(id));
    }

    @DeleteMapping("/user/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }

    @GetMapping("/filter/role")
    public ResponseEntity<List<User>> getRoles(@RequestParam String roleName) {
        return ResponseEntity.ok(userService.filterItems(roleName)) ;
    }


    @GetMapping("/filter/name")
    public ResponseEntity<User> getUserDetailsByName(@RequestParam String userName){
        return ResponseEntity.ok(userService.getUserDetail(userName));
    }



    @GetMapping("/getUserByName")
    public ResponseEntity<User> getUserByName(@RequestParam String userName){
        return ResponseEntity.ok(userService.getUserDetail(userName));

    }



    @PostMapping("/test/scheduler")
    public ResponseEntity<String> testScheduler(){
        return ResponseEntity.ok("Scheduler is running");
    }


    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String result = userService.verifyUser(authRequest);
        try {
            if ("Success".equals(result)) {
                return ResponseEntity.ok("Login Successful");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @PostMapping(value = "/echo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> echoVideo(@RequestPart("file") MultipartFile file) throws IOException {
        InputStreamResource resource = new InputStreamResource(file.getInputStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getOriginalFilename() + "\"")
                .contentType(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())))
                .body(resource);
    }
    @PostMapping("/feedback")
    public ResponseEntity<String> feedback(@RequestBody String feedback){
        log.info("Feedback is {} ",feedback);
        return ResponseEntity.ok("Feedback is Submitted");
    }
}
