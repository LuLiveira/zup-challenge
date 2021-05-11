package br.com.lucas.services.builder;

import br.com.lucas.entities.Endereco;

public class EnderecoBuilder {

    private Endereco endereco;

    public EnderecoBuilder() {
        endereco = new Endereco();
    }

    public EnderecoBuilder builder() {
        return this;
    }

    public EnderecoBuilder logradouro(String logradouro) {
        this.endereco.setLogradouro(logradouro);
        return this;
    }

    public EnderecoBuilder numero(Integer numero) {
        this.endereco.setNumero(numero);
        return this;
    }

    public EnderecoBuilder bairro(String bairro) {
        this.endereco.setBairro(bairro);
        return this;
    }

    public EnderecoBuilder cidade(String localidade) {
        endereco.setCidade(localidade);
        return this;
    }

    public EnderecoBuilder estado(String uf) {
        endereco.setEstado(uf);
        return this;
    }

    public EnderecoBuilder cep(String cep) {
        endereco.setCep(cep);
        return this;
    }

    public EnderecoBuilder idUsuario(Integer idUsuario) {
        endereco.setIdUsuario(idUsuario);
        return this;
    }

    public Endereco build() {
        return endereco;
    }

    public EnderecoBuilder complemento(String complemento) {
        endereco.setComplemento(complemento);
        return this;
    }
}
