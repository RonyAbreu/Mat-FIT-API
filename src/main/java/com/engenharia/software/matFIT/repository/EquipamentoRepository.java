package com.engenharia.software.matFIT.repository;

import com.engenharia.software.matFIT.entity.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Equipamento findByNomeIgnoreCase(String nome);
}
