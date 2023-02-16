package br.com.avaliacao.programa.cliente.service;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import br.com.avaliacao.programa.cliente.form.ClienteForm;
import br.com.avaliacao.programa.cliente.model.Cliente;
import br.com.avaliacao.programa.cliente.repository.ClienteRepository;
import br.com.avaliacao.programa.endereco.model.Endereco;
import br.com.avaliacao.programa.endereco.repository.EnderecoRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public ResponseEntity<Cliente> salvar(ClienteForm cliente) throws IOException, InterruptedException {

        Gson gson = new Gson();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.of(1, MINUTES))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://viacep.com.br/ws/" + cliente.getCep() + "/json"))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        log.info("[VIA CEP API] - [RESULTADO DA BUSCA: {}]", httpResponse.body());

        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setNome(cliente.getNome());
        clienteSalvo.setCpf(cliente.getCpf());
        clienteSalvo.setRg(cliente.getRg());
        clienteSalvo.setEndereco(gson.fromJson(httpResponse.body(), Endereco.class));

        enderecoRepository.save(clienteSalvo.getEndereco());
        clienteRepository.save(clienteSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity<List<Cliente>> listarClientes() {

        List<Cliente> clientes = clienteRepository.findAll();

        return ResponseEntity.ok().body(clientes);
    }

    public ResponseEntity<Cliente> procurarClientePorId(Long id) {

        Long idCliente = verificarSeClienteExiste(id);

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        return ResponseEntity.ok().body(cliente.get());
    }

    public ResponseEntity<Cliente> deletarClientePorId(Long id) {

        Long idCliente = verificarSeClienteExiste(id);

        clienteRepository.deleteById(idCliente);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /*
     * Metodo para verificar se o cliente existe
     * 
     * @param id : Long
     */
    public Long verificarSeClienteExiste(Long id) {

        if (clienteRepository.existsById(id)) {
            return id;
        }

        throw new RuntimeException("Cliente não encontrado");
    }

    public ResponseEntity<Cliente> atualizarCliente(Long id, ClienteForm cliente)
            throws IOException, InterruptedException {

        Long idCliente = verificarSeClienteExiste(id);

        Cliente ClienteAtualizado = clienteRepository.findById(idCliente).get();

        Gson gson = new Gson();

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.of(1, MINUTES))
                .build();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://viacep.com.br/ws/" + cliente.getCep() + "/json"))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        log.info("[VIA CEP API] - [RESULTADO DA BUSCA: {}]", httpResponse.body());

        ClienteAtualizado.setNome(cliente.getNome());
        ClienteAtualizado.setCpf(cliente.getCpf());
        ClienteAtualizado.setRg(cliente.getRg());
        ClienteAtualizado.setEndereco(gson.fromJson(httpResponse.body(), Endereco.class));

        enderecoRepository.save(ClienteAtualizado.getEndereco());
        clienteRepository.save(ClienteAtualizado);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
