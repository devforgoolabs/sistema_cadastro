package com.cadastro.services;

import com.cadastro.model.Pessoa;
import com.cadastro.repositories.PessoaRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

        @Autowired
        private PessoaRepository pessoaRepository;

        public List<Pessoa> lista(){
            return pessoaRepository.findAll();
        }

        public Pessoa busca(Long id) throws ObjectNotFoundException {
            Optional<Pessoa> obj = pessoaRepository.findById(id);

            return obj.orElseThrow(() -> new ObjectNotFoundException(
                    "Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName()));
        }

        public Pessoa inserir(Pessoa pessoa){
            return pessoaRepository.save(pessoa);
        }

        public Pessoa atualiza(Long id, Pessoa pessoa) throws ObjectNotFoundException {
            Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);

            if (pessoaSalva.isEmpty()){
                throw new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + id + ", Tipo: " + Pessoa.class.getName());
            }

            BeanUtils.copyProperties(pessoa, pessoaSalva.get(),"id");
            return pessoaRepository.save(pessoaSalva.get());
        }

        public void deleta(Long id) throws ObjectNotFoundException {
            Optional<Pessoa> pessoa = pessoaRepository.findById(id);
            if (pessoa.isEmpty()){
                throw new ObjectNotFoundException("Objeto com o Id: " + id + " Não existe!!");
            }
            pessoaRepository.deleteById(id);
        }
    }

