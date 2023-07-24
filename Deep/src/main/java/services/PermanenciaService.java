package services;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PermanenciaService {

    private Statement statement;
    private ResultSet rs=null;

    private Scanner sc= new Scanner(System.in);

    private Integer idCarro,idPermanencia;


    private double valorTotal=0.0, calculo= 0.0, calculo2=0.0, valor=0.0;

    private LocalDate dataSaida;
    private LocalTime horaSaida;

    public void menuPermanencia(){

        System.out.println("Digite o  código id do seu carro");
        idCarro=sc.nextInt();

    }

    public void  menuAtualizar(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Digite o  código id do seu carro");
        idCarro=sc.nextInt();

        System.out.println("Digite o  código de permanencia do seu carro");
        idPermanencia=sc.nextInt();

        System.out.println("Digite a data de retirar o carro (dd/MM/yyyy)");
        dataSaida=LocalDate.parse(sc.next(),formatter);

        System.out.println("Digite o horário de retirada do carro (HH:mm)");
        horaSaida=LocalTime.parse(sc.next());
    }

    public void calcPermanencia(Connection conn){

        try {
            menuPermanencia();
            String comando=String.format("select distinct  nome,cpf,data_entrada,data_saida,placa,hora_entrada,hora_saida,valor  from permanencias\n" +
                            "inner join carros \n" +
                            "on id_permanencia=id_carro\n" +
                            "inner  join clientes \n" +
                            "on id_permanencia  =idCliente\n" +
                            "where idcarro ='%d'\n" +
                            ";"
                    ,idCarro);
            statement=conn.createStatement();
            rs=statement.executeQuery(comando);
            if (rs.next()){

                LocalDate dataEntrada= LocalDate.parse(rs.getString("data_entrada"));
                LocalDate dataSaida= LocalDate.parse(rs.getString("data_saida"));
                Period period = Period.between(dataEntrada,dataSaida);

                LocalTime horaEntrada=LocalTime.parse(rs.getString("hora_entrada"));
                LocalTime horaSaida=LocalTime.parse(rs.getString("hora_saida"));
                Duration duration = Duration.between(horaEntrada,horaSaida);


                double horasData=period.getDays()*24;
                double horas=duration.toMinutes()/60.0;
                double horasTotal=horasData+horas;

                if (horasTotal>=1){
                    valor=10;
                    calculo=valor*horasTotal;
                }
                if (horasTotal == 12 ){
                    valor=90;
                    calculo2=valor*horasTotal;
                }
                if (horasTotal > 12 ){
                    calculo2+=14;
                }

            }
            valorTotal=calculo2+calculo;

            System.out.printf("Valor a pagar: R$ %.2f",valorTotal);
            String insert=String.format("insert into  permanencias(idCarro,valor) values ('%d','"+ valorTotal +"');",idCarro);
            statement=conn.createStatement();
            statement.executeUpdate(insert);
            System.out.println("Cadastro concluido");

        }
        catch (SQLException e){
            System.out.println("Erro ao cadastrar permanecia!");
        }
    }

    public void tirarCarro(Connection conn){
        try {
            System.out.println("Digite qual é o id do seu carro");
            idPermanencia=sc.nextInt();

            String comando = String.format("delete from permanencias where id_permanencia='%d'",idPermanencia);
            String comando2 = String.format("delete from carros where id_carro='%d'",idPermanencia);
            statement=conn.createStatement();

            statement.executeUpdate(comando);
            System.out.println("Permanencia sendo retirada!");

            statement.executeUpdate(comando2);
            System.out.println("Carro sendo retirado!");

        }catch (SQLException e){
            System.out.println("Não foi encontrado o carro com esse id!");
        }
    }



    public  void atualizarTempoEstacionado(Connection conn){

        try {
            menuAtualizar();
            String comando=String.format("select distinct  nome,cpf,data_entrada,data_saida,placa,hora_entrada,hora_saida,valor  from permanencias\n" +
                            "inner join carros \n" +
                            "on id_permanencia=id_carro\n" +
                            "inner  join clientes \n" +
                            "on id_permanencia  =idCliente\n" +
                            "where idcarro ='%d'\n" +
                            ";"
                    ,idCarro);
            statement=conn.createStatement();
            rs=statement.executeQuery(comando);
            if (rs.next()){

                LocalDate dataEntrada= LocalDate.parse(rs.getString("data_entrada"));
                LocalDate dataSaida= LocalDate.parse(rs.getString("data_saida"));
                Period period = Period.between(dataEntrada,dataSaida);

                LocalTime horaEntrada=LocalTime.parse(rs.getString("hora_entrada"));
                LocalTime horaSaida=LocalTime.parse(rs.getString("hora_saida"));
                Duration duration = Duration.between(horaEntrada,horaSaida);


                double horasData=period.getDays()*24;
                double horas=duration.toMinutes()/60.0;
                double horasTotal=horasData+horas;

                if (horasTotal>=1){
                    valor=10;
                    calculo=valor*horasTotal;
                }
                if (horasTotal==12){
                    valor=90;
                    calculo2=valor+horas;

                }
                if (horasTotal>12){
                    calculo2+=14;
                }

            }

            String update=String.format("update carros set data_saida='%s',hora_saida='%s' where id_carro='%d';\n",dataSaida,horaSaida,idCarro);
            statement=conn.createStatement();
            statement.executeUpdate(update);
            System.out.println("Tempo  atualizado");

            valorTotal=calculo2+calculo;

            System.out.printf("Valor a pagar atualizado: R$ %.2f",valorTotal);
            String update2=String.format("update permanencias set valor='"+valorTotal+"' where id_permanencia='%d';\n",idPermanencia);
            statement=conn.createStatement();
            statement.executeUpdate(update2);
            System.out.println();
            System.out.println("Valor atualizado!");

        }
        catch (SQLException e){
            System.out.println("Erro ao atualizar carro no estacionamento!");
        }
    }

}
