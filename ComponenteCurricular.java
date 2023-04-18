
public class ComponenteCurricular {
    private int cargaHoraria;
    private String nome;
    private int ID;

    public ComponenteCurricular(int cargaHoraria, String nome, int ID) {
            this.nome = nome;
            this.ID = ID;
            if(cargaHoraria < 40){
                this.cargaHoraria = 30;
            }  else if (cargaHoraria >= 40){
                this.cargaHoraria = 60;
            } else {
                this.cargaHoraria = cargaHoraria;
            }
    }
    //Construtor usado somente para pesquisar e comparar o objeto instanciado a qual se quer remover ou adicionar
    public ComponenteCurricular(String nome, int ID){
        this.nome = nome;
        this.ID = ID;
    }

    //Metodo que será posteriormente utilizado em algumas operações
    public int getCargaHoraria() {
        return this.cargaHoraria;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + cargaHoraria;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ID;
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
        
        //Realizei uma pequena alteração no metodo equals para que não precisemos comparar a carga horaria de cada componente
        /*if (cargaHoraria != other.cargaHoraria)
            return false;*/
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (ID != other.ID)
            return false;
        return true;
    }

    
    @Override
    public String toString() {
        return "Carga horaria: " + cargaHoraria + " nome do componente: " + nome + " ID: " + ID;
    }

}