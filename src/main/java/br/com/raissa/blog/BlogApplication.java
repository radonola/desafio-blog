package br.com.raissa.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.raissa.blog.model.Role;
import br.com.raissa.blog.repository.RoleRepository;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		roleRepository.save(Role.builder().id(1).nome("USER").build());
	}

}
