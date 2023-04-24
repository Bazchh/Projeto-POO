import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class ComponenteCurricularTest {

	ComponenteCurricular C;
	
	@BeforeEach
	void setUp() {
		C = new ComponenteCurricular(60,"Teste de Componente","Testando");
	}
	
	@Test
	void testaAddTurmaParaOComponente() {
		C.addTurmaParaOComponente();
		assertEquals(1,C.getTurmaDaDisciplinaSize());
		C = new ComponenteCurricular(30,"Teste de Componente","Testando");
		C.addTurmaParaOComponente();
		assertEquals(1,C.getTurmaDaDisciplinaSize());
		C = new ComponenteCurricular(40,"Teste de Componente","Testando");
		C.addTurmaParaOComponente();
		assertEquals(1,C.getTurmaDaDisciplinaSize());
	}
	
	@Test 
	void testaGetCargaHoraria() {
		C.getCargaHoraria();
		assertEquals(60,C.getCargaHoraria());
	}
	
	@Test
	void testaGetNome() {
		C.getNome();
		assertEquals("Teste de Componente",C.getNome());
	}
	@Test
	void testaGetID() {
		C.getID();
		assertEquals("Testando", C.getID());
	}
	@Test
	void testaGetTurmaDaDisciplina() {
		C.getTurmaDaDisciplina();
		assertEquals(0,C.getTurmaDaDisciplinaSize());
		C.addTurmaParaOComponente();
		assertEquals(1,C.getTurmaDaDisciplinaSize());	
	}
	
	void TestaSetNome() {
		C = new ComponenteCurricular(60,"Teste de Componente","Testando");
		C.setNome("Teste de compo");
		assertEquals("Teste de compo",C.getNome());
	}
	
	@AfterEach
	void tearDown(){
	}
}
