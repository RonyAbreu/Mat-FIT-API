package com.engenharia.software.matFIT.service;

import com.engenharia.software.matFIT.dto.FuncionarioDTO;
import com.engenharia.software.matFIT.entity.Funcionario;
import com.engenharia.software.matFIT.exception.CpfInvalidoException;
import com.engenharia.software.matFIT.repository.FuncionarioRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;
    
    private final String CPF_VALIDO = "12345678909";
    private final String CPF_INVALIDO = "111111111";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarFuncionario_CpfInvalido() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF_INVALIDO); 

        Exception exception = assertThrows(CpfInvalidoException.class, () -> {
            funcionarioService.cadastrarFuncionario(funcionario);
        });

        assertEquals("CPF inválido ou já cadastrado!", exception.getMessage());
    }

    @Test
    void testCadastrarFuncionario_EquipamentoJaCadastrado() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF_VALIDO); 

        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(funcionario);

        Exception exception = assertThrows(EntityExistsException.class, () -> {
            funcionarioService.cadastrarFuncionario(funcionario);
        });

        assertEquals("Esse CPF já foi cadastrado", exception.getMessage());
    }

    @Test
    void testCadastrarFuncionario_Sucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF_VALIDO); 

        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(null);

        Funcionario funcionarioCadastrado = funcionarioService.cadastrarFuncionario(funcionario);

        assertNotNull(funcionarioCadastrado);
        assertEquals(CPF_VALIDO, funcionarioCadastrado.getCpf());
        verify(funcionarioRepository).save(funcionario);
    }

    @Test
    void testRemoverFuncionario_FuncionarioNaoEncontrado() {
        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.removerFuncionario(CPF_VALIDO);
        });

        assertEquals("funcionario não existe", exception.getMessage());
    }

    @Test
    void testRemoverFuncionario_Sucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF_VALIDO);

        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(funcionario);

        funcionarioService.removerFuncionario(CPF_VALIDO);

        verify(funcionarioRepository).delete(funcionario);
    }

    @Test
    void testListarFuncionarios() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF_VALIDO);

        when(funcionarioRepository.findAll()).thenReturn(Collections.singletonList(funcionario));

        var funcionarios = funcionarioService.listarFuncionarios();

        assertEquals(1, funcionarios.size());
        assertEquals(CPF_VALIDO, funcionarios.get(0).getCpf());
    }

    @Test
    void testAtualizarFuncionario_FuncionarioNaoEncontrado() {
        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.atualizarFuncionario(CPF_VALIDO, new FuncionarioDTO("Teste", "Testador", 20));
        });

        assertEquals("Funcionario não existe", exception.getMessage());
    }

    @Test
    void testAtualizarFuncionario_Sucesso() {
        Funcionario funcionarioExistente = new Funcionario();
        funcionarioExistente.setCpf(CPF_VALIDO);

        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(funcionarioExistente);

        Funcionario funcionarioAtualizado = new Funcionario(CPF_VALIDO,"Teste", "Testador", 20);
        funcionarioAtualizado.setNome("Nome Atualizado");
        funcionarioAtualizado.setFuncao("Nova Função");
        funcionarioAtualizado.setCargaHoraria(40);

        Funcionario funcionarioResultado = funcionarioService.atualizarFuncionario(CPF_VALIDO,
                new FuncionarioDTO(
                        funcionarioAtualizado.getNome(),
                        funcionarioAtualizado.getFuncao(),
                        funcionarioAtualizado.getCargaHoraria()
                ));

        assertEquals("Nome Atualizado", funcionarioResultado.getNome());
        assertEquals("Nova Função", funcionarioResultado.getFuncao());
        assertEquals(40, funcionarioResultado.getCargaHoraria());
        verify(funcionarioRepository).save(funcionarioExistente);
    }

    @Test
    void testBuscarFuncionario_FuncionarioNaoEncontrado() {
        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            funcionarioService.buscarFuncionario(CPF_VALIDO);
        });

        assertEquals("Funcionario não existe", exception.getMessage());
    }

    @Test
    void testBuscarFuncionario_Sucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(CPF_VALIDO);

        when(funcionarioRepository.findByCpf(CPF_VALIDO)).thenReturn(funcionario);

        Funcionario funcionarioEncontrado = funcionarioService.buscarFuncionario(CPF_VALIDO);

        assertNotNull(funcionarioEncontrado);
        assertEquals(CPF_VALIDO, funcionarioEncontrado.getCpf());
    }
}
