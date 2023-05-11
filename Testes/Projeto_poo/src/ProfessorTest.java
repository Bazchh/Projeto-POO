package src;



import static org.junit.jupiter.api.Assertions.*;

import java.util.InputMismatchException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceções.DadosDoProfessorInvalidoException;
import Exceções.NomeDoComponenteInvalido;

class ProfessorTest {

	Professor p, p1;
	
	@BeforeEach
	void setUp() throws InputMismatchException, DadosDoProfessorInvalidoException {
		p = new Professor("Testador", "Tester",1);
		assertThrows(DadosDoProfessorInvalidoException.class, () ->{
			p1 = new Professor("","",2);
		});
		assertThrows(InputMismatchException.class, () ->{
			p1 = new Professor('a');
		});
		
		p1 = new Professor(1);
		
	}

	@Test
	void adicionaComponenteCurricularTest() {
		assertEquals(0, p.getComponentesSize());
		p.adicionaComponenteCurricular(60, "Teste de Software", 1271);
		p.adicionaComponenteCurricular(60, "Teste de Software", 271);
		p.adicionaComponenteCurricular(60, "Teste", 6);
		p.adicionaComponenteCurricular(60, "Teste", 27);
		p.adicionaComponenteCurricular(60, "Teste", 12);
		p.adicionaComponenteCurricular(60, "Teste", 276);
		p.adicionaComponenteCurricular(60, "Teste", 126);
		p.adicionaComponenteCurricular(35, "Teste", 176);
		p.adicionaComponenteCurricular(45, "Teste", 12);
		p.adicionaComponenteCurricular(30, "", 12);
		
		
		assertTrue(p.getComponentesSize() >= 0);	
	}
	
	@Test
	void removerComponenteCurricular() {
		assertEquals(0,p.getComponentesSize());
		p.removerComponenteCurricular("Teste de Software", 1271);
		p.adicionaComponenteCurricular(60, "Teste de Software", 1271);
		assertEquals(1, p.getComponentesSize());
		p.removerComponenteCurricular("Teste de Software", 1271);
		assertEquals(0,p.getComponentesSize());
		p.removerComponenteCurricular("", 0);
		p.adicionaComponenteCurricular(60, "Teste de Software", 1271);
		p.removerComponenteCurricular("Teste de Software", 127);
		p.setCargaHoraria(0);
		p.removerComponenteCurricular("Teste de Softw", 1);
		p.adicionaComponenteCurricular(60, "Teste de Software", 1271);
		p.adicionaComponenteCurricular(60, "Teste", 11);
		p.removerComponenteCurricular("Teste", 11);
		assertTrue(p.getComponentesSize() >= 0);
	}
	
	@AfterEach
	void tearDown(){
	}

	

}
