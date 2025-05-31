package com.marin.TaskManagement.config;

import com.marin.TaskManagement.common.entity.Role;
import com.marin.TaskManagement.common.entity.User;
import com.marin.TaskManagement.user.repository.RoleRepository;
import com.marin.TaskManagement.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Manages the initial setup of the admin account.
 *
 * This component will execute only ONCE in the initial deployment of the app and its credentials should be changed
 */
@Order(2)
@Component
public class AdminSetup implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final String adminUser;

    private final String adminPass;

    @Autowired
    public AdminSetup(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, @Value("${auth.admin.username}") String adminUser, @Value("${auth.admin.password}") String adminPass) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUser = adminUser;
        this.adminPass = adminPass;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.findByUsername(adminUser).isEmpty()){
            User admin = new User();
            admin.setUsername(adminUser);
            admin.setPassword(passwordEncoder.encode(adminPass));

            Role role = roleRepository.findByName("ADMIN").orElseThrow();

            admin.getRoles().add(role);

            userRepository.save(admin);
        }
    }
}
