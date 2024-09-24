package com.engenharia.software.matFIT.service;

import com.engenharia.software.matFIT.entity.Equipamento;
import com.engenharia.software.matFIT.repository.EquipamentoRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EquipamentoServiceTest {

    @Mock
    private EquipamentoRepository equipamentoRepository;

    @InjectMocks
    private EquipamentoService equipamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarEquipamento_EquipamentoJaCadastrado() {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Equipamento Teste");

        when(equipamentoRepository.findByNomeIgnoreCase("Equipamento Teste")).thenReturn(equipamento);

        Exception exception = assertThrows(EntityExistsException.class, () -> {
            equipamentoService.cadastrarEquipamento(equipamento);
        });

        assertEquals("Esse equipamento já foi cadastrado", exception.getMessage());
    }

    @Test
    void testCadastrarEquipamento_Sucesso() {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Equipamento Teste");

        when(equipamentoRepository.findByNomeIgnoreCase("Equipamento Teste")).thenReturn(null);

        Equipamento equipamentoCadastrado = equipamentoService.cadastrarEquipamento(equipamento);

        assertNotNull(equipamentoCadastrado);
        assertEquals("Equipamento Teste", equipamentoCadastrado.getNome());
        verify(equipamentoRepository).save(equipamento);
    }

    @Test
    void testBuscarEquipamento_EquipamentoNaoEncontrado() {
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipamentoService.buscarEquipamento(1L);
        });

        assertEquals("Equipamento não encontrado", exception.getMessage());
    }

    @Test
    void testBuscarEquipamento_Sucesso() {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Equipamento Teste");
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));

        Equipamento equipamentoEncontrado = equipamentoService.buscarEquipamento(1L);

        assertNotNull(equipamentoEncontrado);
        assertEquals("Equipamento Teste", equipamentoEncontrado.getNome());
    }

    @Test
    void testRemoverEquipamento_EquipamentoNaoEncontrado() {
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipamentoService.removerEquipamento(1L);
        });

        assertEquals("Equipamento não encontrado", exception.getMessage());
    }

    @Test
    void testRemoverEquipamento_Sucesso() {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Equipamento Teste");
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));

        equipamentoService.removerEquipamento(1L);

        verify(equipamentoRepository).delete(equipamento);
    }

    @Test
    void testListarEquipamentos() {
        Equipamento equipamento = new Equipamento();
        equipamento.setNome("Equipamento Teste");
        when(equipamentoRepository.findAll()).thenReturn(Collections.singletonList(equipamento));

        var equipamentos = equipamentoService.listarEquipamentos();

        assertEquals(1, equipamentos.size());
        assertEquals("Equipamento Teste", equipamentos.get(0).getNome());
    }

    @Test
    void testAtualizarEquipamento_EquipamentoNaoEncontrado() {
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            equipamentoService.atualizarEquipamento(1L, new Equipamento());
        });

        assertEquals("Equipamento não encontrado", exception.getMessage());
    }

    @Test
    void testAtualizarEquipamento_Sucesso() {
        Equipamento equipamentoExistente = new Equipamento();
        equipamentoExistente.setNome("Equipamento Teste");
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamentoExistente));

        Equipamento equipamentoAtualizado = new Equipamento();
        equipamentoAtualizado.setNome("Equipamento Atualizado");
        equipamentoAtualizado.setQuantidade(10);

        Equipamento equipamentoResultado = equipamentoService.atualizarEquipamento(1L, equipamentoAtualizado);

        assertEquals("Equipamento Atualizado", equipamentoResultado.getNome());
        assertEquals(10, equipamentoResultado.getQuantidade());
        verify(equipamentoRepository).save(equipamentoExistente);
    }
}
