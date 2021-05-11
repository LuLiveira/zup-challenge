package br.com.lucas.controllers;

import br.com.lucas.controllers.dto.EnderecoCadastroDTO;
import br.com.lucas.controllers.dto.EnderecoDTO;
import br.com.lucas.entities.Endereco;
import br.com.lucas.services.EnderecoService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private EnderecoService enderecoService;
    private ModelMapper mapper;

    public EnderecoController(EnderecoService enderecoService, ModelMapper mapper) {
        this.enderecoService = enderecoService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> cadastrar(@RequestBody @Valid EnderecoCadastroDTO request){
        Endereco endereco = mapper.map(request, Endereco.class);

        Endereco enderecoRetornado = enderecoService.cadastrar(endereco);

        EnderecoDTO enderecoDTO = mapper.map(enderecoRetornado, EnderecoDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoDTO);
    }
}
