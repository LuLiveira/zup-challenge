package br.com.lucas.services;

import br.com.lucas.entities.Endereco;
import br.com.lucas.feign.ViaCepFeign;
import br.com.lucas.feign.dto.CepDTO;
import br.com.lucas.repositories.EnderecoRepository;
import br.com.lucas.repositories.entities.EnderecoEntity;
import br.com.lucas.repositories.entities.UsuarioEntity;
import br.com.lucas.services.builder.EnderecoBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;
    private ModelMapper mapper;
    private ViaCepFeign viaCepFeign;

    public EnderecoService(EnderecoRepository enderecoRepository, ModelMapper mapper, ViaCepFeign viaCepFeign) {
        this.enderecoRepository = enderecoRepository;
        this.mapper = mapper;
        this.viaCepFeign = viaCepFeign;
    }

    public Endereco cadastrar(Endereco endereco) {

        endereco = verificarEndereco(endereco);


        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(Long.valueOf(endereco.getIdUsuario()));

        EnderecoEntity enderecoEntity = mapper.map(endereco, EnderecoEntity.class);

        enderecoEntity.setId(UUID.randomUUID().toString());
        enderecoEntity.setUsuarioEntity(usuarioEntity);
        EnderecoEntity enderecoRetornado = enderecoRepository.save(enderecoEntity);
        return mapper.map(enderecoRetornado, Endereco.class);
    }

    private Endereco verificarEndereco(Endereco endereco) {
            CepDTO cepDTO = viaCepFeign.getCep(endereco.getCep()).getBody();
            return new EnderecoBuilder().builder()
                                    .logradouro(cepDTO.getLogradouro())
                                    .numero(endereco.getNumero())
                                    .bairro(cepDTO.getBairro())
                                    .cidade(cepDTO.getLocalidade())
                                    .estado(cepDTO.getUf())
                                    .cep(endereco.getCep())
                                    .idUsuario(endereco.getIdUsuario())
                            .build();

    }
}
