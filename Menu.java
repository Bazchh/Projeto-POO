import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
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
        PreparedStatement instrucao;
        try {
            int id;
            String nomeDoProfessor; // Atributo onde será armazenado o nome do professor a ser cadastrado e passsado
                                    // para dentro do construtor da classe professor
            String tituloDoProfessor; // Atributo onde será armazenado o titulo do professor a ser cadastrado e
                                      // passado para dentro do construtor da classe professor
            System.out.print("Insira o nome do professor a ser cadastrado: ");
            nomeDoProfessor = entrada.nextLine(); // Recebendo dados do nome
            System.out.print("Insira o titulo do professor a ser cadastrado: ");
            tituloDoProfessor = entrada.nextLine(); // Recebendo dados do titulo
            System.out.println("Insira o id do professor a ser cadastrado: ");
            id = entrada.nextInt();
            clearBuffer(entrada);
            // instanciando o objeto da classe professor com os dados passados
            Professor professor = new Professor(nomeDoProfessor, tituloDoProfessor, id);
            // Caso o nome informado esteja dentro da nossa arraylist de string com nome de
            // todos os professor com vinculo atualmente
            // Podemos prosseguir e adicionar o professor e seus respectivos dados para o
            // banco de dados
            if (!professores.contains(professor)) {
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
                    instrucao.setInt(1, professor.getId());
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
                    
                     //Fechando conexão com o banco de dados para que não seja ocupado recurso do banco de dados e adicionando objeto professor a nossa arraylist de professores
                    professores.add(professor);
                    instrucao.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                // Caso o professor o nome do professor não esteja dentro da nossa arraylist,
                // mostramos a seguinte mensagem
                System.out.println(
                        "O professor a ser cadastrado no banco de dados já está cadastrado");
            }
        } catch (InputMismatchException e){
            e.printStackTrace();
        } catch (DadosDoProfessorInvalidoException e) {
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
            int idDoProfessor;
            String nomeDoProfessor;
            System.out.println("Insira o id do professor a ser editado: ");
            idDoProfessor = entrada.nextInt();
            clearBuffer(entrada);
                
                try {
                    //Atributo do tipo connection usado para realizar a conexão com nosso banco de dados
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                //String usada para realizar a inserção de dados no banco de dados
                String sql;
                System.out.println("O que deseja atualizar do professor?");
                System.out.println("1 - Componentes");
                System.out.println("2 - Turmas");
                System.out.println("3 - Nome e titulo do professor");;
                
                String tituloDoProfessor;
                System.out.println("Insira o nome do professor para atualizar o nome do professor selecionado: ");
                nomeDoProfessor = entrada.nextLine();
                System.out.println("Insira o titulo para atualizar o titulo do professor selecionado: ");
                tituloDoProfessor = entrada.nextLine();
                sql = "UPDATE professor SET nome = ?, titulo = ? WHERE id_p = ?";
                PreparedStatement instrucao;
                instrucao = connection.prepareStatement(sql);
                instrucao.setString(1, nomeDoProfessor);
                instrucao.setString(2, tituloDoProfessor);
                instrucao.setInt(3, idDoProfessor);
                int linhasAfetadas = instrucao.executeUpdate();
                if(linhasAfetadas > 0){
                    System.out.println("Atualizado com sucesso");
                } else{
                    System.out.println("Atualização falhou");
                }
                instrucao.close();
                connection.close();
                
                } catch (SQLException e) {
                    e.printStackTrace();
                }
             
        } finally {
            entrada.close();
        }
    }

    static Professor verDadosDoProfessor() {
        Scanner entrada = new Scanner(System.in);
        Professor professor = null;
        try {           
            int idDoProfessor;
            System.out.println("Insira o id do professor para ver os seus dados: ");
            idDoProfessor = entrada.nextInt();
            clearBuffer(entrada);
                try {
                    
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                    String sql = "SELECT id_prof, id_p, nome, titulo, carga_horaria FROM professor WHERE id_p = ?";
                    PreparedStatement instrucao = connection.prepareStatement(sql); 
                    instrucao.setInt(1, idDoProfessor);
                    ResultSet consulta = instrucao.executeQuery(); 
                    while(consulta.next()){
                    int idKey = consulta.getInt("id_prof");
                    int id_p = consulta.getInt("id_p");
                    String nomeDoProfessor = consulta.getString("nome");
                    String tituloDoProfessor = consulta.getString("titulo");
                    int cargaHoraria = consulta.getInt("carga_horaria");
                    professor = new Professor(nomeDoProfessor, tituloDoProfessor, id_p);
                    String sql2 = "SELECT id_comp, id_prof, nomeComp, id_p";
                    //Usar o select acima para selecionar os componentes curriculares do professor correspondente para imprimi-los usando objetos

                    /*System.out.println("Nome do professor: "+ nomeDoProfessor);
                    System.out.println("ID aleatorio do professor: "+id_p);
                    System.out.println("ID KEY do professor: "+ idKey);
                    System.out.println("Titulo do professor: "+tituloDoProfessor);
                    System.out.println("Carga horaria do professor: "+cargaHoraria);*/

                    }
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (DadosDoProfessorInvalidoException e) {
                    e.printStackTrace();
                }
            
        } finally {
            entrada.close();
        }
        return professor;
    }

    static void listarProfessores() {
        ArrayList <Professor> professores = new Arraylist<>();
        try{ 
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
        String sql = "SELECT * FROM professor";
        PreparedStatement instrucao = connection.prepareStatement(sql);
        ResultSet consulta = instrucao.executeQuery();
        while(consulta.next()){
         int idKey = consulta.getInt("id_prof");
         int id_p = consulta.getInt("id_p");
         String nomeDoProfessor = consulta.getString("nome");
         String tituloDoProfessor = consulta.getString("titulo");
         int cargaHoraria = consulta.getInt("carga_horaria");
         professor = new Professor(nomeDoProfessor, tituloDoProfessor, id_p);
         professores.add(professor);
        }
            
        for(Professor prof : professores){
            System.out.println(prof);
        }
            
        } catch (SQLException e) {
                    e.printStackTrace();
        }
    }

    static void excluirProfessor() {
        Scanner entrada = new Scanner(System.in);

        try {
            int idDoProfessor;
            System.out.println("Insira o id do professor a ser excluido: ");
            idDoProfessor = entrada.nextInt();
            clearBuffer(entrada);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
            String sql = "DELETE FROM professor WHERE id_p = ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1,idDoProfessor);
            int linhasAfetadas = instrucao.executeUpdate();
            if(linhasAfetadas > 0){
            System.out.println("Professor deletado com sucesso");
            } else {
            System.out.println("Não foi possivel deletar o professor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            entrada.close();
        }

    }

    static void cadastrarComponenteCurricular() throws InputMismatchException {
        String r = "S";
        Scanner entrada = new Scanner(System.in);
        while (r.equals("S") || r.equals("s") || r.equals("sim")) {
            try {
                // Variaveis que armazenam os dados do componente curricular a ser cadastrado
                String nomeDoComponenteCurricular;
                int idComponente;
                int cargaHorariaComponente = 0;
                System.out.println("Insira os dados do componente curricular a ser cadastrado: ");
                System.out.print("\nNome do componente curricular: ");
                nomeDoComponenteCurricular = entrada.nextLine();
                System.out.print("\nID do componente curricular: ");
                idComponente = entrada.nextInt();
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
                   Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                   String sql = "INSERT INTO Componentes (inserir aqui colunas da tabela de componentes) VALUES (?,?,?,?)";
                   PreparedStatement instrucao = connection.prepareStatement(sql);
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
            int idDoComponente;
            System.out.println("Insira o nome do componente a qual quer editar: ");
            nomeDoComponente = entrada.nextLine();
            System.out.println("Insira o id do componente a qual quer editar: ");
            idDoComponente = entrada.nextInt();
            componente = new ComponenteCurricular(nomeDoComponente, idDoComponente);
            
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

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

}
