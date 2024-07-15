package com.engenharia.software.matFIT.entity;

import com.engenharia.software.matFIT.dto.AlunoRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_aluno")
public class Aluno {
    @Id
    @Column(unique = true)
    private String cpf;
    private String nome;
    private String esporte;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;
    private Boolean pagamentoAtrasado;

    public Aluno(AlunoRequest aluno) {
        this.cpf = aluno.cpf();
        this.nome = aluno.nome();
        this.esporte = aluno.esporte();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Aluno aluno = (Aluno) object;
        return Objects.equals(cpf, aluno.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}
