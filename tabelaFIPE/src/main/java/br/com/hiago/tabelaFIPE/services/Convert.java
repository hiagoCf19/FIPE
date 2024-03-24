package br.com.hiago.tabelaFIPE.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

public class Convert implements iConvert {

    private ObjectMapper mapper= new ObjectMapper();




    @Override
    public <T> T getData(String json, Class<T> classe) {
        try {
            return  mapper.readValue(json, classe);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List <T> getList(String json, Class<T> classe) {
        CollectionType list= mapper.getTypeFactory()
                .constructCollectionType(List.class, classe);
        try {
            return mapper.readValue(json, list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
