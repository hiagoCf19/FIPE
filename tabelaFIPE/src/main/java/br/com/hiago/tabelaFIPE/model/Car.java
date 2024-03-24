package br.com.hiago.tabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Car(String Valor,
                  String Marca,
                  String Modelo,
                  @JsonAlias("AnoModelo") String Ano,
                  String Combustivel,
                  String CodigoFipe   ) {
}
