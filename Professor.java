
import java.util.LinkedList;
import java.util.Random;

import Exceções.NomeDoComponenteInvalido;
import Exceções.ValoresInvalidosPCargaHoraria;
import Exceções.NomeDoProfessorInvalido;
public class Professor {
    private final String identity = geradorAleatorioIdProfessor();
    private String titulo;
    private String nome; // Variavel que armazena o nome do professor
    private int cargaHoraria; // Variavel utilizada para somar a carga horaria total do professor e verificar se é válida com base no critério estabelecido
    // ArrayList utilizada para guardar os componentes curriculares do professor
    private LinkedList<ComponenteCurricular> componentes = new LinkedList<>(); 
    
    public int getComponentesSize() {
    	return this.componentes.size();
    }

    // Construtor
    public Professor(String nome, String titulo) throws NomeDoProfessorInvalido {
        if(nome.isEmpty()){
            throw new NomeDoProfessorInvalido("Nome do professor não deve estar vazio");
        }
        
        this.nome = nome;
        this.titulo = titulo;
    }

    public Professor(String nome){
        this.nome = nome;
        this.titulo = "";
    }

    // Metodo que adiciona um componente curricular na lista de componentes do
    // professor
    public void adicionaComponenteCurricular(int cargaHoraria, String nome, String iD) {
        ComponenteCurricular componente = null;
        try {
            componente = new ComponenteCurricular(cargaHoraria,nome,iD);
        } catch (ValoresInvalidosPCargaHoraria | NomeDoComponenteInvalido e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (componente != null && !this.componentes.contains(componente)) {
            // Se a carga horaria do professor ainda for menor que 300 horas, então ainda é
            // possivel adicionar
            // horas a carga horaria do professor, logo executamos o comando dentro do if
            if (this.cargaHoraria < 300 && this.cargaHoraria + componente.getCargaHoraria() <= 300) {
                this.componentes.add(componente);
                // Quando adicionamos um componente, verificamos também sua carga horaria e
                // somamos a carga horaria total do professor
                this.cargaHoraria += componente.getCargaHoraria();
                System.out.println("\nComponente curricular adicionado com sucesso");
            } else {
                // Caso a condição anterior não seja satisfeita informamos que o professor já
                // atingiu a carga horaria limite
                System.out.println("\nO professor já atingiu o limite de horas por componentes curriculares");
                System.out.println(
                        "\nNão é possivel adicionar mais " + componente.getCargaHoraria() + " horas na carga horaria");
            }
        } else {
            System.out.println("\nO professor já possui o componente curricular informado");
        }

    }

    // Metodo para remover o componente curricular da grade de um professor
    public void removerComponenteCurricular(String nome, String iD) {
        ComponenteCurricular componente = null;
        try {
            componente = new ComponenteCurricular(nome, iD);
        } catch (NomeDoComponenteInvalido e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Caso o professor possua carga horaria maior que 0 significa que ele possui
        // componentes curriculares em sua grade, logo, podemos verificar se ele possui
        // o componente que buscamos remover
        if (this.componentes.isEmpty()) {
            System.out.println("\nA lista de componentes curriculares do professor está vazia");
        } else if (this.cargaHoraria > 0) {
            if (this.componentes.contains(componente)) { // verificando se o professor possui o componente curricular
                                                         // que deseja remover
                // Buscando a carga horaria do componente a ser removido para diminuir a carga
                // horaria na grade do professor
                for (ComponenteCurricular componenteCurricular : componentes) {
                    if (componenteCurricular.equals(componente)) {
                        this.cargaHoraria -= componenteCurricular.getCargaHoraria();
                    }
                }
                // Removendo componente curricular
                this.componentes.remove(componente);
                System.out.println("\nComponente curricular removido com sucesso");
            } else {
                System.out.println("\nEste professor não possui o componente curricular informado");
            }

        } else {
            System.out.println(
                    "\nNão é possivel remover o componente curricular pois o professor não possui nenhum em sua grade");
        }
    }

    //Metodo para adicionar uma turma ao componente curricular do professor
    //O usuario deve inserir a qual componente do professor ele quer adicionar uma turma, assim o professor irá ficar cadastrado na turma como o professor da mesma
    public void adicionarTurmaAoComponenteDoProfessor(String nome, String id){
        //Primeiro o usuario passará parametros para escolher a qual disciplina ele quer adicionar uma turma
        ComponenteCurricular componente = null;
        try {
            componente = new ComponenteCurricular(nome, id);
        } catch (NomeDoComponenteInvalido e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Verifica-se se o professor contém a disciplina em sua grade curricular
        if(componente != null && this.componentes.size() > 0){
            if(this.componentes.contains(componente)){       
            //Caso sim, entramos na condição e procuramos a posição onde está o componente curricular a qual se quer adiconar uma turma
            int i = this.componentes.indexOf(componente);
            this.componentes.get(i).addTurmaParaOComponente();
        } else {
            System.out.println("O professor não possui o componente curricular buscado");
             }
        } else if(componente != null){
            System.out.println("Componente está com valor NULL");
        } else  {
            System.out.println("O professor não possui componentes curriculares em sua grade");
        }
    }
    
    //Getters and setters
    public String getId() {
        return identity;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public LinkedList<ComponenteCurricular> getComponentes() {
        return componentes;
    }

    public void setComponentes(LinkedList<ComponenteCurricular> componentes) {
        this.componentes = componentes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Professor other = (Professor) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    @Override
    public String toString() {
        String str = "";
        // Utilizei um for para adicionar todos os componentes curriculares á uma string
        // e mostra-los sem que seja no formato de uma lista
        for (int i = 0; i < componentes.size(); i++) {
            str += "\n" + componentes.get(i).toString();
        }

        return "\nNome do professor: " + nome + "\nTitulo do professor" + this.titulo + "\nID do professor: " + this.identity + "\nCarga Horaria: " + cargaHoraria
                + "\nComponentes curriculares: " + str;
    }

    private static String geradorAleatorioIdProfessor() {
        Random random = new Random();
        int n = random.nextInt(90000);
        String str = Integer.toString(n);
        return str;
    }

}
