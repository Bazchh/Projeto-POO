
public class Turma {
    // Utilizando classe calendario para obter o ano atual do sistema e adiciona-lo
    // ao id da turma

    // Adicionando o ano atual do calendario + 6 numeros gerados aleatoriamente
    private int idKey;
    private int id;
    private int idComp;
    private int idProf;
    private String nomeDaTurma;
    private int semestre;

    public Turma(String nomeDaTurma, int semestre) {
        this.nomeDaTurma = nomeDaTurma;
        this.semestre = semestre;
    }

    //Getters and setters
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    
    public int getIdKey() {
        return idKey;
    }

    public void setIdKey(int idKey) {
        this.idKey = idKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public String getNomeDaTurma() {
        return nomeDaTurma;
    }

    public void setNomeDaTurma(String nomeDaTurma) {
        this.nomeDaTurma = nomeDaTurma;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Turma other = (Turma) obj;
        if (id != other.id)
            return false;
        return true;
    }

    public String toString(){
        return "\nNome da turma: "+this.nomeDaTurma+"\nID da turma: "+this.id+"\nSemestre: "+this.semestre;
    }

}
