package br.com.lucas.services.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String s) {
        super(s);
    }
}
