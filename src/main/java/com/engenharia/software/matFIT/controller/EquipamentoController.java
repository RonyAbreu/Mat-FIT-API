package com.engenharia.software.matFIT.controller;

import com.engenharia.software.matFIT.entity.Equipamento;
import com.engenharia.software.matFIT.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamento")
@Tag(name = "Equipamento")
public class EquipamentoController {
    private EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    @Operation(tags = "Equipamento", summary = "Cadastrar equipamento")
    @PostMapping
    public ResponseEntity<Equipamento> cadastrarEquipamento(@RequestBody Equipamento equipamento){
        return ResponseEntity.status(201).body(equipamentoService.cadastrarEquipamento(equipamento));
    }

    @Operation(tags = "Equipamento", summary = "Deletar equipamento pelo id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerEquipamento(@PathVariable Long id){
        equipamentoService.removerEquipamento(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(tags = "Equipamento", summary = "Listar equipamentos")
    @GetMapping
    public ResponseEntity<List<Equipamento>> listarEquipamentos(){
        return ResponseEntity.ok(equipamentoService.listarEquipamentos());
    }

    @Operation(tags = "Equipamento", summary = "Buscar equipamento pelo id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Equipamento> buscarEquipamento(@PathVariable Long id){
        return ResponseEntity.ok(equipamentoService.buscarEquipamento(id));
    }

    @Operation(tags = "Equipamento", summary = "Atualizar equipamento pelo id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Equipamento> atualizarEquipamento(@PathVariable Long id, @RequestBody Equipamento equipamentoDTO) {
        return ResponseEntity.ok(equipamentoService.atualizarEquipamento(id, equipamentoDTO));
    }
}
