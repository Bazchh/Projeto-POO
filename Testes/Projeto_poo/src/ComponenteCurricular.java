package src;

import java.util.LinkedList;

import Exceções.NomeDoComponenteInvalido;
import Exceções.ValoresInvalidosPCargaHoraria;

public class ComponenteCurricular {
    private int cargaHoraria;
    private String nome;
    // Id para criar um componente curricular
    private int idProfessor;
    private int ID;
    private int idKey;
    // Id para buscar, comparar ou remover um componente curricular
    private LinkedList<Turma> turmaDaDisciplina = new LinkedList<>();

    public ComponenteCurricular(int cargaHoraria, String nome, int id) throws ValoresInvalidosPCargaHoraria, NomeDoComponenteInvalido {
        
        // Condições para definição da carga horaria de um componente curricular
        if (cargaHoraria != 30 && cargaHoraria != 60) {
           throw new ValoresInvalidosPCargaHoraria("Carga Horaria deve ser de 30 ou 60 horas para uma disciplina");
        } 

        if(nome.isEmpty()){
            throw new NomeDoComponenteInvalido("Nome do componente não deve estar vazio");
        }

        this.nome = nome;
        this.ID = id;
        this.cargaHoraria = cargaHoraria;
    }


    // Construtor usado somente para pesquisar e comparar o objeto instanciado a
    // qual se quer remover ou adicionar
    public ComponenteCurricular(String nome, int idBusca) throws NomeDoComponenteInvalido{
        
        if(nome.isEmpty()){
            throw new NomeDoComponenteInvalido("Nome do componente não deve estar vazio");
        }
        
        this.nome = nome;
        this.ID = idBusca;
    }

    public void addTurmaParaOComponente(int semestre) {
        Turma novaTurma = new Turma(this.nome + " T nº" + (this.turmaDaDisciplina.size()+1), semestre);
        this.turmaDaDisciplina.add(novaTurma);

    }

    //Getters and setters

    public int getTurmaDaDisciplinaSize() {
        return this.turmaDaDisciplina.size();
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public int getIdProf(){
        return idProfessor;
    }

    public int getIdKey(){
        return idKey;
    }

    public void setIdKey(int idKey){
        this.idKey = idKey;
    }

    public void setIdProf(int idProfessor){
        this.idProfessor = idProfessor;
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

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
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
        if (ID != other.ID)
            return false;
        return true;
    }


    @Override
    public String toString() {
        return  "Nome do componente: " + nome +  " ID: " + ID + " ID do banco de dados: " + idKey +  " Carga horaria: " + cargaHoraria;
    }

}
