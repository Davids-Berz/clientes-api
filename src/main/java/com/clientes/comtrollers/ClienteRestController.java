package com.clientes.comtrollers;

import com.clientes.models.entity.Cliente;
import com.clientes.models.services.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ClienteRestController {

    private IClienteService clienteService;

    @Autowired
    public ClienteRestController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        Cliente cliente = null;

        try {
            cliente = clienteService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al consultar la base de datos: " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null) {
            response.put("mensaje", "El cliente ID: " + id.toString() + " no existe!");
            return new ResponseEntity<>(cliente, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
        Cliente clienteActual = clienteService.findById(id);
        clienteActual.setNombre(cliente.getNombre());
        clienteActual.setApellido(cliente.getApellido());
        clienteActual.setEmail(cliente.getEmail());

        return clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }

}
