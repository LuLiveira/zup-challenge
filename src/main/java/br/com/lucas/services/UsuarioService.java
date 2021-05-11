package br.com.lucas.services;

import br.com.lucas.entities.Usuario;
import br.com.lucas.repositories.UsuarioRepository;
import br.com.lucas.repositories.entities.UsuarioEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private ModelMapper mapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    public Usuario cadastrar(Usuario usuario) {

        UsuarioEntity usuarioEntity = mapper.map(usuario, UsuarioEntity.class);

        buscarPorEmail(usuario.getEmail());

        buscarPorCpf(usuario.getCpf());

        UsuarioEntity usuarioRetornado = usuarioRepository.save(usuarioEntity);

        return mapper.map(usuarioRetornado, Usuario.class);
    }

    private void buscarPorCpf(String cpf) {
        Optional<UsuarioEntity> usuarioRetornado = usuarioRepository.findByCpf(cpf);
        if(usuarioRetornado.isPresent()){
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
    }

    private void buscarPorEmail(String email) {
        Optional<UsuarioEntity> usuarioRetornado = usuarioRepository.findByEmail(email);
        if(usuarioRetornado.isPresent()){
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
    }


    public Optional<UsuarioEntity> buscarPorId(Long id) {

        return usuarioRepository.findById(id);
    }
}
