package com.engenharia.software.matFIT.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<RespostaDeErro> entidadeJaExiste(EntityExistsException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RespostaDeErro respostaDeErro = new RespostaDeErro(exception.getMessage(), status.value());
        return ResponseEntity.status(status).body(respostaDeErro);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RespostaDeErro> entidadeNaoExiste(EntityNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        RespostaDeErro respostaDeErro = new RespostaDeErro(exception.getMessage(), status.value());
        return ResponseEntity.status(status).body(respostaDeErro);
    }

    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<RespostaDeErro> cpfInvalido(CpfInvalidoException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RespostaDeErro respostaDeErro = new RespostaDeErro(exception.getMessage(), status.value());
        return ResponseEntity.status(status).body(respostaDeErro);
    }
}
