package br.com.avaliacao.programa.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.programa.cliente.model.Cliente;

public  interface ClienteRepository extends JpaRepository<Cliente, Long>{
    
}
