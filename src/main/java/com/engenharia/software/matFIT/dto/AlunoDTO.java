package com.engenharia.software.matFIT.dto;

import java.time.LocalDate;

public record AlunoDTO(
         String nome,
         String esporte,
         LocalDate diaPagamento
) {
}
