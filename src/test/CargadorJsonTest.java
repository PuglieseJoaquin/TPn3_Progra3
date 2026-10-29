package test;

import static org.junit.Assert.*;
import org.junit.Test;
import datos.CargadorJson;
import modelo.Instancia;

public class CargadorJsonTest {
	
    private Instancia cargarGrafoNumeros() {
        return CargadorJson.cargarInstanciaDesdeJson("/JSONs/grafoNumeros.json");
    }

    @Test(expected = RuntimeException.class)
    public void cargarRutaInexistenteLanzaExcepcionTest() {
    	CargadorJson.cargarInstanciaDesdeJson("/JSONs/grafoQueNoExiste.json");
    }
    
    @Test
    public void cargarInstanciaDesdeJsonNoEsNuloTest() {
        Instancia instancia = cargarGrafoNumeros();
        assertNotNull("La instancia no debería ser nula", instancia);
    }

    @Test
    public void cargarInstanciaDesdeJsonCantidadVerticesCorrectaTest() {
        Instancia instancia = cargarGrafoNumeros();
        assertEquals(6, instancia.cantidadVertices());
    }
    
    @Test
    public void cargarInstanciaDesdeJsonCargaAristasCorrectamenteTest() {
        Instancia instancia = cargarGrafoNumeros();
        assertFalse("El grafo cargado debería tener aristas", instancia.getAristasComoNombres().isEmpty());
    }
    
}