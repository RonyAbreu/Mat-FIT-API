package com.engenharia.software.matFIT.service;


import com.engenharia.software.matFIT.entity.Equipamento;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.engenharia.software.matFIT.repository.EquipamentoRepository;

import java.util.List;

@Service
public class EquipamentoService {
    private EquipamentoRepository equipamentoRepository;

    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    public Equipamento cadastrarEquipamento(Equipamento equipamento){
        Equipamento equipamentoExistente = equipamentoRepository.findByNomeIgnoreCase(equipamento.getNome());

        if (equipamentoExistente != null) {
            throw new EntityExistsException("Esse equipamento já foi cadastrado");
        }

        equipamentoRepository.save(equipamento);

        return equipamentoExistente;
    }

    public Equipamento buscarEquipamento(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
    }

    public void removerEquipamento(Long id) {
        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        equipamentoRepository.delete(equipamento);
    }

    public List<Equipamento> listarEquipamentos(){
        return equipamentoRepository.findAll();
    }

    public Equipamento atualizarEquipamento(Long id, Equipamento equipamentoAtualizado) {
        Equipamento equipamentoExistente = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        atualizaDados(equipamentoExistente, equipamentoAtualizado);

        equipamentoRepository.save(equipamentoExistente);

        return equipamentoExistente;
    }

    private void atualizaDados(Equipamento equipamentoExistente, Equipamento equipamentoAtualizado) {
        equipamentoExistente.setNome(equipamentoAtualizado.getNome());
        equipamentoExistente.setQuantidade(equipamentoAtualizado.getQuantidade());
    }
}
