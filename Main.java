public class Main {
    public static void main(String[] args) {
        Professor p = new Professor("Marcos");
        
       p.adicionaComponenteCurricular(60, "Teste de software", "34234");
       System.out.println(p.toString());
       p.adicionarTurmaAoComponenteDoProfessor("Teste de software", "34234");
    }
}
