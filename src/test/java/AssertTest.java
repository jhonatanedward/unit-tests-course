

import org.junit.Assert;
import org.junit.Test;

import br.edward.entidades.Usuario;

public class AssertTest {
	@Test
	public void Teste() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals("Erro de comparação", 10, 10);
		Assert.assertEquals(0.5123, 0.51245, 0.001);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		Assert.assertNotEquals("bola", "case");
		Assert.assertEquals("bola", "bola");
		
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Teste");
		Usuario u2 = new Usuario("Teste");
		Usuario u3 = u2;
		
		Assert.assertEquals(u1, u2);
		
		// Comparar instancias
		Assert.assertSame(u3, u2);
		Assert.assertNotSame(u1, u2);
		
		Assert.assertTrue(u3 == null);
		// ou
		
		Assert.assertNull(u3);
		Assert.assertNotNull(u3);
	
		
	}
}
