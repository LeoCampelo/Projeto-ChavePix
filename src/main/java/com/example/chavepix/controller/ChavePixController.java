package com.example.chavepix.controller;

import com.example.chavepix.exceptions.DataNotFoundException;
import com.example.chavepix.exceptions.InvalidDataException;
import com.example.chavepix.model.ChavePix;
import com.example.chavepix.model.ChavePixDTO;
import com.example.chavepix.service.ChavePixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@RestController
@RequestMapping(value="/pix")
public class ChavePixController {

    @Autowired
    private ChavePixService service;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> findChavePixById(@PathVariable @NotNull UUID id) throws DataNotFoundException {
        return ResponseEntity.ok(service.findChavePixById(id));
    }

    @RequestMapping(value = "/tipoChave/{tipoChave}",method = RequestMethod.GET)
    public ResponseEntity<?> findChavePixByTipoChave(@PathVariable @NotNull String tipoChave) throws InvalidDataException {
        return ResponseEntity.ok(service.findChavePixByTipoChave(tipoChave));
    }

    @RequestMapping(value = "/nome/{nome}",method = RequestMethod.GET)
    public ResponseEntity<?> findChavePixByNome(@PathVariable @NotNull String nome) throws InvalidDataException, DataNotFoundException {
        return ResponseEntity.ok(service.findChavePixByNome(nome));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createChavePix(@RequestBody ChavePix chavePix) throws InvalidDataException {
        ChavePix response = service.createChavePix(chavePix);
        return ResponseEntity.ok(response.getId());
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateChavePix(@RequestBody ChavePix chavePix) throws InvalidDataException, DataNotFoundException {
        ChavePixDTO response = service.updateChavePix(chavePix);
        return ResponseEntity.ok(response);
    }

}
