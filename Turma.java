import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

public class Turma {
    // Utilizando classe calendario para obter o ano atual do sistema e adiciona-lo
    // ao id da turma
    private Calendar cal = GregorianCalendar.getInstance();
    // Adicionando o ano atual do calendario + 6 numeros gerados aleatoriamente
    private final String iD = "" + cal.get(Calendar.YEAR) + geradorAleatorio();;
    private String nomeDaTurma;

    public Turma(String nomeDaTurma) {
        this.nomeDaTurma = nomeDaTurma;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((iD == null) ? 0 : iD.hashCode());
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
        Turma other = (Turma) obj;
        if (iD == null) {
            if (other.iD != null)
                return false;
        } else if (!iD.equals(other.iD))
            return false;
        return true;
    }

    public String getiD() {
        return iD;
    }

    public String getNomeDaTurma() {
        return nomeDaTurma;
    }

    public void setNomeDaTurma(String nomeDaTurma) {
        this.nomeDaTurma = nomeDaTurma;
    }

    final private static String geradorAleatorio() {
        Random random = new Random();
        int n = random.nextInt(600000);
        String str = Integer.toString(n);
        return str;
    }

}
