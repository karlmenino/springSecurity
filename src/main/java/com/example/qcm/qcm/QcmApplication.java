package com.example.qcm.qcm;

import com.example.qcm.qcm.entity.Role;
import com.example.qcm.qcm.entity.RoleEnum;
import com.example.qcm.qcm.entity.User;
import com.example.qcm.qcm.repository.RoleRepository;
import com.example.qcm.qcm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class QcmApplication {

	public static void main(String[] args) {
		SpringApplication.run(QcmApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder ) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				User userAdmin = new User();
				userAdmin.setUsername("admin");
				userAdmin.setPassword(passwordEncoder.encode("admin"));
				Role role1=new Role(RoleEnum.ROLE_USER);
				Role role2=new Role(RoleEnum.ROLE_ADMIN);
				List<Role> roles = Arrays.asList(role1, role2);
				userAdmin.setRoles(roles);
				roleRepository.saveAll(roles);
				userRepository.save(userAdmin);
			}
		};
	}
}
