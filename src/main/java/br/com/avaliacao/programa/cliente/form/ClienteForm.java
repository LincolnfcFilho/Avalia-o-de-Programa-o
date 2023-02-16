package br.com.avaliacao.programa.cliente.form;

import lombok.Data;

@Data
public class ClienteForm {
    
    private int codigo;
    private String nome;
    private String cpf;
    private String rg;
    private String cep;

}
