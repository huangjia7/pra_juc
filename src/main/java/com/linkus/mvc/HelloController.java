package com.linkus.mvc;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/user/{aa}/user1/{bb}")
    public Map<String,String> ll(@PathVariable Map<String,String> pv){
        return pv;
    }

    @GetMapping("/ll1")
    public Map<String,String> ll1(@RequestHeader Map<String,String> header){
        return header;
    }
    @GetMapping("/ll2")
    public Map<String,String> ll2(@RequestParam Map<String,String> params){
        return params;
    }
    @GetMapping("/ll3")
    public MultiValueMap ll3(@RequestParam MultiValueMap multiValueMap){

        return multiValueMap;
    }

}
