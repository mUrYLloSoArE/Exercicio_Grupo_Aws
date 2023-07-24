package controller;

import connections.Conexao;
import services.CarrosService;
import services.ClienteService;
import services.PermanenciaService;

import java.util.Scanner;

import static connections.Conexao.conectar;

public class Controller {

    private PermanenciaService permanenciaService=new PermanenciaService();
    private ClienteService clienteService=new ClienteService();
    private CarrosService carrosService=new CarrosService();
    private Scanner sc = new Scanner(System.in);
    private int opcao;

    public void menuInsertCarro() {
          clienteService.cadastrarCliente(conectar());
          carrosService.cadastrarCarro(conectar());
          permanenciaService.calcPermanencia(conectar());
    }

    public void menuDeep() {
        System.out.println("Bem vindo ao sistema de Estacionamento Deep");
        System.out.println("Digite uma das opções: ");
        System.out.println("0- Encerrar");
        System.out.println("1- Cadastrar carro");
        System.out.println("2- Carros estacionados!");
        System.out.println("3- Tirar o carro do Estacionamento! ");
        System.out.println("4- Atualizar carro no estacionamento");
    }


    public void menu() {
        do {
            menuDeep();
            opcao = sc.nextInt();

            switch (opcao) {
                case 0:
                    System.out.println("Programa Encerrado!");
                    break;
                case 1:
                    menuInsertCarro();
                    break;
                case 2:
                    carrosService.carrosNoEstacionamento(conectar());
                    break;
                case 3:
                    permanenciaService.tirarCarro(conectar());
                    break;
                case 4:
                    permanenciaService.atualizarTempoEstacionado(conectar());
                    break;
                default:
                    System.out.println("Opção inválida! ");
            }

        } while (opcao != 0);
    }

}
