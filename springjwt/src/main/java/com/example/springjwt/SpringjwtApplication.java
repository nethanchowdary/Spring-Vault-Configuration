package com.example.springjwt;

import com.example.springjwt.entity.Users;
import com.example.springjwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringjwtApplication {

	@Autowired
	private UserRepo repo;

	@PostConstruct
	public void users(){
		List<Users> users= Stream.of(new Users(1,"nethan", "password", "nethan@gmail.com"),
				new Users(2, "chandu", "passsword1", "chandu@gmail.com"),
				new Users(3, "arunima", "password2", "arunima@gmail.com")).collect(Collectors.toList());
		repo.saveAll(users);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringjwtApplication.class, args);
	}

}
