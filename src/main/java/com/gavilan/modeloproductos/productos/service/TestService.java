package com.gavilan.modeloproductos.productos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class TestService {

    @GetMapping("/redirect")
    public String redirect() {
        return "redirect:http://localhost:4200/recuperar-pass/redirect";
    }
}
