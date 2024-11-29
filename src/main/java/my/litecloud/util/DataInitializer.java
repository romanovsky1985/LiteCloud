package my.litecloud.util;

import my.litecloud.dto.PageDTO;
import my.litecloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
@Profile("development")
public class DataInitializer implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        userService.createUser("anton", "5557720");
        userService.appendPage("anton",
                new PageDTO("Поиск", "Гугл", "https://google.com"));
    }
}
