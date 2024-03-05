package br.com.raissa.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raissa.blog.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

	List<Comentario> findByPostId(Long postId);
	Optional<Comentario> findByIdAndUsuarioEmail(Long id, String email);
	
}
