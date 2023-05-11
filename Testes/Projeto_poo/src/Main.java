package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

      Scanner entrada = new Scanner(System.in);
      int op = 1;
      
      do {
      System.out.println("\nSISTEMA DE HORARIOS\n");
      System.out.println("Selecione uma opção");
      System.out.println("1 - Menu professor");
      System.out.println("2 - Menu componente curricular");
      System.out.println("3 - Menu turma");
      System.out.println("0 - Sair\n");
      int op2 = 1;
      op = entrada.nextInt();
      switch(op){
        case 1:
        while(op2 != 0){
          System.out.println("\nMENU PROFESSOR\n");
          System.out.println("Selecione uma opção");
          System.out.println("1 - Cadastrar professor");
          System.out.println("2 - Editar professor");
          System.out.println("3 - Ver dados do professor");
          System.out.println("4 - Excluir professor");
          System.out.println("5 - Listar professores");
          System.out.println("0 - Voltar\n");
          op2 = entrada.nextInt();
          Menu.clearBuffer(entrada);
          switch(op2){
            case 1:
            Menu.cadastrarProfessor();
            break;
            case 2:
            Menu.editarProfessor();
            break;
            case 3:
            Menu.verDadosDoProfessor();
            break;
            case 4:
            Menu.excluirProfessor();
            break;
            case 5:
            Menu.listarProfessores();
            break;
            case 0:
            System.out.println("\nRetornando para o menu principal");
            break;
            default:
            System.out.println("\nOpção inválida");
            break;
          }
        }
      break;
      case 2:
      while(op2 != 0){
        System.out.println("\nMENU COMPONENTE CURRICULAR\n");
        System.out.println("Selecione uma opção");
        System.out.println("1 - Cadastrar componente curricular");
        System.out.println("2 - Editar componente curricular");
        System.out.println("3 - Ver dados do componente curricular");
        System.out.println("4 - Excluir componente curricular");
        System.out.println("5 - Listar componentes");
        System.out.println("0 - Voltar\n");
        op2 = entrada.nextInt();
        Menu.clearBuffer(entrada);
        switch(op2){
          case 1:
          Menu.cadastrarComponenteCurricular();
          break;
          case 2: 
          Menu.editarComponenteCurricular();
          break;
          case 3:
          Menu.verComponenteCurricular();
          break;
          case 4:
          Menu.excluirComponenteCurricular();;
          break;
          case 5:
          Menu.listarComponentesCurriculares();
          break;
          case 0:
          System.out.println("\nRetornando para o menu principal");
          break;
          default:
          System.out.println("\nOpção inválida");
          break;
        }
      }  
      break;
      case 3:
      while(op2!=0){
      System.out.println("\nMENU TURMA\n");
      System.out.println("Selecione uma opção");
      System.out.println("1 - Cadastrar turma");
      System.out.println("2 - Ver dados da turma");
      System.out.println("3 - Excluir turma");
      System.out.println("4 - Listar turmas");
      System.out.println("5 - Listar turmas por semestre");
      System.out.println("6 - Listar turmas por professor");
      System.out.println("0 - Voltar\n");
      op2 = entrada.nextInt();
      Menu.clearBuffer(entrada);
      switch(op2){
        case 1:
        Menu.cadastrarTurma();
        break;
        case 2:
        Menu.verDadosDaTurma();
        break;
        case 3:
        Menu.excluirTurma();
        break;
        case 4:
        Menu.listarTurmas();
        break;
        case 5:
        Menu.listarTurmasPorSemestre();
        break;
        case 6:
        Menu.listarTurmasPorProfessor();
        break;
        case 0:
        System.out.println("\nRetornando para o menu principal");
        break;
        default:
        System.out.println("\nOpção inválida");
        break;
      }
    }
      break;
      case 0:
      System.out.println("\nEncerrando sistema\n");
      break;
      default:
      //System.out.println("\nOpção inválida");
      break;
        }
      } while (op != 0);
      
      

      }
    }
