package com.cadastro.Controller;

import com.cadastro.Exception.ResourceNotFoundException;
import com.cadastro.model.Pessoa;
import com.cadastro.repositories.PessoaRepository;
import com.cadastro.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:8080")
@RequestMapping(path = "/api/pessoa")
@Service
public class PessoaInsertController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @PostMapping(path = "/salvar")
    public ResponseEntity<?> salva(@RequestBody Pessoa pessoa){
        Pessoa obj = pessoaService.inserir(pessoa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(String.format("Salvo com Sucesso!! \nId: " + obj.getId()
                + " \nNome: " + obj.getNome()));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable(value = "id") Long pessoaId,
                                               @Valid @RequestBody Pessoa pessoaDetails) throws ResourceNotFoundException {
        Pessoa pessoa = pessoaRepository.findById((pessoaId))
                .orElseThrow(()-> new ResourceNotFoundException("Person not found for this id::" + pessoaId));

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
                .orElseThrow(()-> new ResourceNotFoundException("Person not found for this id: "+ pessoaId));

        pessoaRepository.delete(pessoa);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deletado com sucesso", Boolean.TRUE);

        return response;
    }
}
