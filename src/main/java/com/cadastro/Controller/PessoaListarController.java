package com.cadastro.Controller;

import com.cadastro.Exception.ResourceNotFoundException;
import com.cadastro.model.Pessoa;
import com.cadastro.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
@RequestMapping(path = "/api/pessoa")
public class PessoaListarController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping(path = "/")
    public List<Pessoa> getAllPessoa() {

        return pessoaRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable(value = "id") long pessoaId)
            throws ResourceNotFoundException {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).
                orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada:: " + pessoaId));
        return ResponseEntity.ok().body(pessoa);
    }
}