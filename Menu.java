import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.naming.spi.DirStateFactory.Result;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Exceções.NomeDoComponenteInvalido;
import Exceções.DadosDoProfessorInvalidoException;
import Exceções.ValoresInvalidosPCargaHoraria;

public class Menu {

    // Metodo que cadastra um professor em nosso banco de dados
    static void cadastrarProfessor() {

        Scanner entrada = new Scanner(System.in); // Scanner para ler dados de entrada para preencher os dados do
                                                  // professor a ser cadastrado de acordo com o usuario

        try {
            int id;
            String nomeDoProfessor; // Atributo onde será armazenado o nome do professor a ser cadastrado e passsado
                                    // para dentro do construtor da classe professor
            String tituloDoProfessor; // Atributo onde será armazenado o titulo do professor a ser cadastrado e
                                      // passado para dentro do construtor da classe professor
            System.out.print("Insira o nome do professor a ser cadastrado: ");
            nomeDoProfessor = entrada.nextLine().trim(); // Recebendo dados do nome
            System.out.print("Insira o titulo do professor a ser cadastrado: ");
            tituloDoProfessor = entrada.nextLine().trim(); // Recebendo dados do titulo
            System.out.println("Insira o id do professor a ser cadastrado: ");
            id = entrada.nextInt();
            // instanciando o objeto da classe professor com os dados passados
            Professor professor = new Professor(nomeDoProfessor, tituloDoProfessor, id);
            // Caso o nome informado esteja dentro da nossa arraylist de string com nome de
            // todos os professor com vinculo atualmente
            // Podemos prosseguir e adicionar o professor e seus respectivos dados para o
            // banco de dados
            if (!professoresCad.contains(professor)) {
                // Atributo do tipo connection usado para realziar a conexão com nosso banco de
                // dados
                Connection connection;
                // String usada para realizar a inserção de dados no banco de dados
                String sql = "insert into professor (id_p,nome,titulo,carga_horaria) values (?,?,?,?)";
                try {
                    // Realizando conexão com o banco de dados atraves da url, port, username e
                    // password
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                            "123456789");
                    // Passando para o atributo instrucão onde ela deve realizar as instruções
                    // passadas
                    PreparedStatement instrucao = connection.prepareStatement(sql);
                    // Realizando definição de valores a serem inseridos no banco de dados com base
                    // nos campos definidos na string "sql" (id_p,nome,titulo,carga_horaria)
                    instrucao.setInt(1, professor.getId());
                    instrucao.setString(2, professor.getNome());
                    instrucao.setString(3, professor.getTitulo());
                    instrucao.setInt(4, professor.getCargaHoraria());
                    // Usamos o seguinte atributo para verificar se a alteração foi realmente feita
                    // no nosso banco de dados
                    int linhasAfetadas = instrucao.executeUpdate();
                    // Se o atributo possuir valor maior que zero significa que a inserção foi
                    // realiada com sucesso
                    if (linhasAfetadas > 0) {
                        System.out.println("Inserção concluida com sucesso");
                    } else {
                        System.out.println("A inserção falhou");
                    }

                    // Fechando conexão com o banco de dados para que não seja ocupado recurso do
                    // banco de dados e adicionando objeto professor a nossa arraylist de
                    // professores
                    professoresCad.add(professor);

                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Fecha o scanner

                }
            } else {
                // Caso o professor o nome do professor não esteja dentro da nossa arraylist,
                // mostramos a seguinte mensagem
                System.out.println("O professor a ser cadastrado no banco de dados já está cadastrado");
            }
        } catch (InputMismatchException e) {
            e.printStackTrace();
        } catch (DadosDoProfessorInvalidoException e) {
            e.printStackTrace();
        }
    }

    // Metodo para editar os dados de um professor
    static void editarProfessor() {
        // §canner para ler dados a serem modificados no professor selecionado
        Scanner entrada = new Scanner(System.in);
        // Atributo para armazenar o nome do professor a ser buscado para editar
        int idDoProfessor;
        System.out.println("Insira o id do professor a ser editado: ");
        idDoProfessor = entrada.nextInt();
        clearBuffer(entrada);
        Professor professor = getDadosDoProfessor(idDoProfessor);
        try {
            String nomeDoProfessor;
            try {
                // Atributo do tipo connection usado para realizar a conexão com nosso banco de
                // dados
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                        "123456789");
                // String usada para realizar a inserção de dados no banco de dados
                String tituloDoProfessor;
                System.out.println(
                        "Insira o nome do professor para atualizar o nome do professor selecionado, caso deseje manter o valor atual pressione enter: ");
                nomeDoProfessor = entrada.nextLine().trim();
                if (nomeDoProfessor.isEmpty()) {
                    nomeDoProfessor = professor.getNome();
                }
                System.out.println(
                        "Insira o titulo para atualizar o titulo do professor selecionado, caso deseje manter o valor atual pressione enter: ");
                tituloDoProfessor = entrada.nextLine().trim();
                if (tituloDoProfessor.isEmpty()) {
                    tituloDoProfessor = professor.getTitulo();
                }
                String sql = "UPDATE professor SET nome = ?, titulo = ? WHERE id_p = ?";
                PreparedStatement instrucao;
                instrucao = connection.prepareStatement(sql);
                instrucao.setString(1, nomeDoProfessor);
                instrucao.setString(2, tituloDoProfessor);
                instrucao.setInt(3, professor.getId());
                int linhasAfetadas = instrucao.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Atualizado com sucesso");
                } else {
                    System.out.println("Atualização falhou");
                }

                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } finally {

        }
    }

    static Professor verDadosDoProfessor() {
        Scanner entrada = new Scanner(System.in);
        Professor professor = null;

        System.out.println("Insira o id do professor para ver seus dados: ");
        int idProfessor = entrada.nextInt();
        clearBuffer(entrada);

        try {

            try {
                // Preparando conxexão com o banco de dados
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                        "123456789");
                String sql = "SELECT id_prof, id_p, nome, titulo FROM professor WHERE id_p = ?";
                PreparedStatement instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, idProfessor);
                // executando consulta
                ResultSet consulta = instrucao.executeQuery();
                // Usando laço while para capturar os dados do professor buscado
                while (consulta.next()) {
                    int idKey = consulta.getInt("id_prof");
                    int id_p = consulta.getInt("id_p");
                    String nomeDoProfessor = consulta.getString("nome");
                    String tituloDoProfessor = consulta.getString("titulo");
                    professor = new Professor(nomeDoProfessor, tituloDoProfessor, id_p);
                    professor.setIdKey(idKey);
                }
                // preparando nova consulta
                sql = "SELECT id_p, nomecomp, carga_horaria FROM componentes WHERE id_prof = ?";
                PreparedStatement instrucao2 = connection.prepareStatement(sql);
                instrucao2.setInt(1, professor.getIdKey());
                // executando consulta
                consulta = instrucao2.executeQuery();
                // capturando componentes curriculares do professor
                while (consulta.next()) {
                    int idComp = consulta.getInt("id_p");
                    String nomeComp = consulta.getString("nomecomp");
                    int cargaHorariaComp = consulta.getInt("carga_horaria");
                    professor.adicionaComponenteCurricular(cargaHorariaComp, nomeComp, idComp);
                }

                System.out.println(professor);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DadosDoProfessorInvalidoException e) {
                e.printStackTrace();
            }

        } finally {
            // System.out.println("\nFinalizando função verDadosProfessor");
        }
        return professor;
    }

    static Professor getDadosDoProfessor(int idProfessor) {
        Professor professor = null;
        try {

            try {
                // Preparando conxexão com o banco de dados
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                        "123456789");
                String sql = "SELECT id_prof, id_p, nome, titulo FROM professor WHERE id_p = ?";
                PreparedStatement instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, idProfessor);
                // executando consulta
                ResultSet consulta = instrucao.executeQuery();
                // Usando laço while para capturar os dados do professor buscado
                while (consulta.next()) {
                    int idKey = consulta.getInt("id_prof");
                    int id_p = consulta.getInt("id_p");
                    String nomeDoProfessor = consulta.getString("nome");
                    String tituloDoProfessor = consulta.getString("titulo");
                    professor = new Professor(nomeDoProfessor, tituloDoProfessor, id_p);
                    professor.setIdKey(idKey);
                }
                // preparando nova consulta
                sql = "SELECT id_p, nomecomp, carga_horaria FROM componentes WHERE id_prof = ?";
                PreparedStatement instrucao2 = connection.prepareStatement(sql);
                instrucao2.setInt(1, professor.getIdKey());
                // executando consulta
                consulta = instrucao2.executeQuery();
                // capturando componentes curriculares do professor
                while (consulta.next()) {
                    int idComp = consulta.getInt("id_p");
                    String nomeComp = consulta.getString("nomecomp");
                    int cargaHorariaComp = consulta.getInt("carga_horaria");
                    professor.adicionaComponenteCurricular(cargaHorariaComp, nomeComp, idComp);
                }

                System.out.println(professor);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (DadosDoProfessorInvalidoException e) {
                e.printStackTrace();
            }

        } finally {

        }
        return professor;
    }

    // Metodo para listar todos os professores presentes no banco de dados
    static ArrayList<Professor> listarProfessores() {
        ArrayList<Professor> professores = new ArrayList<>();
        Professor professor = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                    "123456789");
            String sql = "SELECT * FROM professor";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet consulta = instrucao.executeQuery();
            while (consulta.next()) {
                int idKey = consulta.getInt("id_prof");
                int id_p = consulta.getInt("id_p");
                String nomeDoProfessor = consulta.getString("nome");
                String tituloDoProfessor = consulta.getString("titulo");
                int cargaHoraria = consulta.getInt("carga_horaria");
                professor = new Professor(nomeDoProfessor, tituloDoProfessor, id_p);
                professor.setCargaHoraria(cargaHoraria);
                professor.setIdKey(idKey);
                professores.add(professor);
            }

            for (Professor prof : professores) {
                System.out.println(prof);
            }

        } catch (SQLException | DadosDoProfessorInvalidoException | InputMismatchException e) {
            e.printStackTrace();
        }
        return professores;
    }

    static void excluirProfessor() {
        Scanner entrada = new Scanner(System.in);
        Professor professor = null;

        try {
            int linhasAfetadas;
            int idDoProfessor;
            String sql;
            // realizando conexão com o banco de dados
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                    "123456789");
            PreparedStatement instrucao;
            ResultSet consulta;
            // Inserimos um id do professor a ser excluido do sistema
            System.out.println("\nLista de professores:");
            listarProfessores();
            System.out.println("\nInsira o id do professor a ser excluido: ");
            idDoProfessor = entrada.nextInt();
            professor = getDadosDoProfessor(idDoProfessor);
            clearBuffer(entrada);
            // primeira consulta
            sql = "SELECT id_p FROM componentes WHERE id_prof = ?";
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, professor.getIdKey());
            consulta = instrucao.executeQuery();
            // segunda consulta
            if (consulta.next()) {
                sql = "UPDATE componentes SET id_prof = null WHERE id_prof = ?";
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, professor.getIdKey());
                linhasAfetadas = instrucao.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Sucesso");
                } else {
                    System.out.println("Erro");
                }
            }
            // terceira consulta
            sql = "DELETE FROM professor WHERE id_p = ?";
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, professor.getId());
            linhasAfetadas = instrucao.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Professor deletado com sucesso");
            } else {
                System.out.println("Não foi possivel deletar o professor");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }

    }

    static ComponenteCurricular cadastrarComponenteCurricular() throws InputMismatchException {
        ComponenteCurricular componenteASerAdicionado = null;
        Professor professor = null;
        ArrayList<Professor> profs = listarProfessores();
        ArrayList<ComponenteCurricular> comps = listarComponentesCurriculares();
        String r = "S";
        Scanner entrada = new Scanner(System.in);
        while (r.equals("S") || r.equals("s") || r.equals("sim")) {
            try {
                // Variaveis que armazenam os dados do componente curricular a ser cadastrado

                String nomeDoComponenteCurricular;
                int idComponente;
                int cargaHorariaComponente = 0;
                int id_prof;
                System.out.println("\nInsira os dados do componente curricular a ser cadastrado: ");
                System.out.print("\nNome do componente curricular: ");
                nomeDoComponenteCurricular = entrada.nextLine().trim();

                System.out.print("\nID do componente curricular: ");
                idComponente = entrada.nextInt();
                System.out.print("\nCarga horaria do componente curricular: ");
                cargaHorariaComponente = entrada.nextInt();
                System.out.println("\nId do professor para cadastrar a discplina: ");
                id_prof = entrada.nextInt();
                clearBuffer(entrada);
                // Caso seja inserido algum caractere que não seja alfanumerico, lançamos uma
                // exceção, pois o campo cargahoraria deve ser do tipo inteiro
                if (Character.isLetter((char) cargaHorariaComponente)) {
                    throw new InputMismatchException(
                            "Você digitou uma letra, mas deveria ser um caractere alfanumérico!");
                }

                // instanciando um objeto da classe componente a qual armazena os dados que
                // serão enviados para o banco de dados
                componenteASerAdicionado = new ComponenteCurricular(cargaHorariaComponente, nomeDoComponenteCurricular,
                        idComponente);
                System.out.println("\nComponente curricular a ser adicionado: \n");
                // Informando os dados do componente curricular a ser adicionado antes de
                // envia-lo para o BD, afim de que o usuario verifique se os dados estão
                // corretos
                System.out.println(componenteASerAdicionado + "\n");
                r = "";
                System.out.println("\nDeseja adiconar este componente curricular? S/N : \n");
                r = entrada.nextLine().trim();

                // Caso o usuario realmente deseje inserir no BD entramos neste
                // laço if
                if (r.equals("S") || r.equals("s") || r.equals("sim")) {
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael",
                            "mikael", "123456789");
                    String sql = "INSERT INTO componentes (id_prof,nomecomp,id_p,carga_horaria) VALUES (?,?,?,?)";
                    PreparedStatement instrucao = connection.prepareStatement(sql);
                    professor = getDadosDoProfessor(id_prof);
                    // Antes de fazre atualizações e inserções verificamos se o professor
                    // selecionado não está com sua carga horaria maxima
                    if (professor.getCargaHoraria() < 300 && profs.contains(professor)) {
                        int idkey = professor.getIdKey();
                        instrucao.setInt(1, idkey);
                        instrucao.setString(2, componenteASerAdicionado.getNome());
                        instrucao.setInt(3, componenteASerAdicionado.getID());
                        instrucao.setInt(4, componenteASerAdicionado.getCargaHoraria());
                        int linhasAfetadas = 0;
                        boolean sit = true;
                        if (professor.getCargaHoraria() + componenteASerAdicionado.getCargaHoraria() <= 300) {
                            linhasAfetadas = instrucao.executeUpdate();
                        } else {
                            sit = false;
                            if (professor.getCargaHoraria() + componenteASerAdicionado.getCargaHoraria() > 300) {
                                System.out.println(
                                        "\nA carga horaria da nova disciplina somada com a do professor selecionado ultrapassa a carga horaria limite");
                            }
                        }

                        if (linhasAfetadas > 0) {
                            System.out.println("\nInserido com sucesso");
                        } else {
                            System.out.println("\nInserção falhou");
                        }
                        // Caso o atributo sit seja true, podemos realizar a inserçao do professor no
                        // banco de dados
                        if (sit == true) {
                            sql = "UPDATE professor SET carga_horaria = ? WHERE id_p = ?";
                            PreparedStatement instrucao2 = connection.prepareStatement(sql);
                            professor.adicionaComponenteCurricular(cargaHorariaComponente, nomeDoComponenteCurricular,
                                    id_prof);
                            instrucao2.setInt(1, professor.getCargaHoraria());
                            instrucao2.setInt(2, id_prof);
                            linhasAfetadas = instrucao2.executeUpdate();
                            if (linhasAfetadas > 0) {
                                System.out.println("\nO sistema atualizou a carga horaria do professor");
                            } else {
                                System.out.println("\nNão foi possivel atualizar a carga horaria do professor");
                            }
                        } else {
                            System.out.println("\nNão foi possivel realizar as aplicações no sistema");
                        }
                    } else {
                        System.out.println(
                                "O professor ja possui carga horaria maxima ou o professor já está cadastrado no sistema");
                    }

                }
                // Logo depois perguntamos se o mesmo deseja inserir mais algum componente, se
                // sim continuamos com as inserções, caso não retornamos ao menu de ops
                System.out.println("\nDeseja adicionar mais algum componente curricular?");
                System.out.println("\nS/N ?");
                r = entrada.nextLine().trim();
            } catch (InputMismatchException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido
                    | SQLException e) {
                e.printStackTrace();
            }
            // Verificar a resposta do usuario, caso ele responda com qualquer resposta
            // diferente das que
            // Fazem o laço continuar se repetindo, o programa sai do laço de repetição
            // while e retorna ao menu
            // Usado na main

        }

        return componenteASerAdicionado;

    }

    static ComponenteCurricular editarComponenteCurricular() {
        Scanner entrada = new Scanner(System.in);
        ComponenteCurricular componente = null;
        ArrayList<Professor> profs = listarProfessores();
        limparConsole();
        listarComponentesCurriculares();
        try {

            int idDoComponente;
            System.out.println("\nInsira o id do componente a qual quer editar: ");
            idDoComponente = entrada.nextInt();
            // Aqui chamamos um metodo para que possamos guardar dados atuais do banco de
            // dados e não perde-los caso o usuario não deseje alterar algum dos dados
            componente = getComponenteCurricular(idDoComponente);
            clearBuffer(entrada);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                    "123456789");
            PreparedStatement instrucao;
            String sql;
            String idProfessor;
            // Abaixo temos alguns ifs que servem para que o usuario possa decidir ou não
            // inserir valores novos para o componente curricular
            // Caso deseje manter algum dos dados atuais, então ele deve apenas pressionar
            // enter e o dado atual irá se manter
            System.out.println(
                    "\nSe deseja alterar o id do professor a qual a disciplina está vinculada digite um id, se deseja manter valor atual digite enter");
            idProfessor = entrada.nextLine();
            if (idProfessor.isEmpty()) {
                idProfessor = Integer.toString(componente.getIdProf());
            }
            // Utilizei o metodo de transformar um numero inteiro em uma string para
            // armazena-lo em uma string sem ter problemas de buffer quando utilizo o
            // nextInt
            // Repetimos o processo até que o usuario decida quais campos ele deseja alterar
            System.out.println(
                    "\nSe deseja alterar o nome do componente digite um nome, se deseja manter valor atual digite enter");
            String nomeComp = entrada.nextLine().trim();
            if (nomeComp.isEmpty()) {
                nomeComp = componente.getNome();
            }
            System.out.println(
                    "\nSe deseja alterar o id do componente digite um id, se deseja manter valor atual digite enter");
            String id_p;
            id_p = entrada.nextLine();
            if (id_p.isEmpty()) {
                id_p = Integer.toString(componente.getID());
            }
            System.out.println(
                    "\nSe deseja alterar a carga horaria do componente digite um novo valor, se deseja manter valor atual digite enter");
            System.out.println("\nInserir valor de 30 ou 60");
            String carga_horaria;
            carga_horaria = entrada.nextLine();
            if (carga_horaria.isEmpty()) {
                carga_horaria = Integer.toString(componente.getCargaHoraria());
            }
            //realizamos essa consulta para pegar o id do professor e utiliza-lo, ao inves de sua chave primaria
            sql = "SELECT id_p FROM professor WHERE id_prof = ?";
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, componente.getIdProf());
            ResultSet consulta = instrucao.executeQuery();
            int id_prof = 0;
            while (consulta.next()) {
                id_prof = consulta.getInt("id_p");
            }
            // Caso o id do professor inserido seja diferente do id atual do professor, quer
            // dizer que aquele professor não leciona mais aquela disciplina e sua carga
            // horaria mudou juntamente da de outro professor e devemos atualiza-las
            if (Integer.parseInt(idProfessor) != id_prof) {
                Professor professor = getDadosDoProfessor(Integer.parseInt(idProfessor));
                if (professor.getCargaHoraria() < 300 && profs.contains(professor)) {
                    // Atualizamos primeiro os dados do novo professor do componente
                    if (professor.getCargaHoraria() + Integer.parseInt(carga_horaria) <= 300) {

                        sql = "UPDATE professor SET carga_horaria = ? WHERE id_p = ?";
                        professor.adicionaComponenteCurricular(Integer.parseInt(carga_horaria), nomeComp,
                                Integer.parseInt(id_p));
                        instrucao = connection.prepareStatement(sql);
                        instrucao.setInt(1, professor.getCargaHoraria());
                        instrucao.setInt(2, Integer.parseInt(idProfessor));
                        int linhasAfetadas = instrucao.executeUpdate();
                        if (linhasAfetadas > 0) {
                            System.out.println("Dados do professor com id " + Integer.parseInt(idProfessor)+ " atualizados com sucesso");
                        } else {
                            System.out.println("Não foi possivel modificar os dados do professor com id "+ Integer.parseInt(idProfessor));
                        }
                        // Em seguida atualizamos os dados do professor que teve o componente removido
                        // de sua grade
                        instrucao = connection.prepareStatement(sql);
                        professor = getDadosDoProfessor(id_prof);
                        professor.removerComponenteCurricular(nomeComp, Integer.parseInt(id_p));
                        instrucao.setInt(1, professor.getCargaHoraria());
                        instrucao.setInt(2, componente.getIdProf());
                        linhasAfetadas = instrucao.executeUpdate();
                        if (linhasAfetadas > 0) {
                            System.out.println("Dados do professor com id " + componente.getIdProf() + " atualizados com sucesso");
                        } else {
                            System.out.println("Não foi possivel modificar os dados do professor com id " + componente.getIdProf());
                        }

                        // Atualizamos os dados, sendo ou não alterados, em caso de nçao alteração
                        // mantém-se dados atuais que foram armazenados anteriormente no inicio deste
                        // metodo
                        sql = "UPDATE componentes SET id_prof = ?, nomecomp = ?, id_p = ?, carga_horaria = ? WHERE id_p = ?";
                        instrucao = connection.prepareStatement(sql);
                        instrucao.setInt(1,professor.getIdKey());
                        instrucao.setString(2, nomeComp);
                        instrucao.setInt(3, idDoComponente);
                        instrucao.setInt(4, Integer.parseInt(carga_horaria));
                        instrucao.setInt(5, idDoComponente);
                        linhasAfetadas = instrucao.executeUpdate();
                    if (linhasAfetadas > 0) {
                        System.out.println("Sucesso na atualização do sistema");
                    } else {
                       System.out.println("Atualização no sistema falhou");
                    }

                    } else {
                        System.out.println("\nA carga horaria do professor não pode ultrapassar 300 horas");
                    }

                } else {
                    System.out.println("O novo id do professor não está no sistema");
                }

            } else {
                // Atualizamos os dados, sendo ou não alterados, em caso de nçao alteração
                // mantém-se dados atuais que foram armazenados anteriormente no inicio deste
                // metodo
                Professor professor = getDadosDoProfessor(Integer.parseInt(idProfessor));
                sql = "UPDATE componentes SET id_prof = ?, nomecomp = ?, id_p = ?, carga_horaria = ? WHERE id_p = ?";
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1,professor.getIdKey());
                instrucao.setString(2, nomeComp);
                instrucao.setInt(3, idDoComponente);
                instrucao.setInt(4, Integer.parseInt(carga_horaria));
                instrucao.setInt(5, idDoComponente);
                int linhasAfetadas = instrucao.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("Sucesso na atualização do sistema");
                } else {
                    System.out.println("Atualização no sistema falhou");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return componente;
    }

    static ComponenteCurricular verComponenteCurricular(int idComp) {
        Scanner entrada = new Scanner(System.in);
        ComponenteCurricular componente = null;
        System.out.println("\nInsira o id do componente: ");
        idComp = entrada.nextInt();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
            String sql = "SELECT id_comp, id_prof, nomecomp, id_p, carga_horaria FROM componentes WHERE id_p = ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, idComp);
            ResultSet consulta = instrucao.executeQuery();
            //Utilizamos o laço while nas consultas com a classe ResultSet para verificar se há algum elemento na linha que corresponde ao que buscamos
            //Caso exista, armazenamos os dados em um objeto e o exibimos
            while (consulta.next()) {
                int idProf = consulta.getInt("id_prof");
                int idKey = consulta.getInt("id_comp");
                String nome = consulta.getString("nomecomp");
                int id_p = consulta.getInt("id_p");
                int carga_horaria = consulta.getInt("carga_horaria");
                componente = new ComponenteCurricular(carga_horaria, nome, id_p);
                componente.setIdProf(idProf);
                componente.setID(id_p);
                componente.setIdKey(idKey);
                System.out.println(componente);
            }
        } catch (SQLException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
            e.printStackTrace();
        }

        return componente;
    }

    static ComponenteCurricular getComponenteCurricular(int idComp) {
        ComponenteCurricular componente = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                    "123456789");
            String sql = "SELECT id_comp, id_prof, nomecomp, id_p, carga_horaria FROM componentes WHERE id_p = ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, idComp);
            ResultSet consulta = instrucao.executeQuery();
            while (consulta.next()) {
                int idProf = consulta.getInt("id_prof");
                int idKey = consulta.getInt("id_comp");
                String nome = consulta.getString("nomecomp");
                int id_p = consulta.getInt("id_p");
                int carga_horaria = consulta.getInt("carga_horaria");
                componente = new ComponenteCurricular(carga_horaria, nome, id_p);
                componente.setIdProf(idProf);
                componente.setID(id_p);
                componente.setIdKey(idKey);
                System.out.println(componente);
            }
        } catch (SQLException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
            e.printStackTrace();
        }

        return componente;
    }
//Faz uma lista dos componente curriculares
    static ArrayList<ComponenteCurricular> listarComponentesCurriculares() {
        ArrayList<ComponenteCurricular> componentes = new ArrayList<>();
        ComponenteCurricular componente = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael","123456789");
            String sql = "SELECT * FROM componentes";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet consulta = instrucao.executeQuery();
            while (consulta.next()) {
                int idKey = consulta.getInt("id_comp");
                int id_prof = consulta.getInt("id_prof");
                String nomeComp = consulta.getString("nomecomp");
                int id_p = consulta.getInt("id_p");
                int cargaHoraria = consulta.getInt("carga_horaria");
                componente = new ComponenteCurricular(cargaHoraria, nomeComp, id_p);
                componente.setIdProf(id_prof);
                componente.setID(id_p);
                componente.setIdKey(idKey);
                componentes.add(componente);
            }

            for (ComponenteCurricular comp : componentes) {
                System.out.println(comp);
            }

        } catch (SQLException | InputMismatchException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
            e.printStackTrace();
        }
        return componentes;
    }
//Exclui um componente curricular do sistema
    static void excluirComponenteCurricular() {
        Scanner entrada = new Scanner(System.in);
        ComponenteCurricular componente = null;
        Professor professor = null;
        try {
            int idComp;
            System.out.println("\nLista dos componentes: ");
            listarComponentesCurriculares();
            System.out.println("\nInsira o id da disciplina a ser excluida: ");
            idComp = entrada.nextInt();
            // Primeiro buscamos o componente a qual foi inserido o id e armazenamos em um
            // objeto
            componente = getComponenteCurricular(idComp);
            clearBuffer(entrada);
            // Neste objeto que obtivemos, temos também o id do professor a qual ela está
            // vinculada
            if (componente.getIdProf() != 0) {
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                        "123456789");
                PreparedStatement instrucao;
                String sql;
                // Consulta realizada para armazenar dados do professor a ser modificado no
                // banco de dados após exclusão do componene curricular
                sql = "SELECT id_p FROM professor WHERE id_prof = ?";
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, componente.getIdProf());
                ResultSet consulta = instrucao.executeQuery();
                int idProf = 0;
                while (consulta.next()) {
                    idProf = consulta.getInt("id_p");
                }

                professor = getDadosDoProfessor(idProf);
                // Consulta a realizar para atualizar os dados do professor a ser modificado
                // após exclusão do componente curricular
                sql = "UPDATE professor SET carga_horaria = ? WHERE id_prof = ?";
                professor.removerComponenteCurricular(componente.getNome(), idComp);
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, professor.getCargaHoraria());
                instrucao.setInt(2, professor.getIdKey());
                int linhasAfetadas = instrucao.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\nCarga horaria do professor atualizada");
                } else {
                    System.out.println("\nNão foi possivel remover atualizar a carga horaria do professor");
                }
                // Finalmente deletando a disciplina do banco de dados
                sql = "DELETE FROM componentes WHERE id_p = ?";
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, componente.getID());
                linhasAfetadas = instrucao.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\nComponente removido com sucesso do sistema");
                } else {
                    System.out.println("\nNão foi possivel remover o componente do sistema");
                }

            } else {
                // Caso a disciplina esteja sem nenhum professor vinculado a esse componente,
                // podemos exclui-lo do banco de dados de forma direta
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael",
                        "123456789");
                String sql = "DELETE FROM componentes WHERE id_p = ?";
                PreparedStatement instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, componente.getID());
                int linhasAfetadas = instrucao.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\nComponente removido com sucesso do sistema");
                } else {
                    System.out.println("\nNão foi possivel remover o componente do sistema");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
    }

    static void cadastrarTurma() {
        Turma turma = null;
        ComponenteCurricular c = null;
        Scanner entrada = new Scanner(System.in);
        try {
            //Lista os componente curriculares para que o usuario possa visualizar melhor para qual disciplina se quer adicionar uma turma 
            listarComponentesCurriculares();
            System.out.println("\nInsira o id do componente curricular a qual quer adicionar uma turma: ");
            int idComp = entrada.nextInt();
            c = getComponenteCurricular(idComp);
            turma = new Turma(c.getNome(), idComp);
            System.out.println("\nInsira o semestre a qual a turma está vinculada (1 ou 2): ");
            int semestre = entrada.nextInt();
            turma.setSemestre(semestre);
            System.out.println("\nInsira o id da turma: ");
            int id_t = entrada.nextInt();
            System.out.println("\nInsira o id do professor para vincular a turma: ");
            int id_p = entrada.nextInt();
            Professor professor = getDadosDoProfessor(id_p);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael","123456789");
            String sql = "insert into turma (id_comp, id_prof, nome_turma, id_t, semestre) VALUES (?,?,?,?,?)";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, c.getIdKey());
            instrucao.setInt(2, professor.getIdKey());
            instrucao.setString(3, c.getNome());
            instrucao.setInt(4, id_t);
            instrucao.setInt(5, semestre);
            int linhasAfetadas = instrucao.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("\nAdição de turma foi um sucesso");
            } else {
                System.out.println("\nAdição de turma falhou");
            }
            int contagemProfs = 0;
            sql = "SELECT id_prof FROM turma WHERE id_t = ?";
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, id_t);
            ResultSet consulta = instrucao.executeQuery();
            ArrayList<Integer> ids = new ArrayList<>();
            while (consulta.next()) {
                int id_prof = consulta.getInt("id_prof");
                ids.add(id_prof);
                System.out.println("\nID do professor: " + id_prof);
                contagemProfs++;
            }

            if (contagemProfs > 1) {

            for(int i = 0; i < ids.size(); i++){
                sql = "SELECT carga_horaria FROM professor WHERE id_prof = ?";
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1, ids.get(i));
                consulta = instrucao.executeQuery();
                int carga_horaria = 0;
                while(consulta.next()){
                carga_horaria = consulta.getInt("carga_horaria");
                }
                carga_horaria -= c.getCargaHoraria()/contagemProfs;
                sql = "UPDATE professor SET carga_horaria = ? WHERE id_prof = ?";
                instrucao = connection.prepareStatement(sql);
                instrucao.setInt(1,carga_horaria);
                instrucao.setInt(2,ids.get(i));
                linhasAfetadas = instrucao.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("\nCarga horaria dos professores atualizada com sucesso");
                    }else{
                        System.out.println("\nNão foi possivel atualizar a carga horaria dos professores");
                    }
                }             
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void editarTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarTurma'");
    }

    static void verDadosDaTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verDadosDaTurma'");
    }

    static ArrayList<Turma> listarTurmas() {
        ArrayList<Turma> turmas = new ArrayList<>();
        Turma turma = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael","123456789");
            String sql = "SELECT * FROM turma";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            ResultSet consulta = instrucao.executeQuery();
            while (consulta.next()) {
                int idKey = consulta.getInt("id_turma");
                int idComp = consulta.getInt("id_comp");
                int idProf = consulta.getInt("id_prof");
                String nomeTurma = consulta.getString("nome_turma");
                int id_t = consulta.getInt("id_t");
                int semestre = consulta.getInt("semestre");
                turma = new Turma(nomeTurma, semestre);
                turma.setIdKey(idKey);
                turma.setIdComp(idComp);
                turma.setId(id_t);
                turma.setIdProf(idProf);
                turmas.add(turma);
            }

            for (Turma t : turmas) {
                System.out.println(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turmas;
    }

    static ArrayList<Turma> listarTurmasPorSemestre() {
        ArrayList<Turma> turmas = new ArrayList<>();
        Scanner entrada = new Scanner(System.in);
        Turma turma = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael","123456789");
            String sql = "SELECT * FROM turma WHERE semestre = ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            System.out.println("\nInsira o semestre para listar as turmas (1 ou 2): ");
            int s = entrada.nextInt();
            instrucao.setInt(1, s);
            ResultSet consulta = instrucao.executeQuery();
            while (consulta.next()) {
                int idKey = consulta.getInt("id_turma");
                int idComp = consulta.getInt("id_comp");
                int idProf = consulta.getInt("id_prof");
                String nomeTurma = consulta.getString("nome_turma");
                int id_t = consulta.getInt("id_t");
                int semestre = consulta.getInt("semestre");
                turma = new Turma(nomeTurma, semestre);
                turma.setIdKey(idKey);
                turma.setIdComp(idComp);
                turma.setId(id_t);
                turma.setIdProf(idProf);
                turmas.add(turma);
            }

            for (Turma t : turmas) {
                System.out.println(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turmas;
    }

    static ArrayList<Turma> listarTurmasPorProfessor() {
        ArrayList<Turma> turmas = new ArrayList<>();
        Professor professor = null;
        Scanner entrada = new Scanner(System.in);
        Turma turma = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael","123456789");
            String sql = "SELECT * FROM turma WHERE id_prof = ?";
            listarProfessores();
            PreparedStatement instrucao = connection.prepareStatement(sql);
            System.out.println("\nescolha qual professor deseja visualizar as turmas (insira o id do professor desejado): ");
            int IDP = entrada.nextInt();
            professor = getDadosDoProfessor(IDP);
            instrucao.setInt(1, professor.getIdKey());
            ResultSet consulta = instrucao.executeQuery();
            while (consulta.next()) {
                int idKey = consulta.getInt("id_turma");
                int idComp = consulta.getInt("id_comp");
                int idProf = consulta.getInt("id_prof");
                String nomeTurma = consulta.getString("nome_turma");
                int id_t = consulta.getInt("id_t");
                int semestre = consulta.getInt("semestre");
                turma = new Turma(nomeTurma, semestre);
                turma.setIdKey(idKey);
                turma.setIdComp(idComp);
                turma.setId(id_t);
                turma.setIdProf(idProf);
                turmas.add(turma);
            }

            for (Turma t : turmas) {
                System.out.println(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return turmas;
        }
    

    static void excluirTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluirTurma'");
    }

    private static ArrayList<Professor> professoresCad = new ArrayList<>();

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    public static void limparConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}
