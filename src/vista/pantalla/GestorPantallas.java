package vista.pantalla;

import javax.swing.JFrame;

import modelo.Arista;
import modelo.Instancia;
import modelo.Vertice;
import presentador.CargarDatosPresentador;
import vista.interfaz.InterfazGestorPantalla;

public class GestorPantallas implements InterfazGestorPantalla {

	private PantallaInicio pantallaInicio;
	private PantallaCargarDatosManual pantallaCargaDatos;
	private PantallaCargarDatosDesdeJSON pantallaCargaDesdeJSON;
	private PantallaResultado pantallaResultado;
	private PantallaCargarTestStress pantallaCargarTestStress;
	
	public void crearPantallaInicio() {
		pantallaInicio = new PantallaInicio(this);
		mostrarPantalla(pantallaInicio);
	}

	public void crearPantallaCargaDatos() {
		pantallaCargaDatos = new PantallaCargarDatosManual(this);
		mostrarPantalla(pantallaCargaDatos);
	}
	
	public void crearPantallaCargaDesdeJSON() {
	    pantallaCargaDesdeJSON = new PantallaCargarDatosDesdeJSON(this);
	    mostrarPantalla(pantallaCargaDesdeJSON);
	}
	
	public void crearPantallaTestStress() {
		pantallaCargarTestStress = new PantallaCargarTestStress(this);
		mostrarPantalla(pantallaCargarTestStress);	
	}
		
	public PantallaResultado crearPantallaResultado() {
		pantallaResultado = new PantallaResultado(this);
	    mostrarPantalla(pantallaResultado);
	    return pantallaResultado;
	}	
	
	public void mostrarPantallaCargaManual() {
		mostrarPantalla(pantallaCargaDatos);
	}
	
	public void mostrarPantallaCargaDesdeJSON() {
	    mostrarPantalla(pantallaCargaDesdeJSON);
	}

	@Override
	public void mostrarPantallaCargaManualConInstancia(Instancia instanciaOriginal) {
	    inicializarPantallaCarga();

	    if (instanciaOriginal != null) {
	        CargarDatosPresentador presentador = pantallaCargaDatos.getPresentador();
	        cargarDatosEnPresentador(presentador, instanciaOriginal);
	    }
	}

	private void inicializarPantallaCarga() {
	    pantallaCargaDatos = new PantallaCargarDatosManual(this);
	    mostrarPantalla(pantallaCargaDatos);
	}

	private void cargarDatosEnPresentador(CargarDatosPresentador presentador, Instancia instancia) {
	    cargarVertices(presentador, instancia);
	    cargarAristas(presentador, instancia);
	}

	private void cargarVertices(CargarDatosPresentador presentador, Instancia instancia) {
	    for (Vertice v : instancia.getVertices()) {
	        presentador.manejarClickAgregarVertice(v.getNombre());
	    }
	}

	private void cargarAristas(CargarDatosPresentador presentador, Instancia instancia) {
	    for (Arista a : instancia.getGrafo().obtenerTodasLasAristas()) {
	        presentador.manejarClickAgregarArista(
	            a.getOrigen().getNombre(), 
	            a.getDestino().getNombre()
	        );
	    }
	}
	
	private JFrame mostrarPantalla(JFrame pantalla) {
		pantalla.setVisible(true);
		ocultarPantallas(pantalla);
		return pantalla;
	}
		
	private void ocultarPantallas(JFrame pantallaActual) {
		JFrame[] todasLasPantallas = { pantallaInicio, pantallaCargaDatos, pantallaResultado, pantallaCargaDesdeJSON, pantallaCargarTestStress };

		for (JFrame pantalla : todasLasPantallas) {
			if (pantalla != pantallaActual && pantalla != null) {
				pantalla.setVisible(false);
			}
		}
	}
}