import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    
//Arraylist de string com os nomes de todos os professor que possuem atualmente um vinculo a universidade, inicialmente para facilitar a 
//Validação de dados, de busca, remoção e edição de dados
private static ArrayList <String> bancoDeProfessoresAtuais = new ArrayList<>(Arrays.asList("ÁDLLER DE OLIVEIRA GUIMARÃES","ALVARO ALVARES DE CARVALHO CESAR SOBRINHO", "ANTONIO DIEGO SILVA FARIAS","BRUNO FONTES DE SOUSA", "CLAUDIO ANDRES CALLEJAS OLGUIN",
"CLAUDIO DE SOUZA ROCHA", "CLECIDA MARIA BEZERRA BESSA","VERÔNICA MARIA LIMA SILVA", "FELIPE TORRES LEITE", "GLAUBER BARRETO LUNA", "GLAYDSON F B DE OLIVEIRA", "HELDER FERNANDO DE ARAUJO OLIVEIRA", "HIDALYN THEODORY CLEMENTE MATTOS DE SOUZA", "JARBELE CASSIA DA SILVA COUTINHO", "KATIA CILENE DA SILVA SANTOS",
"LAURO CESAR BEZERRA NOGUEIRA", "LAYSA MABEL DE OLIVEIRA FONTES", "LENARDO CHAVES E SILVA", "LINO MARTINS DE HOLANDA JUNIOR",
"MONICA PAULA DE SOUSA", "NATHALEE CAVALCANTI DE ALMEIDA", "PAULO GUSTAVO DA SILVA", "PAULO HENRIQUE DAS CHAGAS SILVA", 
"PEDRO THIAGO VALERIO DE SOUZA", "RAIMUNDO LEIRTON FREITAS MAIA", "REUDISMAM ROLIM DE SOUSA", "ROBSON LOCATELLI MACEDO", 
"RODRIGO SOARES SEMENTE", "SHARON DANTAS DA CUNHA", "THATYARA FREIRE DE SOUZA", "THIAGO PEREIRA RIQUE", "VINICIUS SAMUEL VALERIO DE SOUZA", "WILLIAM VIEIRA GOMES"));

//Metodo que cadastra um professor em nosso banco de dados
static void cadastrarProfessor() {

        Scanner entrada = new Scanner(System.in); //Scanner para ler dados de entrada para preencher os dados do professor a ser cadastrado de acordo com o usuario
        String nomeDoProfessor; //Atributo onde será armazenado o nome do professor a ser cadastrado e passsado para dentro do construtor da classe professor
        String tituloDoProfessor; //Atributo onde será armazenado o titulo do professor a ser cadastrado e passado para dentro do construtor da classe professor
        System.out.println("Insira o nome do professor a ser cadastrado: "); 
        nomeDoProfessor = entrada.nextLine(); //Recebendo dados do nome
        System.out.println("Insira o titulo do professor a ser cadastrado: ");
        tituloDoProfessor = entrada.nextLine(); // Recebendo dados do titulo
        //instanciando o objeto da classe professor com os dados passados
        Professor professor = new Professor(nomeDoProfessor, tituloDoProfessor);
        //Caso o nome informado esteja dentro da nossa arraylist de string com nome de todos os professor com vinculo atualmente
        //Podemos prosseguir e adicionar o professor e seus respectivos dados para o banco de dados
        if(bancoDeProfessoresAtuais.contains(nomeDoProfessor)){
            
        } else {
            //Caso o professor o nome do professor não esteja dentro da nossa arraylist, mostramos a seguinte mensagem
            System.out.println("O professor a ser cadastrado no banco de dados não faz parte do banco de professores do banco de dados atual da universidade");
        }

    }
    //Metodo para editar os dados de um professor
    public static void editarProfessor() {
        //§canner para ler dados a serem modificados no professor selecionado
        Scanner entrada = new Scanner(System.in);
        //Atributo para armazenar o nome do professor a ser buscado para editar
        String nomeDoProfessor;
        System.out.println("Insira o nome do professor a ser editado: ");
        nomeDoProfessor = entrada.nextLine();
        Professor professor  = new Professor(nomeDoProfessor);
        if(bancoDeProfessoresAtuais.contains(nomeDoProfessor)){
            /*utilizar comando update aqui para editar o professor caso ele exista no banco de dados*/
        } else  {
            System.out.println("O professor buscado não existe no banco de dados da universidade");
        }
    }

    
    public void verDadosProfessor() {
        Scanner entrada = new Scanner(System.in);
        String nomeDoProfessor;
        System.out.println("Insira o nome do professor para ver os seus dados: ");
        nomeDoProfessor = entrada.nextLine();
        Professor professor  = new Professor(nomeDoProfessor);
        if(bancoDeProfessoresAtuais.contains(nomeDoProfessor)){
            /*utilizar comando select aqui para ver os dados do professor caso ele exista no banco de dados*/
        } else  {
            System.out.println("O professor buscado não existe no banco de dados da universidade");
        }
    }

    
    public void listarProfessores() {
        ArrayList <Professor> listaDeProfessores = new ArrayList<>();
        
    }

    
    public void excluirProfessor() {
        Scanner entrada = new Scanner(System.in);
        String nomeDoProfessor;
        System.out.println("Insira o nome do professor a ser excluido: ");
        nomeDoProfessor = entrada.nextLine();
        Professor professor  = new Professor(nomeDoProfessor);
        if(bancoDeProfessoresAtuais.contains(professor)){
            /*utilizar comando drop aqui para excluir o professor caso ele exista no banco de dados*/
        } else  {
            System.out.println("O professor buscado não existe no banco de dados da universidade");
        }
    }

    
    public void cadastrarComponenteCurricular() {
        String r = "S";
        while(r == "S" || r == "s" || r == "sim"){
        Scanner entrada = new Scanner(System.in);
        String nomeDoComponenteCurricular;
        String idComponente;
        int cargaHorariaComponente;
        System.out.println("Insira os dados do componente curricular a ser cadastrado: ");
        System.out.print("\nNome do componente curricular: ");
        nomeDoComponenteCurricular = entrada.nextLine();
        System.out.print("\nID do componente curricular: ");
        idComponente = entrada.nextLine();
        System.out.print("\nCarga horaria do componente curricular: ");
        cargaHorariaComponente = entrada.nextInt();
        ComponenteCurricular componenteASerAdicionado = new ComponenteCurricular(cargaHorariaComponente,nomeDoComponenteCurricular,idComponente);
        System.out.println("Componente curricular a ser adicionado: ");
        System.out.println(componenteASerAdicionado.toString());
        System.out.println("Deseja adiconado este componente curricular?");
        System.out.println("S/N ?");
        r = entrada.nextLine();
        if(r == "S" || r == "s" || r == "sim") {
            /*adiciona o componente inserido ao banco */
            
        }
            System.out.println("Deseja adicionar mais algum componente curricular?");
            System.out.println("S/N ?");
            r = entrada.nextLine();
            //Verificar a resposta do usuario, caso ele responda com qualquer resposta diferente das que 
            //Fazem o laço continuar se repetindo, o programa sai do laço de repetição while e retorna ao meno
            //Usado na main
            if(!(r == "S" || r == "s" || r == "sim")){
                System.out.println("Retornando ao menu principal");
            }

        }
    }

    
    public void editarComponenteCurricular() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarComponenteCurricular'");
    }

    
    public void verComponenteCurricular() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verComponenteCurricular'");
    }

    
    public void listarComponentesCurriculares() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarComponentesCurriculares'");
    }

    
    public void excluirComponenteCurricular() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluirComponenteCurricular'");
    }

    
    public void cadastrarTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cadastrarTurma'");
    }

    
    public void editarTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarTurma'");
    }

    
    public void verDadosDaTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verDadosDaTurma'");
    }

    
    public void listarTurmas() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTurmas'");
    }

    
    public void listarTurmasPorSemestre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTurmasPorSemestre'");
    }

    
    public void listarTurmasPorProfessor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarTurmasPorProfessor'");
    }

    
    public void excluirTurma() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluirTurma'");
    }
    
}
