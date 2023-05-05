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
                    professoresCad.add(professor);
                    
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    //Fecha o scanner
                    
                }
            } else {
                // Caso o professor o nome do professor não esteja dentro da nossa arraylist,
                // mostramos a seguinte mensagem
                System.out.println("O professor a ser cadastrado no banco de dados já está cadastrado");
            }
        } catch (InputMismatchException e){
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
        Professor professor = verDadosDoProfessor(idDoProfessor);
        try {   
            String nomeDoProfessor;                    
                try {
                    //Atributo do tipo connection usado para realizar a conexão com nosso banco de dados
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                //String usada para realizar a inserção de dados no banco de dados                                        
                String tituloDoProfessor;
                System.out.println("Insira o nome do professor para atualizar o nome do professor selecionado, caso deseje manter o valor atual pressione enter: ");
                nomeDoProfessor = entrada.nextLine().trim();
                if(nomeDoProfessor.isEmpty()){
                    nomeDoProfessor = professor.getNome();
                }
                System.out.println("Insira o titulo para atualizar o titulo do professor selecionado, caso deseje manter o valor atual pressione enter: ");
                tituloDoProfessor = entrada.nextLine().trim();
                if(tituloDoProfessor.isEmpty()){
                    tituloDoProfessor = professor.getTitulo();
                }
                String sql = "UPDATE professor SET nome = ?, titulo = ? WHERE id_prof = ?";
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
           
                connection.close();
                
                } catch (SQLException e) {
                    e.printStackTrace();
                }
             
        } finally{

        }
    }

    static Professor verDadosDoProfessor(int idProfessor) {
        Scanner entrada = new Scanner(System.in);   
        Professor professor = null;
        if(idProfessor == 0){
            System.out.println("Insira o id do professor para ver seus dados: ");
            idProfessor = entrada.nextInt();
            clearBuffer(entrada);
        }
        try {           
            
                try {
                    //Preparando conxexão com o banco de dados
                    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                    String sql = "SELECT * FROM professor WHERE id_p = ?";
                    PreparedStatement instrucao = connection.prepareStatement(sql); 
                    instrucao.setInt(1, idProfessor);
                    //executando consulta
                    ResultSet consulta = instrucao.executeQuery();
                    //Usando laço while para capturar os dados do professor buscado
                    while(consulta.next()){
                    int idKey = consulta.getInt("id_prof");
                    int id_p = consulta.getInt("id_p");
                    String nomeDoProfessor = consulta.getString("nome");
                    String tituloDoProfessor = consulta.getString("titulo");
                    int cargaHoraria = consulta.getInt("carga_horaria");
                    professor = new Professor(nomeDoProfessor, tituloDoProfessor, id_p);
                    professor.setCargaHoraria(cargaHoraria);
                    professor.setIdKey(idKey);
                    
                    }
                    //preparando nova consulta
                    sql = "SELECT * FROM componentes WHERE id_prof = ?";
                    PreparedStatement instrucao2 = connection.prepareStatement(sql);
                    instrucao2.setInt(1, professor.getId());
                    //executando consulta
                    consulta = instrucao2.executeQuery();
                    //capturando componentes curriculares do professor
                    while(consulta.next()){
                    int cargaHorariaComp = consulta.getInt("carga_horaria");
                    String nomeComp = consulta.getString("nome");
                    int idComp = consulta.getInt("id");
                    professor.adicionaComponenteCurricular(cargaHorariaComp, nomeComp, idComp);
                        }      

                    System.out.println(professor);

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

    static ArrayList<Professor> listarProfessores() {
        ArrayList <Professor> professores = new ArrayList<>();
        Professor professor = null;
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
         professor.setCargaHoraria(cargaHoraria);
         professor.setIdKey(idKey);
         professores.add(professor);
        }
       
          
        for(Professor prof : professores){
            System.out.println(prof);
        }
            
        } catch (SQLException | DadosDoProfessorInvalidoException | InputMismatchException e) {
                    e.printStackTrace();
        }
        return professores;
    }

    static void excluirProfessor() {
        Scanner entrada = new Scanner(System.in);

        try {
            int idDoProfessor;
            //Inserimos um id do professor a ser excluido do sistema
            System.out.println("Lista de professores:");
            listarProfessores();
            System.out.println("\nInsira o id do professor a ser excluido: ");
            idDoProfessor = entrada.nextInt();
            clearBuffer(entrada);
            //realizando conexão com o banco de dados
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

    static ComponenteCurricular cadastrarComponenteCurricular() throws InputMismatchException {
        ComponenteCurricular componenteASerAdicionado = null;
        Professor professor = null;
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
                //Caso seja inserido algum caractere que não seja alfanumerico, lançamos uma exceção, pois o campo cargahoraria deve ser do tipo inteiro
                if (Character.isLetter((char) cargaHorariaComponente)) {
                        throw new InputMismatchException("Você digitou uma letra, mas deveria ser um caractere alfanumérico!");
                }
                
                // instanciando um objeto da classe componente a qual armazena os dados que
                // serão enviados para o banco de dados
                componenteASerAdicionado = new ComponenteCurricular(cargaHorariaComponente,nomeDoComponenteCurricular, idComponente);
                System.out.println("Componente curricular a ser adicionado: ");
                // Informando os dados do componente curricular a ser adicionado antes de
                // envia-lo para o BD, afim de que o usuario verifique se os dados estão
                // corretos
                System.out.println(componenteASerAdicionado + "\n");
                r = "";
                System.out.println("\nDeseja adiconar este componente curricular? S/N :");
                r = entrada.nextLine().trim();

                // Caso o usuario realmente deseje inserir no BD o componente entramos neste
                // laço if
                if (r.equals("S") || r.equals("s") || r.equals("sim")) {
                   Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
                   String sql = "INSERT INTO componentes (id_prof,nomecomp,id_p,carga_horaria) VALUES (?,?,?,?);";
                   PreparedStatement instrucao = connection.prepareStatement(sql);
                   professor = verDadosDoProfessor(id_prof);
                   System.out.println(professor.getIdKey());
                   id_prof = professor.getIdKey();
                   instrucao.setInt(1, id_prof);
                   instrucao.setString(2,componenteASerAdicionado.getNome());
                   instrucao.setInt(3,componenteASerAdicionado.getID());
                   instrucao.setInt(4,componenteASerAdicionado.getCargaHoraria());
                   int linhasAfetadas = instrucao.executeUpdate();
                   if(linhasAfetadas > 0){
                    System.out.println("Inserido com sucesso");
                   } else{
                    System.out.println("Inserção falhou");
                   }
                   
                   sql = "UPDATE professor SET carga_horaria = ? WHERE id_prof = ?";
                   PreparedStatement instrucao2 = connection.prepareStatement(sql);
                   professor.adicionaComponenteCurricular(cargaHorariaComponente, nomeDoComponenteCurricular, id_prof);
                   int cargaHorariaProf = 0;
                   cargaHorariaProf = cargaHorariaProf + professor.getCargaHoraria();
                   instrucao2.setInt(1, cargaHorariaProf);
                   instrucao2.setInt(2, id_prof);
                   linhasAfetadas = instrucao2.executeUpdate();
                   if(linhasAfetadas > 0){
                    System.out.println("O sistema atualizou a carga horaria do professor");
                   } else{
                    System.out.println("Não foi possivel atualizar a carga horaria do professor");
                   }

              
                }
            
                // Logo depois perguntamos se o mesmo deseja inserir mais algum componente, se
                // sim continuamos com as inserções, caso não retornamos ao menu de ops
                System.out.println("Deseja adicionar mais algum componente curricular?");
                System.out.println("S/N ?");
                r = entrada.nextLine().trim();
            } catch (InputMismatchException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido | SQLException e) {
                e.printStackTrace();
            }
            // Verificar a resposta do usuario, caso ele responda com qualquer resposta
            // diferente das que
            // Fazem o laço continuar se repetindo, o programa sai do laço de repetição
            // while e retorna ao menu
            // Usado na main

        } 
        entrada.close();
        return componenteASerAdicionado;
        
    }

    static ComponenteCurricular editarComponenteCurricular() {
        Scanner entrada = new Scanner(System.in);
        ComponenteCurricular componente = null;
        
        try {

            int idDoComponente;
            System.out.println("Insira o id do componente a qual quer editar: ");
            idDoComponente = entrada.nextInt();
            //Aqui chamamos um metodo para que possamos guardar dados atuais do banco de dados e não perde-los caso o usuario não deseje alterar algum dos dados
            componente = verComponenteCurricular(idDoComponente);
            clearBuffer(entrada);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
            int idProfessor;
            //Abaixo temos alguns ifs que servem para que o usuario possa decidir ou não inserir valores novos para o componente curricular
            //Caso deseje manter algum dos dados atuais, então ele deve apenas pressionar enter e o dado atual irá se manter
            System.out.println("Se deseja alterar o id do professor a qual a disciplina está vinculada digite um id, se deseja manter valor atual digite enter");
            idProfessor = entrada.nextInt();
            clearBuffer(entrada);
            if(idProfessor == 0){
                idProfessor = componente.getID();
            } 
            System.out.println("Se deseja alterar o nome do componente digite um nome, se deseja manter valor atual digite enter");
            String nomeComp = entrada.nextLine().trim();
            if(nomeComp.isEmpty()){
                nomeComp = componente.getNome();
            }
            System.out.println("Se deseja alterar o id do componente digite um id, se deseja manter valor atual digite enter");
            int id_p = entrada.nextInt();
            if(id_p == 0){
                id_p = componente.getID();
            }
            System.out.println("Se deseja alterar a carga horaria do componente digite um novo valor, se deseja manter valor atual digite enter");
            System.out.println("Inserir valor de 30 ou 60");
            int carga_horaria = entrada.nextInt();
            clearBuffer(entrada);
            if(carga_horaria == 0){
                carga_horaria = componente.getCargaHoraria();
            }
            String sql = "UPDATE componentes SET id_prof = ?, nomecomp = ?, id_p = ?, carga_horaria = ? WHERE id_p = ?";
            PreparedStatement instrucao;
            //Atualizamos os dados, sendo ou não alterados, em caso de nçao alteração mantém-se dados atuais que foram armazenados anteriormente no inicio deste metodo
            instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, idProfessor);
            instrucao.setString(2, nomeComp);
            instrucao.setInt(3, id_p);
            instrucao.setInt(4, carga_horaria);
            instrucao.setInt(5, idDoComponente);
            int linhasAfetadas = instrucao.executeUpdate();
            if(linhasAfetadas > 0){
                System.out.println("Sistema atualizado com sucesso");
            } else {
                System.out.println("Atualização do sistema falhou");
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            entrada.close();
        }
        return componente;
    }

    static ComponenteCurricular verComponenteCurricular(int idComp) {
        Scanner entrada = new Scanner(System.in);
        ComponenteCurricular componente = null;
        //Usa-se esta condição em caso de chamar este metodo dentro de outro metodo que necessite usa-lo
        if(idComp == 0){
            System.out.println("Insira o id do componente: ");
            idComp = entrada.nextInt();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
            String sql = "SELECT * FROM componentes WHERE id_p = ?";
            PreparedStatement instrucao = connection.prepareStatement(sql);
            instrucao.setInt(1, idComp);
            ResultSet consulta = instrucao.executeQuery();
            while(consulta.next()){
            int idKey = consulta.getInt("id_comp");
            int id_prof = consulta.getInt("id_prof");
            String nome = consulta.getString("nomecomp");
            int id_p = consulta.getInt("id_p");
            int carga_horaria = consulta.getInt("carga_horaria");
            componente = new ComponenteCurricular(carga_horaria, nome, id_p);
            componente.setID(id_prof);
            componente.setIdKey(idKey);
            System.out.println(componente);
            }
        } catch (SQLException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
            e.printStackTrace();
        }

        return componente;
    }

    static ArrayList<ComponenteCurricular> listarComponentesCurriculares() {
        ArrayList <ComponenteCurricular> componentes = new ArrayList<>();
        ComponenteCurricular componente = null;
        try{ 
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mikael", "mikael", "123456789");
        String sql = "SELECT * FROM componentes";
        PreparedStatement instrucao = connection.prepareStatement(sql);
        ResultSet consulta = instrucao.executeQuery();
        while(consulta.next()){
         int idKey = consulta.getInt("id_comp");
         int id_prof = consulta.getInt("id_prof");
         String nomeComp = consulta.getString("nomecomp");
         int id_p = consulta.getInt("id_p");
         int cargaHoraria = consulta.getInt("carga_horaria");
         componente = new ComponenteCurricular(cargaHoraria, nomeComp, id_p);
         componente.setIdKey(idKey);
         componentes.add(componente);
        }
       
            
        for(ComponenteCurricular comp: componentes){
            System.out.println(comp);
        }
            
        } catch (SQLException | InputMismatchException | ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
                    e.printStackTrace();
        }
        return componentes;
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

    private static ArrayList<Professor> professoresCad = new ArrayList<>();

    private static ArrayList<ComponenteCurricular> componentesBTI = new ArrayList<>();

    private static ArrayList<Turma> turmas = new ArrayList<>();

    private static void clearBuffer(Scanner scanner) {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

}
