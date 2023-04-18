public class Main {
    public static void main(String[] args) {
        Professor p = new Professor(1231, "Marcos");
        
        p.adicionaComponenteCurricular(30, "Sistemas Operacionais", 22124);
        p.adicionaComponenteCurricular(30, "Sistemas Operacionais", 22124);
        p.adicionaComponenteCurricular(30, "Sistemas Operacionais", 22124);
        p.adicionaComponenteCurricular(30, "Sistemas Operacionais", 22124);
        p.adicionaComponenteCurricular(30, "Sistemas Operacionais", 22124);
        p.adicionaComponenteCurricular(20, "juca", 21);
        System.out.println(p.toString());
        p.adicionaComponenteCurricular(30, "Sistemas", 222);
        p.adicionaComponenteCurricular(30, "Sis", 22);
        System.out.println(p.toString());
        System.out.println(p.toString());
        p.adicionaComponenteCurricular(60, "Si", 21);
        p.adicionaComponenteCurricular(60, "S", 23);
        p.adicionaComponenteCurricular(30, "S", 25);
        System.out.println(p.toString());
        p.adicionaComponenteCurricular(30, "S2", 235);
        System.out.println(p.toString());
        p.adicionaComponenteCurricular(60, "S22", 2354);
    }
}
