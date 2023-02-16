package br.com.avaliacao.programa.exception;

public class CepFormatoInvalidoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public CepFormatoInvalidoException(String mensagem){
		super(mensagem);
	}
}
