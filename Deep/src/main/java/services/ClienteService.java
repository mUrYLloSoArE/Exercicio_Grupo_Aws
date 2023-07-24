package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ClienteService {

    private Statement statement;
    private ResultSet rs=null;
    private Scanner sc= new Scanner(System.in);
    private String cpf,nome;

    public void menuCliente(){
        System.out.println("Bem vindo antes de tudo se cadastre no nosso estacionamento! ");

        System.out.println("Digite o seu nome: ");
        nome = sc.next();

        System.out.println("Digite o seu CPF: ");
        cpf = sc.next();

    }

    public void cadastrarCliente(Connection conn){
        try {
            menuCliente();
            String comando=String.format("insert into clientes (nome,cpf) " +
                            "values('%s','%s');",
                             nome,cpf
            );
            statement=conn.createStatement();
            statement.executeUpdate(comando);
            System.out.println("Cliente cadastrado");
        }catch (SQLException e){
            System.out.println("Erro ao cadastrar cliente!");
        }
    }

    }

