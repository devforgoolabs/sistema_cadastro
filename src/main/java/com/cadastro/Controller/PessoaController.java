package com.cadastro.Controller;

import com.cadastro.Exception.ResourceNotFoundException;
import com.cadastro.model.Pessoa;
import com.cadastro.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
@RequestMapping(path = "/api/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping(path = "/")
    public List<Pessoa> getAllPessoa(){

        return pessoaRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@PathVariable(value = "id") long pessoaId)
            throws ResourceNotFoundException {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).
                orElseThrow(()-> new ResourceNotFoundException("Pessoa not found for this id:: " + pessoaId));
        return ResponseEntity.ok().body(pessoa);
    }

    @PostMapping(path = "/salvar")
    public Pessoa salvar(@Valid @RequestBody Pessoa pessoa){
        return pessoaRepository.save(pessoa);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable(value = "id") Long pessoaId,
                                               @Valid @RequestBody Pessoa pessoaDetails) throws ResourceNotFoundException {
        Pessoa pessoa = pessoaRepository.findById((pessoaId))
                .orElseThrow(()-> new ResourceNotFoundException("Pessoa not found for this id::" + pessoaId));

        pessoa.setNome(pessoaDetails.getNome());
        pessoa.setCpf(pessoaDetails.getCpf());
        pessoa.setDataNascimento(pessoaDetails.getDataNascimento());
        pessoa.setNomeMae(pessoaDetails.getNomeMae());
        pessoa.setEmail(pessoaDetails.getEmail());
        pessoa.setObservacao(pessoaDetails.getObservacao());


        final Pessoa updatePessoa = pessoaRepository.save(pessoa);
        return ResponseEntity.ok(updatePessoa);
    }

    @DeleteMapping(path = "/{id}")
    public Map<String, Boolean> deletePessoa(@PathVariable(value = "id") Long pessoaId)
            throws ResourceNotFoundException {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(()-> new ResourceNotFoundException("Pessoa not found for this id: "+ pessoaId));

        pessoaRepository.delete(pessoa);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
