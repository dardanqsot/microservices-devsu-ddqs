package com.dardan.client.controller;

import com.dardan.client.dto.ClientDto;
import com.dardan.client.dto.ResponseDto;
import com.dardan.client.model.Client;
import com.dardan.client.service.ClientService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/client")
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Client Service Microservice",
                version = "0.0.1",
                description = "Módulo para la gestión de clientes",
                contact = @Contact(
                        name = "Dardan",
                        url = "http://github.com/dardanqsot",
                        email = "quispesotodaniel@gmail.com"
                )
        ),
        servers = { @Server(url = "http:localhost:8080")},
        tags = @Tag(name = "Clientes", description = "Microservicio para la gestión de clientes")
)
public class ClientController {

    private final ModelMapper mapper;
    private final ClientService service;

    @GetMapping
    public ResponseEntity<ResponseDto<List<ClientDto>>> findAll(){
        List<ClientDto> lst = service.findAll().stream()
                .filter(Client::isEnabled)
                .map(this::convertToDto).toList();
        return new ResponseEntity<>(ResponseDto.successResponse(lst), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ClientDto>> findById(@PathVariable("id") Integer id){
        Client obj = service.findByIdClientAndEnabled(id);
        return new ResponseEntity<>(ResponseDto.successResponse(convertToDto(obj)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> create(@Valid @RequestBody ClientDto clientDto) {
        service.save(convertToEntity(clientDto));
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> update(@Valid @RequestBody ClientDto clientDto, @PathVariable Integer id) throws Exception {
        clientDto.setIdClient(id);
        service.update(convertToEntity(clientDto), id);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> remove(@PathVariable Integer id) {
        Client client = service.findByIdClientAndEnabled(id);
        client.changeEnabled();
        service.save(client);
        return new ResponseEntity<>(ResponseDto.successResponse(null), HttpStatus.OK);
    }
    private ClientDto convertToDto(Client obj){
        return mapper.map(obj, ClientDto.class);
    }
    private Client convertToEntity(ClientDto dto){
        return mapper.map(dto, Client.class);
    }

}
