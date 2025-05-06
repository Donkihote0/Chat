package messenger.messenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/user")
    public String getUser(Principal principal) {
        return principal.getName(); // Trả về tên người đã đăng nhập
    }
}

