package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CarrosService {

    private Statement statement;
    private ResultSet rs=null;
    private Scanner sc= new Scanner(System.in);
    private String nome,placa;
    private  Integer idCliente;

    private LocalDate dataEntrada=LocalDate.now();
    private LocalTime horaEntrada=LocalTime.now();

    private LocalDate dataSaida;
    private LocalTime horaSaida;

    public void menuCarro(){

        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite  nome do carro: ");
        nome = sc.next();

        System.out.println("Digite o seu código id");
        idCliente=sc.nextInt();

        System.out.println("Digite a placa do seu carro: ");
        placa = sc.next();

        System.out.println("Data de hoje: " +dataEntrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        System.out.println("Hora de entrada: " + horaEntrada);

        System.out.println("Digite a data de retirar o carro (dd/MM/yyyy)");
        dataSaida=LocalDate.parse(sc.next(),formatter);

        System.out.println("Digite o horário de retirada do carro (HH:mm)");
        horaSaida=LocalTime.parse(sc.next());

        System.out.println("Data da saida: " +dataSaida.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        System.out.println("Hora da saida: " + horaSaida);

    }

    public void cadastrarCarro(Connection conn){
        try {
            menuCarro();
            String comando=String.format("insert into carros (nome_carro,id_cliente,placa,data_entrada,hora_entrada,data_saida,hora_saida) " +
                            "values('%s','%d','%s','%s','%s','%s','%s');",
                            nome,idCliente,placa,dataEntrada,horaEntrada,dataSaida,horaSaida
            );
            statement=conn.createStatement();
            statement.executeUpdate(comando);
            System.out.println("Carro indo ao estacionamento! ");
        }catch (SQLException e){
            System.out.println("Erro não conseguimos colocar no estacionamento!");
        }
    }

    public void carrosNoEstacionamento(Connection conn){
        try {

            String comando=String.format("select distinct  nome,cpf,data_entrada,data_saida,placa,hora_entrada,hora_saida,valor  from permanencias\n" +
                    "inner join carros \n" +
                    "on id_permanencia=id_carro\n" +
                    "inner  join clientes \n" +
                    "on id_permanencia  =idCliente\n" +
                    ";");
            statement=conn.createStatement();
            rs=statement.executeQuery(comando);
            while (rs.next()){
                System.out.println("Nome -> " + rs.getString("nome")+"\n");
                System.out.println("cpf -> " + rs.getString("cpf")+"\n");
                System.out.println("Data de entrada -> " + rs.getString("data_entrada")+"\n");
                System.out.println("Data de saida -> " + rs.getString("data_saida")+"\n");
                System.out.println("Placa -> " + rs.getString("placa")+"\n");
                System.out.println("Hora de entrada -> " + rs.getString("hora_entrada")+"\n");
                System.out.println("Hora da Saida -> " + rs.getString("hora_saida")+"\n");
                System.out.println("Valor a pagar -> " + rs.getDouble("valor")+"\n");
                System.out.println();
            }


        }
        catch (SQLException e){
            System.out.println("Erro ao realizar consulta!");
        }
    }


}
