package com.rishabh.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rishabh.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
		
	}
	
	@Test
	public void userRepoTest() {
		String className = userRepo.getClass().getName();
		String packacgeName = userRepo.getClass().getPackageName();
		
		System.out.println("Class Name: "+className);
		System.out.println("Package Name: "+packacgeName );
	}
}
