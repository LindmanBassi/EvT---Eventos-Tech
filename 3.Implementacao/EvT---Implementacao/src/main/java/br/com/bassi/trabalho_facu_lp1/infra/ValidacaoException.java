package br.com.bassi.trabalho_facu_lp1.infra;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
