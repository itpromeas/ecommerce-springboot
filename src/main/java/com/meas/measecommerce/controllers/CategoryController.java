package com.meas.measecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @GetMapping("/test")
    public String test(){
        return "success";
    }


}
