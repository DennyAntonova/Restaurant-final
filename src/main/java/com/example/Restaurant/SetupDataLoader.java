package com.example.Restaurant;

import com.example.Restaurant.entity.Privilege;
import com.example.Restaurant.entity.Role;
import com.example.Restaurant.entity.User;
import com.example.Restaurant.repository.PrivilegeRepository;
import com.example.Restaurant.repository.RoleRepository;
import com.example.Restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = true;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

//        if (alreadySetup) {
//            return;
//        }

        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> waiterPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        Role waiter = createRoleIfNotFound("ROLE_WAITER", waiterPrivileges);
        createRoleIfNotFound("ROLE_WAITER", List.of(writePrivilege));
        List<Privilege> cookPrivileges = Arrays.asList(readPrivilege, writePrivilege);
        Role cook = createRoleIfNotFound("ROLE_COOK", cookPrivileges);
        createRoleIfNotFound("ROLE_COOK", List.of(writePrivilege));

        User Ivan = new User();
        Ivan.setFirstName("Ivan");
        Ivan.setLastName("Petrov");
        Ivan.setPassword(passwordEncoder.encode("Ivan"));
        Ivan.setEmail("ivan@gmail.com");
        Ivan.setAge(35);
        Ivan.setRoles(List.of(waiter));
        userRepository.save(Ivan);

        User Maria = new User();
        Maria.setFirstName("Maria");
        Maria.setLastName("Petrova");
        Maria.setPassword(passwordEncoder.encode("Maria"));
        Maria.setEmail("maria@gmail.com");
        Maria.setAge(35);
        Maria.setRoles(List.of(waiter));
        userRepository.save(Maria);

        User Petyr = new User();
        Petyr.setFirstName("Petyr");
        Petyr.setLastName("Ivanov");
        Petyr.setPassword(passwordEncoder.encode("Petyr"));
        Petyr.setEmail("petyr@gmail.com");
        Petyr.setAge(33);
        Petyr.setRoles(List.of(cook));
        userRepository.save(Petyr);
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
