package com.example.chavepix.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "chave_pix")
public class ChavePix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(name = "valor_chave", nullable = false)
    private String valorChave;

    @Column(name = "tipo_chave", nullable = false)
    private String tipoChave;

    @Column(name = "tipo_conta", nullable = false)
    private String tipoConta;

    @Column(name = "agencia", nullable = false)
    private Integer agencia;

    @Column(name = "conta", nullable = false)
    private Integer conta;

    @Column(name = "nome_correntista", nullable = false)
    private String nomeCorrentista;

    @Column(name = "sobrenome_correntista")
    private String sobrenomeCorrentista;

    @Column(name = "pfpj", nullable = false)
    private String pfpj;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}
