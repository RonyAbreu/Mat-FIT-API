package com.engenharia.software.matFIT.service;

import com.engenharia.software.matFIT.dto.AlunoDTO;
import com.engenharia.software.matFIT.entity.Aluno;
import com.engenharia.software.matFIT.exception.CpfInvalidoException;
import com.engenharia.software.matFIT.repository.AlunoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlunoService {
    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrarAluno(Aluno aluno){
        if (!isCPF(aluno.getCpf())) {
            throw new CpfInvalidoException("Cpf inválido!");
        }

        Aluno alunoExistente = alunoRepository.findByCpf(aluno.getCpf());

        if (alunoExistente != null) {
            throw new EntityExistsException("Esse CPF já foi cadastrado");
        }

        aluno.setDataPagamento(LocalDate.now().minusMonths(1));
        aluno.setPagamentoAtrasado(false);
        alunoRepository.save(aluno);

        return aluno;
    }

    private boolean isCPF(String cpf) {
        String cpfValido = cpf.replaceAll("\\D", "");
        if (cpfValido.length() != 11) {
            return false;
        }

        return !cpfValido.matches("(\\d)\\1{10}");
    }

    public Aluno buscarAluno(String cpf) {
        Aluno aluno = alunoRepository.findByCpf(cpf);

        if (aluno == null) {
            throw new EntityNotFoundException("Aluno não existe");
        }

        return aluno;
    }

    public void removerAluno(String cpf) {
        Aluno aluno = alunoRepository.findByCpf(cpf);

        if (aluno == null) {
            throw new EntityNotFoundException("Aluno não existe");
        }

        alunoRepository.delete(aluno);
    }

    public List<Aluno> listarAlunos(){
        return alunoRepository.findAll();
    }

    public Aluno atualizarAluno(String cpf, AlunoDTO alunoAtualizado) {
        Aluno alunoExistente = alunoRepository.findByCpf(cpf);

        if (alunoExistente == null) {
            throw new EntityNotFoundException("Aluno não existe");
        }

        atualizaDados(alunoExistente, alunoAtualizado);

        alunoRepository.save(alunoExistente);

        return alunoExistente;
    }

    private void atualizaDados(Aluno alunoExistente, AlunoDTO alunoAtualizado) {
        alunoExistente.setNome(alunoAtualizado.nome());
        alunoExistente.setEsporte(alunoAtualizado.esporte());
        alunoExistente.setDataPagamento(alunoAtualizado.diaPagamento());
    }

    public Aluno verificaPagamento(String cpf) {
        Aluno alunoExistente = alunoRepository.findByCpf(cpf);

        if (alunoExistente == null) {
            throw new EntityNotFoundException("Aluno não existe");
        }

        LocalDate dataAtual = LocalDate.now();
        LocalDate dataPagamento = alunoExistente.getDataPagamento();

        alunoExistente.setPagamentoAtrasado(dataAtual.isAfter(dataPagamento));
        alunoRepository.save(alunoExistente);

        return alunoExistente;
    }

    public void realizarPagamento(String cpf) {
        Aluno alunoExistente = alunoRepository.findByCpf(cpf);

        if (alunoExistente == null) {
            throw new EntityNotFoundException("Aluno não existe");
        }

        alunoExistente.setDataPagamento(LocalDate.now().plusMonths(1));
        alunoExistente.setPagamentoAtrasado(false);
        alunoRepository.save(alunoExistente);
    }

}
