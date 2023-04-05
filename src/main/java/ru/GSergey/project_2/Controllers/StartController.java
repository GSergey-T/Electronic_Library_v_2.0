package ru.GSergey.project_2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/startPage")
public class StartController {

    @GetMapping()
    public String start() {
        return "start";
    }
}
