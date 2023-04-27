import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Exceções.NomeDoComponenteInvalido;
import Exceções.NomeDoProfessorInvalidoException;
import Exceções.ValoresInvalidosPCargaHoraria;

public class Menu {

    // Metodo que cadastra um professor em nosso banco de dados
    static void cadastrarProfessor() {

        Scanner entrada = new Scanner(System.in); // Scanner para ler dados de entrada para preencher os dados do
                                                  // professor a ser cadastrado de acordo com o usuario
        PreparedStatement instrucao;
        try {
            String nomeDoProfessor; // Atributo onde será armazenado o nome do professor a ser cadastrado e passsado
                                    // para dentro do construtor da classe professor
            String tituloDoProfessor; // Atributo onde será armazenado o titulo do professor a ser cadastrado e
                                      // passado para dentro do construtor da classe professor
            System.out.print("Insira o nome do professor a ser cadastrado: ");
            nomeDoProfessor = entrada.nextLine(); // Recebendo dados do nome
            System.out.print("Insira o titulo do professor a ser cadastrado: ");
            tituloDoProfessor = entrada.nextLine(); // Recebendo dados do titulo

            // instanciando o objeto da classe professor com os dados passados
            Professor professor = new Professor(nomeDoProfessor, tituloDoProfessor);
            // Caso o nome informado esteja dentro da nossa arraylist de string com nome de
            // todos os professor com vinculo atualmente
            // Podemos prosseguir e adicionar o professor e seus respectivos dados para o
            // banco de dados
            if (bancoDeProfessoresAtuais.contains(nomeDoProfessor)) {
                //Atributo do tipo connection usado para realziar a conexão com nosso banco de dados
                Connection connection;
                //String usada para realizar a inserção de dados no banco de dados
                String sql = "insert into professor (id_p,nome,titulo,carga_horaria) values (?,?,?,?)";
                try {
                    //Realizando conexão com o banco de dados atraves da url, port, username e password
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                    //Passando para o atributo instrucão onde ela deve realizar as instruções passadas
                    instrucao = connection.prepareStatement(sql);
                    //Realizando definição de valores a serem inseridos no banco de dados com base nos campos definidos na string "sql" (id_p,nome,titulo,carga_horaria)
                    instrucao.setString(1, professor.getId());
                    instrucao.setString(2, professor.getNome());
                    instrucao.setString(3, professor.getTitulo());
                    instrucao.setInt(4, professor.getCargaHoraria());
                    //Usamos o seguinte atributo para verificar se a alteração foi realmente feita no nosso banco de dados
                    int linhasAfetadas = instrucao.executeUpdate();
                    //Se o atributo possuir valor maior que zero significa que a inserção foi realiada com sucesso
                    if(linhasAfetadas > 0){
                        System.out.println("Inserção concluida com sucesso");
                    } else {
                        System.out.println("A inserção falhou");
                    }
                    
                     //Fechando conexão com o banco de dados para que não seja ocupado recurso do banco de dados
                    instrucao.close();
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                // Caso o professor o nome do professor não esteja dentro da nossa arraylist,
                // mostramos a seguinte mensagem
                System.out.println(
                        "O professor a ser cadastrado no banco de dados não faz parte do banco de professores do banco de dados atual da universidade");
            }
        } catch (NomeDoProfessorInvalidoException e) {
            e.printStackTrace();
        } finally {
            //Fecha o scanner
            entrada.close();
        }
    }

    // Metodo para editar os dados de um professor
    static void editarProfessor() {
        // §canner para ler dados a serem modificados no professor selecionado
        Scanner entrada = new Scanner(System.in);
        // Atributo para armazenar o nome do professor a ser buscado para editar

        try {
            String nomeDoProfessor;
            System.out.println("Insira o nome do professor a ser editado: ");
            nomeDoProfessor = entrada.nextLine();
            Professor professor = new Professor(nomeDoProfessor);
            if (bancoDeProfessoresAtuais.contains(nomeDoProfessor)) {
                /*
                 * utilizar comando update aqui para editar o professor caso ele exista no banco
                 * de dados
                 */
            } else {
                System.out.println("O professor buscado não existe no banco de dados da universidade");
            }
        } catch (NomeDoProfessorInvalidoException e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }
    }

    static void verDadosProfessor() {
        Scanner entrada = new Scanner(System.in);
        try {
            String nomeDoProfessor;
            System.out.println("Insira o nome do professor para ver os seus dados: ");
            nomeDoProfessor = entrada.nextLine();
            Professor professor = new Professor(nomeDoProfessor);
            if (bancoDeProfessoresAtuais.contains(nomeDoProfessor)) {
                /*
                 * utilizar comando select aqui para ver os dados do professor caso ele exista
                 * no banco de dados
                 */
            } else {
                System.out.println("O professor buscado não existe no banco de dados da universidade");
            }
        } catch (NomeDoProfessorInvalidoException e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }

    }

    static void listarProfessores() {
        ArrayList<Professor> listaDeProfessores = new ArrayList<>();

    }

    static void excluirProfessor() {
        Scanner entrada = new Scanner(System.in);

        try {
            String nomeDoProfessor;
            System.out.println("Insira o nome do professor a ser excluido: ");
            nomeDoProfessor = entrada.nextLine();
            Professor professor = new Professor(nomeDoProfessor);
            if (bancoDeProfessoresAtuais.contains(nomeDoProfessor)) {
                /*
                 * utilizar comando drop aqui para excluir o professor caso ele exista no banco
                 * de dados
                 */
            } else {
                System.out.println("O professor buscado não existe no banco de dados da universidade");
            }
        } catch (NomeDoProfessorInvalidoException e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }

    }

    static void cadastrarComponenteCurricular() {
        String r = "S";
        Scanner entrada = new Scanner(System.in);
        while (r.equals("S") || r.equals("s") || r.equals("sim")) {
            try {
                // Variaveis que armazenam os dados do componente curricular a ser cadastrado
                String nomeDoComponenteCurricular;
                String idComponente;
                int cargaHorariaComponente = 0;
                System.out.println("Insira os dados do componente curricular a ser cadastrado: ");
                System.out.print("\nNome do componente curricular: ");
                nomeDoComponenteCurricular = entrada.nextLine();
                System.out.print("\nID do componente curricular: ");
                idComponente = entrada.nextLine();
                System.out.print("\nCarga horaria do componente curricular: ");
                cargaHorariaComponente = entrada.nextInt();
              
                //Caso seja inserido algum caractere que não seja alfanumerico, lançamos uma exceção, pois o campo cargahoraria deve ser do tipo inteiro
                if (Character.isLetter((char) cargaHorariaComponente)) {
                        throw new InputMismatchException(
                                "Você digitou uma letra, mas deveria ser um caractere alfanumérico!");
                }
                //Após ler um dado nextInt() e tentar ler uma string em seguida estava tendo problemas de linhas serem executadas antes do que deveriam, então usei um limpador de buffer para 
                //resolver a situação 
                clearBuffer(entrada);
                // instanciando um objeto da classe componente a qual armazena os dados que
                // serão enviados para o banco de dados
                ComponenteCurricular componenteASerAdicionado = new ComponenteCurricular(cargaHorariaComponente,
                        nomeDoComponenteCurricular, idComponente);
                System.out.println("Componente curricular a ser adicionado: ");
                // Informando os dados do componente curricular a ser adicionado antes de
                // envia-lo para o BD, afim de que o usuario verifique se os dados estão
                // corretos
                System.out.println(componenteASerAdicionado + "\n");
                r = "";
                System.out.println("Deseja adiconar este componente curricular? S/N :");
                r = entrada.nextLine();

                // Caso o usuario realmente deseje inserir no BD o componente entramos neste
                // laço if
                if (r.equals("S") || r.equals("s") || r.equals("sim")) {
                    /* adiciona o componente inserido ao banco */
                    System.out.println("Inserido com sucesso");

                }

                // Logo depois perguntamos se o mesmo deseja inserir mais algum componente, se
                // sim continuamos com as inserções, caso não retornamos ao menu de ops
                System.out.println("Deseja adicionar mais algum componente curricular?");
                System.out.println("S/N ?");
                r = entrada.nextLine();
            } catch (InputMismatchException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
                e.printStackTrace();
            }
            // Verificar a resposta do usuario, caso ele responda com qualquer resposta
            // diferente das que
            // Fazem o laço continuar se repetindo, o programa sai do laço de repetição
            // while e retorna ao menu
            // Usado na main

        }
        entrada.close();

    }

    static void editarComponenteCurricular() {
        Scanner entrada = new Scanner(System.in);
        ComponenteCurricular componente = null;
        try {
            String nomeDoComponente;
            String idDoComponente;
            System.out.println("Insira o nome do componente a qual quer editar: ");
            nomeDoComponente = entrada.nextLine();
            System.out.println("Insira o id do componente a qual quer editar: ");
            idDoComponente = entrada.nextLine();
            componente = new ComponenteCurricular(nomeDoComponente, idDoComponente);
            if (idsComponentes.contains(idDoComponente)) {

            } else {
                System.out.println("O componente curricular informado não está na lista dos componentes cadastrados");
            }
        } catch (NomeDoComponenteInvalido e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }
    }

    static void verComponenteCurricular() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verComponenteCurricular'");
    }

    static void listarComponentesCurriculares() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarComponentesCurriculares'");
    }

    static void excluirComponenteCurricular() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluirComponenteCurricular'");
    }

    static void cadastrarTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cadastrarTurma'");
    }

    static void editarTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarTurma'");
    }

    static void verDadosDaTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verDadosDaTurma'");
    }

    static void listarTurmas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTurmas'");
    }

    static void listarTurmasPorSemestre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTurmasPorSemestre'");
    }

    static void listarTurmasPorProfessor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTurmasPorProfessor'");
    }

    static void excluirTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluirTurma'");
    }

    /* IGNORAR LINHAS ABAIXO */

    // Arraylist de string com os nomes de todos os professores, componentes e ids
    // dos componentes que possuem atualmente um vinculo a universidade,
    // inicialmente para facilitar a
    // Validação de dados, de busca, remoção e edição de dados
    private static ArrayList<String> bancoDeProfessoresAtuais = new ArrayList<>(
            Arrays.asList("ÁDLLER DE OLIVEIRA GUIMARÃES", "ALVARO ALVARES DE CARVALHO CESAR SOBRINHO",
                    "ANTONIO DIEGO SILVA FARIAS", "BRUNO FONTES DE SOUSA", "CLAUDIO ANDRES CALLEJAS OLGUIN",
                    "CLAUDIO DE SOUZA ROCHA", "CLECIDA MARIA BEZERRA BESSA", "VERÔNICA MARIA LIMA SILVA",
                    "FELIPE TORRES LEITE", "GLAUBER BARRETO LUNA", "GLAYDSON F B DE OLIVEIRA",
                    "HELDER FERNANDO DE ARAUJO OLIVEIRA", "HIDALYN THEODORY CLEMENTE MATTOS DE SOUZA",
                    "JARBELE CASSIA DA SILVA COUTINHO", "KATIA CILENE DA SILVA SANTOS",
                    "LAURO CESAR BEZERRA NOGUEIRA", "LAYSA MABEL DE OLIVEIRA FONTES", "LENARDO CHAVES E SILVA",
                    "LINO MARTINS DE HOLANDA JUNIOR",
                    "MONICA PAULA DE SOUSA", "NATHALEE CAVALCANTI DE ALMEIDA", "PAULO GUSTAVO DA SILVA",
                    "PAULO HENRIQUE DAS CHAGAS SILVA",
                    "PEDRO THIAGO VALERIO DE SOUZA", "RAIMUNDO LEIRTON FREITAS MAIA", "REUDISMAM ROLIM DE SOUSA",
                    "ROBSON LOCATELLI MACEDO",
                    "RODRIGO SOARES SEMENTE", "SHARON DANTAS DA CUNHA", "THATYARA FREIRE DE SOUZA",
                    "THIAGO PEREIRA RIQUE", "VINICIUS SAMUEL VALERIO DE SOUZA", "WILLIAM VIEIRA GOMES"));

    private static ArrayList<String> bancoDeComponentesBTI = new ArrayList<>(
            Arrays.asList("ETICA E LEGISLACAO", "ANALISE E EXPRESSAO TEXTUAL", "CALCULO I", "ALGORITMOS",
                    "LABORATÓRIO DE ALGORITMOS", "INTRODUÇÃO À COMPUTAÇÃO E AOS SISTEMAS DE INFORMAÇÃO",
                    "SEMINÁRIO DE INTRODUÇÃO AO CURSO", "SOCIOLOGIA", "ADMINISTRACAO E EMPREENDEDORISMO",
                    "CALCULO II", "GEOMETRIA ANALITICA", "ALGORITMOS E ESTRUTURAS DE DADOS I",
                    "LABORATÓRIO DE ALGORITMOS E ESTRUTURAS DE DADOS I", "ARQUITETURA E ORGANIZAÇÃO DE COMPUTADORES",
                    "ECONOMIA PARA ENGENHARIA", "MATEMATICA DISCRETA", "SISTEMAS OPERACIONAIS", "ALGEBRA LINEAR",
                    "NTRODUCAO AS FUNCOES DE VARIAS VARIAVEIS", "ALGORITMOS E ESTRUTURAS DE DADOS II",
                    "LABORATÓRIO DE ALGORITMOS E ESTRUTURAS DE DADOS II", "REDES DE COMPUTADORES",
                    "PROGRAMAÇÃO ORIENTADA A OBJETOS", "BANCO DE DADOS", "ESTATISTICA",
                    "FILOSOFIA DA CIENCIA E MET. CIENTIFICA",
                    "ENGENHARIA DE SOFTWARE", "SISTEMAS DISTRIBUIDOS", "COMPUTAÇÃO GRÁFICA",
                    "ANÁLISE E PROJETO DE SISTEMAS ORIENTADOS A OBJETOS", "MULTIMÍDIA", "DEPENDABILIDADE E SEGURANÇA",
                    "TRABALHO DE CONCLUSÃO DE CURSO"));

    private static ArrayList<String> idsComponentes = new ArrayList<>(
            Arrays.asList("PAC0008", "PAC0050", "PEX0101", "PEX1236", "PEX1237", "PEX1239", "PEX1240", "PAC0178",
                    "PAC0595", "PEX0102", "PEX0114", "PEX1241", "PEX1243", "PEX1244", "PAC0701", "PAM0324", "PEX0093",
                    "PEX0096", "PEX0117", "PEX1246", "PEX1247", "PEX0041", "PEX0130", "PEX1248",
                    "PVE0004", "PAC0012", "PEX0162", "PEX0183", "PEX1249", "PEX1251", "PEX1253", "PEX1254", "PEX1260"));

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

}
