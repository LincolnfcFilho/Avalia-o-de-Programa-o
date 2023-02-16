package br.com.avaliacao.programa.endereco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.avaliacao.programa.endereco.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
    
}
