package com.sekarre.helpcenterauth.bootloader;

import com.sekarre.helpcenterauth.domain.Role;
import com.sekarre.helpcenterauth.domain.User;
import com.sekarre.helpcenterauth.domain.enums.RoleName;
import com.sekarre.helpcenterauth.domain.enums.Specialization;
import com.sekarre.helpcenterauth.repositories.RoleRepository;
import com.sekarre.helpcenterauth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class Bootloader implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoles();
        createUsers();
    }

    private void createRoles() {
        if (roleRepository.count() != 0) {
            return;
        }
        roleRepository.save(Role.builder()
                .name(RoleName.BASIC)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.SUPPORT)
                .specialization(Specialization.GAMES)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.SUPPORT)
                .specialization(Specialization.SOCIAL)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.SUPPORT)
                .specialization(Specialization.GENERAL_TECH)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.SUPPORT)
                .specialization(Specialization.GAME_CLIENT)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.SUPPORT)
                .specialization(Specialization.ACCOUNT)
                .build());
        roleRepository.save(Role.builder()
                .name(RoleName.ADMIN)
                .build());
    }

    private void createUsers() {
        if (userRepository.count() != 0) {
            return;
        }
        createNormalUsers();
        createAdminUser();
        createSupportUsers();
    }

    private void createNormalUsers() {
        Role role = roleRepository.findByName(RoleName.BASIC).get();

        userRepository.save(User.builder()
                .username("test1")
                .password(passwordEncoder.encode("test1"))
                .roles(Collections.singleton(role))
                .firstName("Adam")
                .lastName("Kowalski")
                .build());

        userRepository.save(User.builder()
                .username("test2")
                .password(passwordEncoder.encode("test2"))
                .roles(Collections.singleton(role))
                .firstName("Aneta")
                .lastName("Nowak")
                .build());


        userRepository.save(User.builder()
                .username("test3")
                .password(passwordEncoder.encode("test3"))
                .roles(Collections.singleton(role))
                .firstName("Mateusz")
                .lastName("Lewandowski")
                .build());
    }

    private void createSupportUsers() {
        Role role = roleRepository.findByNameAndSpecialization(RoleName.SUPPORT, Specialization.GAMES).get();

        userRepository.save(User.builder()
                .username("sup1")
                .password(passwordEncoder.encode("sup1"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_1")
                .build());

        userRepository.save(User.builder()
                .username("sup2")
                .password(passwordEncoder.encode("sup2"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_2")
                .build());


        userRepository.save(User.builder()
                .username("sup3")
                .password(passwordEncoder.encode("sup3"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_3")
                .build());

    }

    private void createAdminUser() {
        Role role = roleRepository.findByName(RoleName.ADMIN).get();

        userRepository.save(User.builder()
                .username("admin1")
                .password(passwordEncoder.encode("admin"))
                .roles(Collections.singleton(role))
                .firstName("INNOS")
                .lastName("_ADMIN")
                .build());
    }
}
