package br.pucpr.authserver.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PhpController {

    @GetMapping("/php")
    fun phpPage(): String {
        return "index.php"
    }
}