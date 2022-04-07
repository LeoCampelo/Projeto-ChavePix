package com.example.chavepix.repository;

import com.example.chavepix.model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, UUID> {

    @Query( "SELECT chavePix " +
            "FROM ChavePix chavePix " +
            "WHERE chavePix.valorChave = ?1 ")
    ChavePix findChaveByValor(String valorChave);

    @Query( "SELECT chavePix " +
            "FROM ChavePix chavePix " +
            "WHERE chavePix.conta = ?1 ")
    List<ChavePix> findChavePixByConta(Integer conta);

    @Query( "SELECT chavePix " +
            "FROM ChavePix chavePix " +
            "WHERE chavePix.tipoChave = ?1 ")
    List<ChavePix> findChavePixByTipoChave(String tipoChave);

    @Query( "SELECT chavePix " +
            "FROM ChavePix chavePix " +
            "WHERE chavePix.nomeCorrentista = ?1 ")
    List<ChavePix> findChavePixByNome(String nome);
}
