package com.engenharia.software.matFIT.controller;

import com.engenharia.software.matFIT.dto.AlunoDTO;
import com.engenharia.software.matFIT.dto.AlunoRequest;
import com.engenharia.software.matFIT.entity.Aluno;
import com.engenharia.software.matFIT.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
@Tag(name = "Aluno")
public class AlunoController {
    private AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Operation(summary = "Cadastrar aluno", tags = "Aluno")
    @PostMapping
    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody AlunoRequest aluno){
        return ResponseEntity.status(201).body(alunoService.cadastrarAluno(aluno));
    }

    @Operation(summary = "Deletar aluno pelo cpf", tags = "Aluno")
    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<Void> removerAluno(@PathVariable String cpf){
        alunoService.removerAluno(cpf);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar alunos", tags = "Aluno")
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos(){
        return ResponseEntity.ok(alunoService.listarAlunos());
    }

    @Operation(summary = "Atualizar aluno pelo cpf", tags = "Aluno")
    @PutMapping(value = "/{cpf}")
    public ResponseEntity<Aluno> atualizarAluno(@PathVariable String cpf, @RequestBody AlunoDTO alunoDTO) {
        return ResponseEntity.ok(alunoService.atualizarAluno(cpf, alunoDTO));
    }

    @Operation(summary = "Verificar pagamento do aluno pelo cpf", tags = "Aluno")
    @PatchMapping(value = "/pagamento/{cpf}")
    public ResponseEntity<Aluno> verificaPagamento(@PathVariable String cpf) {
        return ResponseEntity.ok(alunoService.verificaPagamento(cpf));
    }

    @Operation(summary = "Realizar Pagamento do aluno pelo cpf", tags = "Aluno")
    @PatchMapping(value = "/pagar/{cpf}")
    public ResponseEntity<Void> realizarPagamento(@PathVariable String cpf) {
        alunoService.realizarPagamento(cpf);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar aluno pelo cpf", tags = "Aluno")
    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Aluno> buscarAluno(@PathVariable String cpf) {
        return ResponseEntity.ok(alunoService.buscarAluno(cpf));
    }
}
