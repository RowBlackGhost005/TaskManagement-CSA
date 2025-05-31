package com.marin.TaskManagement.config;

import com.marin.TaskManagement.common.entity.Role;
import com.marin.TaskManagement.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Manages the initial setup of the basic Roles (User and Admin)
 *
 * This component will execute only ONCE in the initial deployment of the app and they these roles MUST exist.
 */
@Order(1)
@Component
public class RoleSetup implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleSetup(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            Role admin = new Role();
            admin.setName("ADMIN");

            Role user = new Role();
            user.setName("USER");

            roleRepository.save(admin);
            roleRepository.save(user);
        }
    }


}
