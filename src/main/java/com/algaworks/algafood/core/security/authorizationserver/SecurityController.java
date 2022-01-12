package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(path = "/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping(path = "/oauth/confirm_access")
    public String approval() {
        return "pages/approval";
    }

}
