import java.util.LinkedList;

public class ComponenteCurricular {
    private int cargaHoraria;
    private String nome;
    // Id para criar um componente curricular
    private String ID;
    // Id para buscar, comparar ou remover um componente curricular
    private String idBusca;
    private LinkedList<Turma> turmaDaDisciplina = new LinkedList<>();

    public ComponenteCurricular(int cargaHoraria, String nome, String id) {
        this.nome = nome;
        this.ID = id;
        // Condições para definição da carga horaria de um componente curricular
        if (cargaHoraria < 40) {
            this.cargaHoraria = 30;
        } else if (cargaHoraria >= 40) {
            this.cargaHoraria = 60;
        } else {
            this.cargaHoraria = cargaHoraria;
        }
    }

    /* adicionar no toString atributos faltosos */


    // Construtor usado somente para pesquisar e comparar o objeto instanciado a
    // qual se quer remover ou adicionar
    public ComponenteCurricular(String nome, String idBusca) {
        this.nome = nome;
        this.ID = idBusca;
    }

    public void addTurmaParaOComponente() {
        Turma novaTurma = new Turma(this.nome + " T nº" + (this.turmaDaDisciplina.size()+1));
        System.out.println("oi");
        this.turmaDaDisciplina.add(novaTurma);
        System.out.println(turmaDaDisciplina);

    }

    //Getters and setters

    public int getTurmaDaDisciplinaSize() {
        return this.turmaDaDisciplina.size();
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public LinkedList<Turma> getTurmaDaDisciplina() {
        return turmaDaDisciplina;
    }

    public void setTurmaDaDisciplina(LinkedList<Turma> turmaDaDisciplina) {
        this.turmaDaDisciplina = turmaDaDisciplina;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ID == null) ? 0 : ID.hashCode());
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
        ComponenteCurricular other = (ComponenteCurricular) obj;
        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Carga horaria: " + cargaHoraria + " nome do componente: " + nome + " ID: " + ID + " Turmas: " + (getTurmaDaDisciplinaSize()+1);
    }

}
