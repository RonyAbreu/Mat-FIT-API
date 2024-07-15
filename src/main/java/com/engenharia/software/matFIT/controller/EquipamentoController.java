package com.engenharia.software.matFIT.controller;

import com.engenharia.software.matFIT.entity.Equipamento;
import com.engenharia.software.matFIT.service.EquipamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamento")
public class EquipamentoController {
    private EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }
    
    @PostMapping
    public ResponseEntity<Equipamento> cadastrarEquipamento(@RequestBody Equipamento equipamento){
        return ResponseEntity.status(201).body(equipamentoService.cadastrarEquipamento(equipamento));
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerEquipamento(@PathVariable Long id){
        equipamentoService.removerEquipamento(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Equipamento>> listarEquipamentos(){
        return ResponseEntity.ok(equipamentoService.listarEquipamentos());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Equipamento> buscarEquipamento(@PathVariable Long id){
        return ResponseEntity.ok(equipamentoService.buscarEquipamento(id));
    }
    
    @PutMapping(value = "/{id}")
    public ResponseEntity<Equipamento> atualizarEquipamento(@PathVariable Long id, @RequestBody Equipamento equipamentoDTO) {
        return ResponseEntity.ok(equipamentoService.atualizarEquipamento(id, equipamentoDTO));
    }
}
