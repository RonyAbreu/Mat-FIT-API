package com.engenharia.software.matFIT.controller;

import com.engenharia.software.matFIT.dto.FuncionarioDTO;
import com.engenharia.software.matFIT.entity.Aluno;
import com.engenharia.software.matFIT.entity.Funcionario;
import com.engenharia.software.matFIT.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
@Tag(name = "Funcionario")
public class FuncionarioController {
    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(tags = "Funcionario", summary = "Cadastrar funcionario")
    @PostMapping
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario funcionario){
        return ResponseEntity.status(201).body(funcionarioService.cadastrarFuncionario(funcionario));
    }

    @Operation(tags = "Funcionario", summary = "Deletar funcionario pelo cpf")
    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable String cpf){
        funcionarioService.removerFuncionario(cpf);
        return ResponseEntity.noContent().build();
    }

    @Operation(tags = "Funcionario", summary = "Listar funcionarios")
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios(){
        return ResponseEntity.ok(funcionarioService.listarFuncionarios());
    }

    @Operation(tags = "Funcionario", summary = "Buscar funcionario pelo cpf")
    @GetMapping(value = "/{cpf}")
    public ResponseEntity<Funcionario> buscarFuncionario(@PathVariable String cpf) {
        return ResponseEntity.ok(funcionarioService.buscarFuncionario(cpf));
    }

    @Operation(tags = "Funcionario", summary = "Atualizar funcionario pelo cpf")
    @PutMapping(value = "/{cpf}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable String cpf, @RequestBody FuncionarioDTO funcionarioDTO) {
        return ResponseEntity.ok(funcionarioService.atualizarFuncionario(cpf, funcionarioDTO));
    }
}
