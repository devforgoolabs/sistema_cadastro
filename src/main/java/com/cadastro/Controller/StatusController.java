package com.cadastro.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RestController
public class StatusController {

    @GetMapping(path = "/api/status")
    public String check(){
        return "online";
    }
}
