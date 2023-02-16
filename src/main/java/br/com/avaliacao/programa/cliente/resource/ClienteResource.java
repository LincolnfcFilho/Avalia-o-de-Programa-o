package br.com.avaliacao.programa.cliente.resource;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.avaliacao.programa.cliente.form.ClienteForm;
import br.com.avaliacao.programa.cliente.model.Cliente;
import br.com.avaliacao.programa.cliente.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/salvar/cliente")
    public ResponseEntity<Cliente> salvar(@RequestBody ClienteForm clienteForm) throws IOException, InterruptedException {
        return clienteService.salvar(clienteForm);
    }

    @GetMapping("/listar/clientes")
    public ResponseEntity<List<Cliente>> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/procurar/cliente/{id}")
    public ResponseEntity<Cliente> procurarClientePorId(@PathVariable("id") Long id) {
        return clienteService.procurarClientePorId(id);
    }

    @DeleteMapping("/deletar/cliente/{id}")
    public ResponseEntity<Cliente> deletarClientePorId(@PathVariable("id") Long id) {
        return clienteService.deletarClientePorId(id);
    }

    @PutMapping("/atualizar/cliente/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteForm cliente) throws IOException, InterruptedException {
        return clienteService.atualizarCliente(id, cliente);
    }

}
