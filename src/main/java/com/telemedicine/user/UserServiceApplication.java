package com.telemedicine.user;

import com.telemedicine.user.model.dao.RoleDao;
import com.telemedicine.user.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;

@SpringBootApplication
@Slf4j
public class UserServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;


	@Override
	public void run(String... args) throws Exception {
		try {
			RoleDao patient = new RoleDao(1, "ROLE_PATIENT");
			RoleDao doctor = new RoleDao(2, "ROLE_DOCTOR");
			List<RoleDao> roles = List.of(patient, doctor);
			roleRepository.saveAll(roles);
			roles.forEach(role -> log.info(role.getId() +" " + role.getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
