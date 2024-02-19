package br.com.cedup.trabalho1.model.pessoa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends CrudRepository<PessoaEntity, Integer> {

    @Query("SELECT p FROM PessoaEntity p WHERE p.idade > ?1") //JPQL ou HQL
    List<PessoaEntity> findByIdadeGreaterThan(int idade);

}
