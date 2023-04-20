import java.util.LinkedList;
import java.util.Random;

public class ComponenteCurricular {
    private int nDeTurmasDoComponente = 0;
    private int cargaHoraria;
    private String nome;
    // Id para criar um componente curricular
    private String ID = geradorAleatorioIdComponente();
    // Id para buscar, comparar ou remover um componente curricular
    private String idBusca;
    private LinkedList<Turma> turmaDaDisciplina = new LinkedList<>();

    public ComponenteCurricular(int cargaHoraria, String nome) {
        this.nome = nome;
        // Condições para definição da carga horaria de um componente curricular
        if (cargaHoraria < 40) {
            this.cargaHoraria = 30;
        } else if (cargaHoraria >= 40) {
            this.cargaHoraria = 60;
        } else {
            this.cargaHoraria = cargaHoraria;
        }
    }



    // Construtor usado somente para pesquisar e comparar o objeto instanciado a
    // qual se quer remover ou adicionar
    public ComponenteCurricular(String nome, String idBusca) {
        this.nome = nome;
        this.idBusca = idBusca;
    }

    // Metodo que será posteriormente utilizado em algumas operações
    public int getCargaHoraria() {
        return this.cargaHoraria;
    }

    public void addTurmaParaOComponente() {
        this.nDeTurmasDoComponente++;
        Turma novaTurma = new Turma(this.nome + " T nº" + this.nDeTurmasDoComponente);
        turmaDaDisciplina.add(novaTurma);
    }

    public String getId(){
        return this.ID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (ID == null) {
            if (other.ID != null)
                return false;
        } else if (!ID.equals(other.ID))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Carga horaria: " + cargaHoraria + " nome do componente: " + nome + " ID: " + ID;
    }

    final private static String geradorAleatorioIdComponente() {
        Random random = new Random();
        int n = random.nextInt(600000);
        String str = Integer.toString(n);
        return str;
    }

}
