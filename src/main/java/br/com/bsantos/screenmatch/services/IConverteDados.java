package br.com.bsantos.screenmatch.services;

public interface IConverteDados {

    <T> T obterDados(String json, Class<T> T);
}
