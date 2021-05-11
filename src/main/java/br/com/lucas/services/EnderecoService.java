package br.com.lucas.services;

import br.com.lucas.entities.Endereco;
import br.com.lucas.feign.ViaCepFeign;
import br.com.lucas.feign.dto.CepDTO;
import br.com.lucas.repositories.EnderecoRepository;
import br.com.lucas.repositories.UsuarioRepository;
import br.com.lucas.repositories.entities.EnderecoEntity;
import br.com.lucas.repositories.entities.UsuarioEntity;
import br.com.lucas.services.builder.EnderecoBuilder;
import br.com.lucas.services.exception.UsuarioNaoEncontradoException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;
    private UsuarioRepository usuarioRepository;
    private ModelMapper mapper;
    private ViaCepFeign viaCepFeign;

    public EnderecoService(EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository, ModelMapper mapper, ViaCepFeign viaCepFeign) {
        this.enderecoRepository = enderecoRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
        this.viaCepFeign = viaCepFeign;
    }

    public Endereco cadastrar(Endereco endereco) {

        endereco = verificarEndereco(endereco);

        var enderecoEntity = mapper.map(endereco, EnderecoEntity.class);

        var usuarioEntity = usuarioRepository.findById(Long.valueOf(endereco.getIdUsuario()));

        if(!usuarioEntity.isPresent()){
            throw new UsuarioNaoEncontradoException("O usuário ao qual o endereço pertence não foi encontrado.");
        }

        enderecoEntity.setId(UUID.randomUUID().toString());
        enderecoEntity.setUsuarioEntity(usuarioEntity.get());
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
