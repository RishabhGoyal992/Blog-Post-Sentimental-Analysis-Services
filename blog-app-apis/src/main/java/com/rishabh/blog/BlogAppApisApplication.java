package com.rishabh.blog;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rishabh.blog.config.ApplicationConstants;
import com.rishabh.blog.entities.Role;
import com.rishabh.blog.repositories.RoleRepo;

import antlr.collections.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Role roleAdmin = new Role();
			roleAdmin.setRoleId(ApplicationConstants.ADMIN_USER_ROLE_ID);
			roleAdmin.setRoleName("ROLE_ADMIN");
			
			Role roleNormal = new Role();
			roleNormal.setRoleId(ApplicationConstants.NORMAL_USER_ROLE_ID);
			roleNormal.setRoleName("ROLE_NORMAL");
			
			ArrayList<Role> aRoles = new ArrayList<>();		
			aRoles.add(roleAdmin);
			aRoles.add(roleNormal);

			this.roleRepo.saveAll(aRoles);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	
}
