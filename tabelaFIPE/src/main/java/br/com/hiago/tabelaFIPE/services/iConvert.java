package br.com.hiago.tabelaFIPE.services;

import java.util.List;

public interface iConvert {
    <T> T getData(String json, Class<T> classe);

    <T> List<T> getList(String json, Class<T> classe);
}
