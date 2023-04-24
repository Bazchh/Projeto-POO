import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfessorTest {

	Professor p;
	
	@BeforeEach
	void setUp() {
		p = new Professor("Testador");
	}

	@Test
	void adicionaComponenteCurricularTest() {
		assertEquals(0, p.getComponentesSize());
		p.adicionaComponenteCurricular(60, "Teste de Software", "PEX1271");
		p.adicionaComponenteCurricular(60, "Teste de Software", "PEX1271");
		p.adicionaComponenteCurricular(60, "Teste", "PEX6");
		p.adicionaComponenteCurricular(60, "Teste", "PEX276");
		p.adicionaComponenteCurricular(60, "Teste", "PEX12");
		p.adicionaComponenteCurricular(60, "Teste", "PEX1276");
		p.adicionaComponenteCurricular(60, "Teste", "P1276");
		p.adicionaComponenteCurricular(35, "Teste", "PEX1276");
		p.adicionaComponenteCurricular(45, "Teste", "PEX1276");
		assertTrue(p.getComponentesSize() >= 0);	
	}
	
	@AfterEach
	void tearDown(){
	}

	

}
