package br.com.lucas.repositories;

import br.com.lucas.repositories.entities.EnderecoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<EnderecoEntity, Long> {
}
