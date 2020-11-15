package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
public class HomeController {
    @Value("${app.version}")
    private String appVersion;

    @GetMapping
    public String versionApp() {
        System.out.println("home route accessed");
        return "coucou";
    }


}
