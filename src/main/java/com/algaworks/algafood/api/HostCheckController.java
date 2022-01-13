package com.algaworks.algafood.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HostCheckController {

    @GetMapping(path = "/hostcheck")
    public String checkHost() throws UnknownHostException {
        return String.format("%s - %s", InetAddress.getLocalHost().getHostAddress(), InetAddress.getLocalHost().getHostName());
    }

}
