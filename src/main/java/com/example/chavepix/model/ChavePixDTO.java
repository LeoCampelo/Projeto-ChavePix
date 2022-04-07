package com.example.chavepix.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChavePixDTO {

    private UUID id;

    private String tipoChave;

    private String valorChave;

    private String tipoConta;

    private Integer agencia;

    private Integer conta;

    private String nomeCorrentista;

    private String sobrenomeCorrentista;

    private LocalDateTime dataCriacao;

    public ChavePixDTO(ChavePix chavePix) {

        this.id = chavePix.getId();
        this.tipoChave = chavePix.getTipoChave();
        this.valorChave = chavePix.getValorChave();
        this.tipoConta = chavePix.getTipoConta();
        this.agencia = chavePix.getAgencia();
        this.conta = chavePix.getConta();
        this.nomeCorrentista = chavePix.getNomeCorrentista();
        this.sobrenomeCorrentista = chavePix.getSobrenomeCorrentista();
        this.dataCriacao = chavePix.getDataCriacao();
    }


}
