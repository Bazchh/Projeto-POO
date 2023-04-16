import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;

public class Professor {

    private int registro; // Variavel usada para identificar o professor
    private String nome; // Variavel que armazena o nome do professor
    private int cargaHoraria; // Variavel utilizada para somar a carga horaria total do professor e verificar
                              // se é válida com base no critério estabelecido
    private ArrayList<ComponenteCurricular> componentes = new ArrayList<>(); // ArrayList utilizada para guardar os
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
        if (componentes.contains(componente)) {
            // Se a carga horaria do professor ainda for menor que 300 horas, então ainda é
            // possivel adicionar
            // horas a carga horaria do professor, logo executamos o comando dentro do if
            if (this.cargaHoraria < 300) {
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
    public void removerComponenteCurricular(ComponenteCurricular componente) {

        // Caso o professor possua carga horaria maior que 0 significa que ele possui
        // componentes curriculares em sua grade, logo, podemos verificar se ele possui
        // o componente que buscamos remover
        if (this.cargaHoraria > 0) {
            if (this.componentes.contains(componente)) { // verificando se o professor possui o componente curricular
                                                         // que deseja remover
                this.componentes.remove(componente);
                this.cargaHoraria -= componente.getCargaHoraria();
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
    public String toString() {
        return "\nID do professor: " + registro + "\nNome: " + nome + "\nCarga Horaria: " + cargaHoraria
                + "\nComponentes curriculares: " + componentes;
    }

}