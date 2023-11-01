package com.codewithsaurabh.blog_app_apis;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithsaurabh.blog_app_apis.config.AppConstants;
import com.codewithsaurabh.blog_app_apis.entities.Role;
import com.codewithsaurabh.blog_app_apis.repositories.RoleRepo;

@SpringBootApplication
public class BackendBlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BackendBlogAppApisApplication.class, args);
 		System.err.println("Welcome to BackendBlogAppApisApplication!!!");
	}

	@Override
	public void run(String... args) throws Exception {
//       String sql = "INSERT INTO users (user_name, email,password,about) VALUES ("
//               + "'Saurabh', 'saurabh@gmail.com','saurabh','Software Developer')";
//   	
//       int rows = jdbcTemplate.update(sql);
//       if (rows > 0) {
//           System.out.println("A new row has been inserted.");
//       }

		System.out.println("abc@gmail.com");
		System.out.println(this.passwordEncoder.encode("abc"));

		try {
			// Role Check exist or not then create
			Role role1 = new Role();
			role1.setRoleId(AppConstants.ADMIN_USER);
			role1.setName("ADMIN_USER");

			Role role2 = new Role();
			role2.setRoleId(AppConstants.NORMAL_USER);
			role2.setName("NORMAL_USER");

//			List<Role> roles = List.of(role1, role2);
			List<Role> roles = new ArrayList<Role>();
			roles.add(role1);
			roles.add(role2);
			List<Role> savedRoleList = this.roleRepo.saveAll(roles);

			savedRoleList.forEach(r -> {
				System.out.println("RoleId: " + r.getRoleId());
				System.out.println("Role: " + r.getName());
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
