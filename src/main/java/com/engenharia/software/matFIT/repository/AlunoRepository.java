package com.engenharia.software.matFIT.repository;

import com.engenharia.software.matFIT.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, String> {
    Aluno findByCpf(String cpf);
}
