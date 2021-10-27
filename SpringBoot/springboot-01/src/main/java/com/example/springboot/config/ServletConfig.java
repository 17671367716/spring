package com.example.springboot.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@ServletComponentScan(value = "com.example.springboot.servlet")
public class ServletConfig {
}
