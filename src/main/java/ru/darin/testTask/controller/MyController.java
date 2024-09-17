package ru.darin.testTask.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @GetMapping("/show")
    public String showMessage(){
        return "Hello";
    }

    @PostMapping("/input")
    public String inputStr(@RequestParam String str) {
        return str;
    }

}
