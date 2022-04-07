package com.example.chavepix.service;

import com.example.chavepix.ApplicationConfigTest;
import com.example.chavepix.exceptions.DataNotFoundException;
import com.example.chavepix.exceptions.InvalidDataException;
import com.example.chavepix.model.ChavePix;
import com.example.chavepix.repository.ChavePixRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DisplayName("ChavePixServiceTest")
@SpringBootTest
public class ChavePixServiceTest extends ApplicationConfigTest {

    @Autowired
    private ChavePixService service;

    @MockBean
    private ChavePixRepository repository;

    @Test
    @DisplayName("Deve retornar a chave pix pelo id UUID")
    public void findChavePixById() throws DataNotFoundException {
        UUID id = UUID.randomUUID();

        Optional<ChavePix> chavePix = Optional.of(criarChavePix());
        Mockito.when(repository.findById(id)).thenReturn(chavePix);

        service.findChavePixById(id);
        Mockito.verify(repository, Mockito.times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar uma lista de chave pix pelo tipo de chave")
    public void findChavePixByTipoChave() throws InvalidDataException {
        String tipoChave = "cpf";

        List<ChavePix> listaChavePix = criarListaChavePix();
        Mockito.when(repository.findChavePixByTipoChave(tipoChave)).thenReturn(listaChavePix);

        service.findChavePixByTipoChave(tipoChave);
    }

    @Test
    @DisplayName("Deve retornar uma lista de chave pix pelo nome do correntista")
    public void findChavePixByNome() throws InvalidDataException, DataNotFoundException {
        String nome = "Leo";

        List<ChavePix> listaChavePix = criarListaChavePix();
        Mockito.when(repository.findChavePixByNome(nome)).thenReturn(listaChavePix);

        service.findChavePixByNome(nome);
    }

    @Test
    @DisplayName("Deve criar uma chave pix")
    public void createChavePix() throws InvalidDataException {
        ChavePix chavePix = criarChavePix();
        Mockito.when(repository.save(chavePix)).thenReturn(chavePix);

        service.createChavePix(chavePix);
    }

    @Test
    @DisplayName("Deve atualizar uma chave pix")
    public void updateChavePix() throws InvalidDataException, DataNotFoundException {
        ChavePix chavePix = criarChavePix();
        Mockito.when(repository.save(chavePix)).thenReturn(chavePix);

        service.updateChavePix(chavePix);
    }

    private ChavePix criarChavePix() {
        ChavePix chavePix = Mockito.mock(ChavePix.class);
        return chavePix;
    }


    private List<ChavePix> criarListaChavePix() {
        List<ChavePix> listaChavePix = Mockito.mock(List.class);
        return listaChavePix;
    }
}
