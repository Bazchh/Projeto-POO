
public class ComponenteCurricular {
    private int cargaHoraria;
    private String nome;
    private int ID;

    public ComponenteCurricular(int cargaHoraria, String nome, int iD) {
        this.cargaHoraria = cargaHoraria;
        this.nome = nome;
        ID = iD;
    }

    public int getCargaHoraria() {
        return this.cargaHoraria;
    }

    @Override
    public String toString() {
        return "Carga horaria: " + cargaHoraria + " nome do componente: " + nome + " ID: " + ID;
    }

}