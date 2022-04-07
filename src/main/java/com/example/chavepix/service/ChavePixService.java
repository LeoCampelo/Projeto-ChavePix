package com.example.chavepix.service;

import com.example.chavepix.exceptions.DataNotFoundException;
import com.example.chavepix.exceptions.InvalidDataException;
import com.example.chavepix.model.ChavePix;
import com.example.chavepix.model.ChavePixDTO;
import com.example.chavepix.repository.ChavePixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ChavePixService {

    @Autowired
    private ChavePixRepository repo;

    //GET
    public ChavePix findChavePixById(UUID id) throws DataNotFoundException {

        Optional<ChavePix> response = repo.findById(id);
        if(response.isPresent())
            return response.get();
        throw new DataNotFoundException("Chave não encontrada");
    }

    public List<ChavePix> findChavePixByTipoChave(String tipoChave) throws InvalidDataException {

        if(!tipoChave.equals("cpf") && !tipoChave.equals("cnpj") && !tipoChave.equals("email") &&
                !tipoChave.equals("celular") && !tipoChave.equals("aleatorio"))
            throw new InvalidDataException("Tipo de chave inválida");
        return repo.findChavePixByTipoChave(tipoChave);
    }

    public List<ChavePix> findChavePixByNome(String nome) throws InvalidDataException, DataNotFoundException {

        List<ChavePix> response = repo.findChavePixByNome(nome);

        if(!nome.matches("^[a-zA-Z]{1,30}$"))
            throw new InvalidDataException("Nome inválido");
        else if(response.isEmpty())
            throw new DataNotFoundException("Chaves não encontradas");

        return response;
    }

    //POST
    public ChavePix createChavePix(ChavePix chavePix) throws InvalidDataException {

        if(chavePix.getTipoChave() != null && chavePix.getValorChave() != null)
            validaChave(chavePix);

        chavePix.setAtiva(true);
        chavePix.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        return repo.save(chavePix);
    }

    //PUT
    public ChavePixDTO updateChavePix(ChavePix chavePix) throws InvalidDataException, DataNotFoundException {
        validaAlteracao(chavePix);
        return new ChavePixDTO(repo.save(chavePix));
    }

    private void validaAlteracao(ChavePix chaveAlterada) throws InvalidDataException, DataNotFoundException {
        Optional<ChavePix> chavePix = repo.findById(chaveAlterada.getId());

        if(!chavePix.isPresent())
            throw new DataNotFoundException("Chave não encontrada");
        else if((chaveAlterada.getTipoChave() != null && !chavePix.get().getTipoChave().equals(chaveAlterada.getTipoChave())) ||
                (chaveAlterada.getValorChave() != null && !chavePix.get().getValorChave().equals(chaveAlterada.getValorChave())))
            throw new InvalidDataException("Alteração inválida");

        validaDadosCorrentista(chaveAlterada);
        chaveAlterada.setTipoChave(chavePix.get().getTipoChave());
        chaveAlterada.setValorChave(chavePix.get().getValorChave());
        chaveAlterada.setAtiva(chavePix.get().getAtiva());
        chaveAlterada.setDataCriacao(chavePix.get().getDataCriacao());
    }

    private void validaChave(ChavePix chave) throws InvalidDataException {

        switch (chave.getTipoChave()) {
            case "celular":
                validaCelular(chave.getValorChave());
                break;
            case "email":
                validaEmail(chave.getValorChave());
                break;
            case "cpf":
                validaCpf(chave.getValorChave());
                break;
            case "cnpj":
                validaCnpj(chave.getValorChave());
                break;
            case "aleatorio":
                validaAleatorio(chave.getValorChave());
                break;
            default:
                throw new InvalidDataException("Tipo de chave inválida");
        }

        validaDadosCorrentista(chave);
        validaQuantidadeChave(chave);

    }

    private ChavePix findChaveByValor(String valorChave) {

        return repo.findChaveByValor(valorChave);
    }

    private void validaCelular(String celular) throws InvalidDataException {

        if(findChaveByValor(celular) != null ||
                celular.length() != 15       ||
                !celular.matches("^\\+[1-9]{2}[0-9]{3}9[0-9]{8}$"))
            throw new InvalidDataException("Número inválido ou já cadastrado");
    }

    private void validaEmail(String email) throws InvalidDataException {

        if(findChaveByValor(email) != null ||
                !(email.length() >= 3 && email.length() <= 77) ||
                !email.matches("^(.+)@(.+)$"))
            throw new InvalidDataException("E-mail inválido ou já cadastrado");
    }

    private void validaCpf(String cpf) throws InvalidDataException {

        if(findChaveByValor(cpf) != null || !cpf.matches("[0-9]{11}$"))
            throw new InvalidDataException("CPF inválido ou já cadastrado");

        int soma = 0, resultado;
        char dig1, dig2;

        for(int i = 0; i < 9; i++) {
            soma += ((Character.getNumericValue(cpf.charAt(i))) * (10-i));
        }

        resultado = 11 - (soma % 11);
        if ((resultado == 10) || (resultado == 11))
            dig1 = '0';
        else
            dig1 = (char)(resultado+'0');

        soma = 0;

        for(int i = 0; i < 10; i++) {
            soma += ((Character.getNumericValue(cpf.charAt(i))) * (11-i));
        }

        resultado = 11 - (soma % 11);
        if ((resultado == 10) || (resultado == 11))
            dig2 = '0';
        else
            dig2 = (char)(resultado+'0');

        if ((dig1 != cpf.charAt(9)) && (dig2 != cpf.charAt(10)))
            throw new InvalidDataException("CPF inválido ou já cadastrado");
    }

    private void validaCnpj(String cnpj) throws InvalidDataException {

        if(findChaveByValor(cnpj) != null || !cnpj.matches("[0-9]{14}$"))
            throw new InvalidDataException("CNPJ inválido ou já cadastrado");

        int soma = 0, resultado, peso = 2;
        char dig1, dig2;

        for(int i = 11; i >= 0; i--) {
            soma += ((Character.getNumericValue(cnpj.charAt(i))) * peso);
            peso++;
            if(peso == 10)
                peso = 2;
        }

        resultado = soma % 11;
        if(resultado == 0 || resultado == 1)
            dig1 = '0';
        else
            dig1 = (char)((11 - resultado)+'0');

        soma = 0;
        peso = 2;

        for(int i = 12; i >= 0; i--) {
            soma += ((Character.getNumericValue(cnpj.charAt(i))) * peso);
            peso++;
            if(peso == 10)
                peso = 2;
        }

        resultado = soma % 11;
        if(resultado == 0 || resultado == 1)
            dig2 = '0';
        else
            dig2 = (char)((11 - resultado)+'0');

        if ((dig1 != cnpj.charAt(12)) && (dig2 != cnpj.charAt(13)))
            throw new InvalidDataException("CNPJ inválido ou já cadastrado");
    }

    private void validaAleatorio(String aleatorio) throws InvalidDataException {

        if(findChaveByValor(aleatorio) != null ||
                aleatorio.length() < 1 ||
                aleatorio.length() > 36 ||
                !aleatorio.matches("^[a-zA-Z0-9]*$"))
            throw new InvalidDataException("Chave aleatória inválida ou já cadastrada");

    }

    private void validaDadosCorrentista(ChavePix chavePix) throws  InvalidDataException {
        if(chavePix.getTipoConta() == null ||
                (!chavePix.getTipoConta().equals("corrente") && !chavePix.getTipoConta().equals("poupança")))
            throw new InvalidDataException("Tipo da conta inválida");

        if(chavePix.getAgencia() == null ||
                !chavePix.getAgencia().toString().matches("^[0-9]{4}$"))
            throw new InvalidDataException("Agência inválida");

        if(chavePix.getConta() == null ||
                !chavePix.getConta().toString().matches("^[0-9]{8}$"))
            throw new InvalidDataException("Número da conta inválido");

        if(chavePix.getPfpj() == null ||
                !chavePix.getPfpj().matches("^[PF|PJ]{2}$"))
            throw new InvalidDataException("Pessoa física ou jurídica");

        if(chavePix.getNomeCorrentista() == null ||
                !chavePix.getNomeCorrentista().matches("^[a-zA-Z]{1,30}$"))
            throw new InvalidDataException("Nome inválido");

        if(!chavePix.getSobrenomeCorrentista().matches("^[a-zA-Z]{1,45}$"))
            throw new InvalidDataException("Sobrenome inválido");
    }

    private void validaQuantidadeChave(ChavePix chavePix) throws InvalidDataException {

        List<ChavePix> chaves = repo.findChavePixByConta(chavePix.getConta());

        if(chaves.isEmpty())
            return;

        if(chavePix.getPfpj().equals("PF") && chaves.size() != 5) {
            for(int i = 0; i < chaves.size(); i++) {
                if(chavePix.getTipoChave().equals(chaves.get(i).getTipoChave()))
                    throw new InvalidDataException("Tipo de chave já registrado");
            }
        } else {
            if (chaves.size() == 20 ||
                    !chavePix.getTipoChave().equals("aleatorio") ||
                    (chavePix.getPfpj().equals("PF") && chaves.size() == 5))
                throw new InvalidDataException("Número máximo de chaves atingido");
        }

    }

}
