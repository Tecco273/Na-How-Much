import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


package com.soft.backapp.controller;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
private final MyUserService userService;
private final ItemService itemService;

@PostMapping("")
public String postMethodName(@RequestBody String entity) {
    //TODO: process POST request
    
    return entity;
}

}