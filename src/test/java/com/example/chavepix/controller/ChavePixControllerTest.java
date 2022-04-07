package com.example.chavepix.controller;

import com.example.chavepix.ApplicationConfigTest;
import com.example.chavepix.exceptions.DataNotFoundException;
import com.example.chavepix.exceptions.InvalidDataException;
import com.example.chavepix.model.ChavePix;
import com.example.chavepix.model.ChavePixDTO;
import com.example.chavepix.service.ChavePixService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@DisplayName("ChavePixControllerTest")
@SpringBootTest
public class ChavePixControllerTest extends ApplicationConfigTest {

    @Autowired
    private ChavePixController controller;

    @MockBean
    private ChavePixService service;

    @Test
    @DisplayName("Deve retornar a resposta OK e a chave pix")
    public void findChavePixById() throws DataNotFoundException {
        UUID id = UUID.randomUUID();
        ChavePix chavePix = criarChavePix();

        Mockito.when(service.findChavePixById(id)).thenReturn(chavePix);

        controller.findChavePixById(id);
    }

    @Test
    @DisplayName("Deve retornar a resposta OK as chaves pix")
    public void findChavePixByTipoChave() throws InvalidDataException {
        String tipoChave = "cpf";
        List<ChavePix> listaChavePix = criarListaChavePix();

        Mockito.when(service.findChavePixByTipoChave(tipoChave)).thenReturn(listaChavePix);

        controller.findChavePixByTipoChave(tipoChave);
    }

    @Test
    @DisplayName("Deve retornar a resposta OK as chaves pix")
    public void findChavePixByNome() throws InvalidDataException, DataNotFoundException {
        String nome = "Leo";
        List<ChavePix> listaChavePix = criarListaChavePix();

        Mockito.when(service.findChavePixByNome(nome)).thenReturn(listaChavePix);

        controller.findChavePixByNome(nome);
    }

    @Test
    @DisplayName("Deve retornar a resposta OK e o id")
    public void createChavePix() throws InvalidDataException {
        ChavePix chavePix = criarChavePix();
        Mockito.when(service.createChavePix(chavePix)).thenReturn(chavePix);

        controller.createChavePix(chavePix);
    }

    @Test
    @DisplayName("Deve retornar a resposta OK e o DTO")
    public void updateChavePix() throws InvalidDataException, DataNotFoundException {
        ChavePix chavePix = criarChavePix();
        ChavePixDTO chavePixDTO = criarChavePixDTO();

        Mockito.when(service.updateChavePix(chavePix)).thenReturn(chavePixDTO);

        controller.updateChavePix(chavePix);
    }

    private ChavePix criarChavePix() {
        ChavePix chavePix = Mockito.mock(ChavePix.class);
        return chavePix;
    }

    private ChavePixDTO criarChavePixDTO() {
        ChavePixDTO chavePix = Mockito.mock(ChavePixDTO.class);
        return chavePix;
    }

    private List<ChavePix> criarListaChavePix() {
        List<ChavePix> listaChavePix = Mockito.mock(List.class);
        return listaChavePix;
    }
}
