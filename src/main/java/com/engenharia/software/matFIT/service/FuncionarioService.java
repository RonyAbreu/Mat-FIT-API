package com.engenharia.software.matFIT.service;

import com.engenharia.software.matFIT.dto.FuncionarioDTO;
import com.engenharia.software.matFIT.entity.Funcionario;
import com.engenharia.software.matFIT.repository.FuncionarioRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }
    
    public Funcionario cadastrarFuncionario(Funcionario funcionario){
        Funcionario funcionarioExistente = funcionarioRepository.findByCpf(funcionario.getCpf());
        
        if (funcionarioExistente != null) {
            throw new EntityExistsException("Esse CPF já foi cadastrado");
        }
        
        funcionarioRepository.save(funcionario);
        
        return funcionario;
    }
    
    public void removerFuncionario(String cpf) {
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf);

        if (funcionario == null) {
            throw new EntityNotFoundException("funcionario não existe");
        }
        
        funcionarioRepository.delete(funcionario);
    }
    
    public List<Funcionario> listarFuncionarios(){
        return funcionarioRepository.findAll();
    }
    
    public Funcionario atualizarFuncionario(String cpf, FuncionarioDTO FuncionarioAtualizado) {
        Funcionario funcionarioExistente = funcionarioRepository.findByCpf(cpf);

        if (funcionarioExistente == null) {
            throw new EntityNotFoundException("Funcionario não existe");
        }
        
        atualizaDados(funcionarioExistente, FuncionarioAtualizado);
        
        funcionarioRepository.save(funcionarioExistente);
        
        return funcionarioExistente;
    }

    private void atualizaDados(Funcionario funcionarioExistente, FuncionarioDTO funcionarioAtualizado) {
        funcionarioExistente.setNome(funcionarioAtualizado.nome());
        funcionarioExistente.setFuncao(funcionarioAtualizado.funcao());
        funcionarioExistente.setCargaHoraria(funcionarioAtualizado.cargaHoraria());
    }

    public Funcionario buscarFuncionario(String cpf) {
        Funcionario funcionarioExistente = funcionarioRepository.findByCpf(cpf);

        if (funcionarioExistente == null) {
            throw new EntityNotFoundException("Funcionario não existe");
        }

        return funcionarioExistente;
    }
}
