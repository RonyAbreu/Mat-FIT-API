package com.engenharia.software.matFIT.repository;

import com.engenharia.software.matFIT.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, String> {
    Funcionario findByCpf(String cpf);
}
