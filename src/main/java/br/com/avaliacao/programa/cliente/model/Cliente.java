package br.com.avaliacao.programa.cliente.model;

import java.io.Serializable;

import br.com.avaliacao.programa.endereco.model.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "codigo", nullable = false)
    @GeneratedValue
    private int codigo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "rg")
    private String rg;

    @OneToOne(targetEntity = Endereco.class)
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

}
