package com.learn.demo.app_b_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ProductBController {

    @GetMapping("productB/test/{arg}")
    public String testB(@PathVariable String arg){
        int wait = 30;
        wait = Integer.parseInt(arg);
        System.out.println();
        System.out.println(LocalDateTime.now()+"wait for "+wait+ " sec"+"\t arg:"+arg);

        try {
            Thread.sleep(wait*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(LocalDateTime.now()+"wait for "+wait+ " sec. completed");
        return "productB_"+ LocalDateTime.now().toString();
    }
}
