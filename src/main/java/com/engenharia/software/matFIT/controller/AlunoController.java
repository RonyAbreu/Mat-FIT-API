package com.engenharia.software.matFIT.controller;

import com.engenharia.software.matFIT.dto.AlunoDTO;
import com.engenharia.software.matFIT.dto.AlunoRequest;
import com.engenharia.software.matFIT.entity.Aluno;
import com.engenharia.software.matFIT.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
public class AlunoController {
    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody AlunoRequest aluno){
        return ResponseEntity.status(201).body(alunoService.cadastrarAluno(aluno));
    }

    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<Void> removerAluno(@PathVariable String cpf){
        alunoService.removerAluno(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos(){
        return ResponseEntity.ok(alunoService.listarAlunos());
    }

    @PutMapping(value = "/{cpf}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable String cpf, @RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.ok(alunoService.atualizarAluno(cpf, alunoDTO));
    }

    @PatchMapping(value = "/pagamento/{cpf}")
    public ResponseEntity<Aluno> verificaPagamento(@PathVariable String cpf) {
        return ResponseEntity.ok(alunoService.verificaPagamento(cpf));
    }

    @PatchMapping(value = "/pagar/{cpf}")
    public ResponseEntity<Void> realizarPagamento(@PathVariable String cpf) {
        alunoService.realizarPagamento(cpf);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Aluno> buscarAluno(@PathVariable String cpf) {
        return ResponseEntity.ok(alunoService.buscarAluno(cpf));
    }
}
