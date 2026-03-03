package br.com.bsantos.screenmatch.services;

public interface ConverteJsonParaObjeto {

    <T> T obterDados(String json, Class<T> T);
}
