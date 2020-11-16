package polytech.covidalert.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin
public class HomeController {
    @Value("${app.version}")
    private String appVersion;

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object versionApp() {
        return Collections.singletonMap("message", this.appVersion);
    }

}
