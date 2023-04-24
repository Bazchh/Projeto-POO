import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {

    private ArrayList <Professor> professores = new ArrayList<>(Arrays.asList());
private ArrayList <String> nomesDosProfessores = new ArrayList<>(Arrays.asList("ÁDLLER DE OLIVEIRA GUIMARÃES","ALVARO ALVARES DE CARVALHO CESAR SOBRINHO", "ANTONIO DIEGO SILVA FARIAS","BRUNO FONTES DE SOUSA", "CLAUDIO ANDRES CALLEJAS OLGUIN",
"CLAUDIO DE SOUZA ROCHA", "CLECIDA MARIA BEZERRA BESSA","VERÔNICA MARIA LIMA SILVA", "FELIPE TORRES LEITE", "GLAUBER BARRETO LUNA", "GLAYDSON F B DE OLIVEIRA", "HELDER FERNANDO DE ARAUJO OLIVEIRA", "HIDALYN THEODORY CLEMENTE MATTOS DE SOUZA", "JARBELE CASSIA DA SILVA COUTINHO", "KATIA CILENE DA SILVA SANTOS",
"LAURO CESAR BEZERRA NOGUEIRA", "LAYSA MABEL DE OLIVEIRA FONTES", "LENARDO CHAVES E SILVA", "LINO MARTINS DE HOLANDA JUNIOR",
"MONICA PAULA DE SOUSA", "NATHALEE CAVALCANTI DE ALMEIDA", "PAULO GUSTAVO DA SILVA", "PAULO HENRIQUE DAS CHAGAS SILVA", 
"PEDRO THIAGO VALERIO DE SOUZA", "RAIMUNDO LEIRTON FREITAS MAIA", "REUDISMAM ROLIM DE SOUSA", "ROBSON LOCATELLI MACEDO", 
"RODRIGO SOARES SEMENTE", "SHARON DANTAS DA CUNHA", "THATYARA FREIRE DE SOUZA", "THIAGO PEREIRA RIQUE", "VINICIUS SAMUEL VALERIO DE SOUZA", "WILLIAM VIEIRA GOMES"));
    static Professor cadastrarProfessor() {
        Scanner entrada = new Scanner(System.in);
        String nomeDoProfessor;
        String tituloDoProfessor;
        System.out.println("Insira o nome do professor a ser cadastrado: ");
        nomeDoProfessor = entrada.nextLine();
        System.out.println("Insira o titulo do professor a ser cadastrado: ");
        tituloDoProfessor = entrada.nextLine();
        Professor professor = new Professor(nomeDoProfessor, tituloDoProfessor);

        return professor;
    }
    
    static void editarProfessor() {
        Scanner entrada = new Scanner(System.in);
        String nomeDoProfessor;
        System.out.println("Insira o nome do professor a ser editado: ");
        nomeDoProfessor = entrada.nextLine();
        Professor professor  = new Professor(nomeDoProfessor);
    }

    
    public void verDadosProfessor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verDadosProfessor'");
    }

    
    public void listarProfessores() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarProfessores'");
    }

    
    public void excluirProfessor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluirProfessor'");
    }

    
    public void cadastrarComponenteCurricular() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cadastrarComponenteCurricular'");
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
