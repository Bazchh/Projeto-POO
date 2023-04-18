
import java.util.LinkedList;

public class Professor {

    private int registro; // Variavel usada para identificar o professor
    private String nome; // Variavel que armazena o nome do professor
    private int cargaHoraria; // Variavel utilizada para somar a carga horaria total do professor e verificar
                              // se é válida com base no critério estabelecido
    private LinkedList<ComponenteCurricular> componentes = new LinkedList<>(); // ArrayList utilizada para guardar os
                                                                               // componentes curriculares do professor

    // Construtor
    public Professor(int registro, String nome) {
        this.registro = registro;
        this.nome = nome;
    }

    // Metodo que adiciona um componente curricular na lista de componentes do
    // professor
    public void adicionaComponenteCurricular(int cargaHoraria, String nome, int iD) {
        ComponenteCurricular componente = new ComponenteCurricular(cargaHoraria, nome, iD);

        if (!this.componentes.contains(componente)) {

            // Se a carga horaria do professor ainda for menor que 300 horas, então ainda é
            // possivel adicionar
            // horas a carga horaria do professor, logo executamos o comando dentro do if
            if (this.cargaHoraria < 300 && this.cargaHoraria + componente.getCargaHoraria() <= 300) {
                this.componentes.add(componente);
                // Quando adicionamos um componente, verificamos também sua carga horaria e
                // somamos a carga horaria total do professor
                this.cargaHoraria += componente.getCargaHoraria();
                System.out.println("Componente curricular adicionado com sucesso");
            } else {
                // Caso a condição anterior não seja satisfeita informamos que o professor já
                // atingiu a carga horaria limite
                System.out.println("O professor já atingiu o limite de horas por componentes curriculares");
            }
        } else {
            System.out.println("O professor já possui o componente curricular informado");
        }

    }

    // Metodo para remover o componente curricular da grade de um professor
    public void removerComponenteCurricular(String nome, int iD) {
        ComponenteCurricular componente = new ComponenteCurricular(nome, iD);
        // Caso o professor possua carga horaria maior que 0 significa que ele possui
        // componentes curriculares em sua grade, logo, podemos verificar se ele possui
        // o componente que buscamos remover
        if (this.componentes.isEmpty()) {
            System.out.println("A lista de componentes curriculares do professor está vazia");
        } else if (this.cargaHoraria > 0) {
            if (this.componentes.contains(componente)) { // verificando se o professor possui o componente curricular
                                                         // que deseja remover
                //Buscando a carga horaria do componente a ser removido para diminuir a carga horaria na grade do professor
                for (ComponenteCurricular componenteCurricular : componentes) {
                    if (componenteCurricular.equals(componente)) {
                        this.cargaHoraria -= componenteCurricular.getCargaHoraria();
                    }
                }
                //Removendo componente curricular
                this.componentes.remove(componente);
                System.out.println("Componente curricular removido com sucesso");
            } else {
                System.out.println("Este professor não possui o componente curricular informado");
            }

        } else {
            System.out.println(
                    "Não é possivel remover o componente curricular pois o professor não possui nenhum em sua grade");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + registro;
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
        if (registro != other.registro)
            return false;
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
        //Utilizei um for para adicionar todos os componentes curriculares á uma string e mostra-los sem que seja no formato de uma lista
        for(int i = 0; i < componentes.size(); i++){
            str += "\n" + componentes.get(i).toString();
        }   

        return "\nID do professor: " + registro + "\nNome: " + nome + "\nCarga Horaria: " + cargaHoraria
                + "\nComponentes curriculares: " + str;
    }

}