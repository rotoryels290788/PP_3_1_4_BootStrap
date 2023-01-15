package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UserService;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Construct {
    private final UserService userService;
    private final RolesService rolesService;


    @Autowired
    public Construct(UserService userService, RolesService rolesService) {

        this.userService = userService;
        this.rolesService = rolesService;
    }


    @PostConstruct
    public void createTable() {

        Roles admin = new Roles("ROLE_ADMIN");
        Roles rolUser = new Roles("ROLE_USER");
        rolesService.saveRole(admin);
        rolesService.saveRole(rolUser);

        List<Roles> role = new ArrayList<>();
        role.add(admin);
        List<Roles> role2 = new ArrayList<>();
        role2.add(rolUser);
        List<Roles> role3 = new ArrayList<>();
        role3.add(admin);
        role3.add(rolUser);


        User user = new User("Dmitry",34, "IT", "dima@.ru",
                "1", role3);

        User user2 = new User("Ivan",36, "Manager", "Iva@.ru",
                "2", role2);

        User user3 = new User("Aleksey",43, "IT", "Aleks@.ru",
                "3", role);


        userService.saveUser(user);
        userService.saveUser(user2);
        userService.saveUser(user3);

    }
}
