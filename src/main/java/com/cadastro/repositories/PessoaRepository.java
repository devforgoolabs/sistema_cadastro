package com.cadastro.repositories;

import com.cadastro.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

//Colocar o @Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{


}
