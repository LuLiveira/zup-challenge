package br.com.lucas.controllers;

import br.com.lucas.controllers.dto.UsuarioCadastroDTO;
import br.com.lucas.controllers.dto.UsuarioDTO;
import br.com.lucas.entities.Usuario;
import br.com.lucas.repositories.entities.UsuarioEntity;
import br.com.lucas.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService usuarioService;
    private ModelMapper mapper;

    public UsuarioController(UsuarioService usuarioService, ModelMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody @Valid UsuarioCadastroDTO request){

        Usuario usuario = mapper.map(request, Usuario.class);

        Usuario usuarioRetornado = usuarioService.cadastrar(usuario);

        UsuarioDTO usuarioDTO = mapper.map(usuarioRetornado, UsuarioDTO.class);

        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> buscar(@PathVariable Long id){

        Optional<UsuarioEntity> usuarioEntity = usuarioService.buscarPorId(id);

        return ResponseEntity.ok(usuarioEntity.get());
    }
}
