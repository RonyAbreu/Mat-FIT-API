package com.engenharia.software.matFIT.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RespostaDeErro {
    private String mensagem;
    private Integer status;

}
