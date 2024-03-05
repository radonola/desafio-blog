package br.com.raissa.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raissa.blog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	Optional<Post> findByIdAndUsuarioEmail(Long id, String email);
	
}
