package br.com.hiago.tabelaFIPE;


import br.com.hiago.tabelaFIPE.model.Car;
import br.com.hiago.tabelaFIPE.model.Data;
import br.com.hiago.tabelaFIPE.model.Modelos;
import br.com.hiago.tabelaFIPE.services.ApiConsumer;
import br.com.hiago.tabelaFIPE.services.Convert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private final ApiConsumer api = new ApiConsumer();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private Convert convert= new Convert();
    private Scanner scanner= new Scanner(System.in);

    public void start() {
        String endereco;
        var menu= """
                *** OPÇÕES ***:
                1- Carros
                2- Motos
                3- Caminhões
                Digite uma das opções para consultar: 
                 """;



        System.out.println(menu);

        var option= scanner.nextLine();


        if(option.toLowerCase().contains("car")){
            endereco= URL_BASE + "carros/marcas/";
        } else if (option.toLowerCase().contains("mot")) {
            endereco= URL_BASE + "motos/marcas/";
        } else{
             endereco= URL_BASE + "caminhoes/marcas/";
        }
        var json= api.getApiData(endereco);
        System.out.println(json);

        var marcas= convert.getList(json, Data.class);

       marcas.stream()
               .sorted(Comparator.comparing(Data::codigo))
               .forEach(System.out::println);


        System.out.println("Informe o código da marca para consulta: ");
        String codBrand= scanner.nextLine();
        endereco += codBrand + "/modelos/";
        json= api.getApiData(endereco);

        var modelos= convert.getData(json, Modelos.class);
        System.out.println("\nModelos da marca: ");
        modelos.modelos().stream().sorted(Comparator.comparing(Data::codigo))
                .forEach(System.out::println);


        System.out.println("\nDigite o nome de um carro a ser buscado");
        var carName= scanner.nextLine();
        List<Data> modelsList= modelos.modelos().stream()
                // filtra os modelos que batem com o search do user
                .filter(m-> m.nome().toLowerCase().contains(carName.toLowerCase()))
                // colete os modelos que passaram no filtro faça o modelList ter isso como valor
                .collect(Collectors.toList());

        if(modelsList.size() > 0 ){
            System.out.println("\nModelos filtrados: ");
            modelsList.forEach(System.out::println);

        System.out.println("Digite o código do carro desejado:  ");
       var codModel= scanner.nextLine();
       endereco +=  codModel + "/anos";

       json= api.getApiData(endereco);
       List<Data> anos= convert.getList(json, Data.class);

       List<Car> cars= new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoYear= endereco + "/" + anos.get(i).codigo();
            json=api.getApiData(enderecoYear);
            var car= convert.getData(json, Car.class);
            cars.add(car);
        }
        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        cars.forEach(System.out::println);

        } else {
            System.out.println("Nenhum modelo encontrado.");

        }
















    }




}
