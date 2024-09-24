package com.engenharia.software.matFIT.service;

import com.engenharia.software.matFIT.dto.AlunoDTO;
import com.engenharia.software.matFIT.dto.AlunoRequest;
import com.engenharia.software.matFIT.entity.Aluno;
import com.engenharia.software.matFIT.exception.CpfInvalidoException;
import com.engenharia.software.matFIT.repository.AlunoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarAluno_CpfInvalido() {
        AlunoRequest alunoRequest = new AlunoRequest("123456789", "Nome Teste", "Academia");

        Exception exception = assertThrows(CpfInvalidoException.class, () -> {
            alunoService.cadastrarAluno(alunoRequest);
        });

        assertEquals("Cpf inválido!", exception.getMessage());
    }

    @Test
    void testCadastrarAluno_AlunoJaCadastrado() {
        AlunoRequest alunoRequest = new AlunoRequest("12345678900", "Nome Teste", "Academia");
        Aluno alunoExistente = new Aluno("12345678900", "Nome Teste", "Academia");

        when(alunoRepository.findByCpf(alunoRequest.cpf())).thenReturn(alunoExistente);

        Exception exception = assertThrows(EntityExistsException.class, () -> {
            alunoService.cadastrarAluno(alunoRequest);
        });

        assertEquals("Esse CPF já foi cadastrado", exception.getMessage());
    }

    @Test
    void testCadastrarAluno_Sucesso() {
        AlunoRequest alunoRequest = new AlunoRequest("12345678900", "Nome Teste", "Academia");

        when(alunoRepository.findByCpf(alunoRequest.cpf())).thenReturn(null);

        Aluno alunoCadastrado = alunoService.cadastrarAluno(alunoRequest);

        assertNotNull(alunoCadastrado);
        assertEquals("12345678900", alunoCadastrado.getCpf());
        assertEquals("Nome Teste", alunoCadastrado.getNome());
        assertEquals(LocalDate.now().plusMonths(1), alunoCadastrado.getDataPagamento());
        assertFalse(alunoCadastrado.getPagamentoAtrasado());
        verify(alunoRepository).save(alunoCadastrado);
    }

    @Test
    void testBuscarAluno_AlunoNaoEncontrado() {
        when(alunoRepository.findByCpf("12345678900")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            alunoService.buscarAluno("12345678900");
        });

        assertEquals("Aluno não existe", exception.getMessage());
    }

    @Test
    void testBuscarAluno_Sucesso() {
        Aluno aluno = new Aluno("12345678900", "Nome Teste", "Academia");
        when(alunoRepository.findByCpf("12345678900")).thenReturn(aluno);

        Aluno alunoEncontrado = alunoService.buscarAluno("12345678900");

        assertNotNull(alunoEncontrado);
        assertEquals("Nome Teste", alunoEncontrado.getNome());
    }

    @Test
    void testRemoverAluno_AlunoNaoEncontrado() {
        when(alunoRepository.findByCpf("12345678900")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            alunoService.removerAluno("12345678900");
        });

        assertEquals("Aluno não existe", exception.getMessage());
    }

    @Test
    void testRemoverAluno_Sucesso() {
        Aluno aluno = new Aluno("12345678900", "Nome Teste" , "Academia");
        when(alunoRepository.findByCpf("12345678900")).thenReturn(aluno);

        alunoService.removerAluno("12345678900");

        verify(alunoRepository).delete(aluno);
    }

    @Test
    void testListarAlunos() {
        Aluno aluno = new Aluno("12345678900", "Nome Teste", "Academia");
        when(alunoRepository.findAll()).thenReturn(Collections.singletonList(aluno));

        var alunos = alunoService.listarAlunos();

        assertEquals(1, alunos.size());
        assertEquals("Nome Teste", alunos.get(0).getNome());
    }

    @Test
    void testAtualizarAluno_AlunoNaoEncontrado() {
        when(alunoRepository.findByCpf("12345678900")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            alunoService.atualizarAluno("12345678900", new AlunoDTO("Nome Atualizado", "Novo Esporte"));
        });

        assertEquals("Aluno não existe", exception.getMessage());
    }

    @Test
    void testAtualizarAluno_Sucesso() {
        Aluno alunoExistente = new Aluno("12345678900", "Nome Teste", "Academia");
        when(alunoRepository.findByCpf("12345678900")).thenReturn(alunoExistente);

        AlunoDTO alunoAtualizado = new AlunoDTO("Nome Atualizado", "Novo Esporte");
        Aluno alunoResultado = alunoService.atualizarAluno("12345678900", alunoAtualizado);

        assertEquals("Nome Atualizado", alunoResultado.getNome());
        assertEquals("Novo Esporte", alunoResultado.getEsporte());
        verify(alunoRepository).save(alunoExistente);
    }

    @Test
    void testVerificaPagamento_AlunoNaoEncontrado() {
        when(alunoRepository.findByCpf("12345678900")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            alunoService.verificaPagamento("12345678900");
        });

        assertEquals("Aluno não existe", exception.getMessage());
    }

    @Test
    void testVerificaPagamento_Sucesso() {
        Aluno aluno = new Aluno("12345678900", "Nome Teste", "Academia");
        aluno.setDataPagamento(LocalDate.now().minusDays(1));
        when(alunoRepository.findByCpf("12345678900")).thenReturn(aluno);

        Aluno alunoVerificado = alunoService.verificaPagamento("12345678900");

        assertTrue(alunoVerificado.getPagamentoAtrasado());
        verify(alunoRepository).save(aluno);
    }

    @Test
    void testRealizarPagamento_AlunoNaoEncontrado() {
        when(alunoRepository.findByCpf("12345678900")).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            alunoService.realizarPagamento("12345678900");
        });

        assertEquals("Aluno não existe", exception.getMessage());
    }

    @Test
    void testRealizarPagamento_Sucesso() {
        Aluno aluno = new Aluno("12345678900", "Nome Teste", "Academia");
        when(alunoRepository.findByCpf("12345678900")).thenReturn(aluno);

        alunoService.realizarPagamento("12345678900");

        assertEquals(LocalDate.now().plusMonths(1), aluno.getDataPagamento());
        assertFalse(aluno.getPagamentoAtrasado());
        verify(alunoRepository).save(aluno);
    }
}
