package presentador;

import modelo.*;

import vista.interfaz.CargarDatosVista;
import vista.interfaz.InterfazGestorPantalla;
import vista.pantalla.PantallaResultado;

public class CargarDatosPresentador {
	private CargarDatosVista _cdVista;
	private InterfazGestorPantalla gestorInterfaz;
	private Instancia instancia;
    
	public CargarDatosPresentador(CargarDatosVista cargarDatosVista, InterfazGestorPantalla gestorInterfaz) {
		this._cdVista = cargarDatosVista;
		this.gestorInterfaz = gestorInterfaz;
		this.instancia = new Instancia(); 
		
	}

	public void manejarClickAgregarVertice(String nombre) {
		try {
			if (nombre == null || nombre.trim().isEmpty()) {
				_cdVista.mostrarMensajeError("El nombre del vértice no puede estar vacío.");
				return;
			}

			instancia.agregarVertice(nombre.trim());
			
			_cdVista.agregarVertice(nombre.trim());
			_cdVista.limpiarCampoVertice();
		} catch (Exception e) {
			_cdVista.mostrarMensajeError(e.getMessage());
		}
	}		
		
	public void manejarClickAgregarArista(String origen, String destino) {
		try {
			if (origen == null || destino == null || origen.equals(destino)) {
				_cdVista.mostrarMensajeError("Debe seleccionar origen y destino válidos (y distintos)");
				return;
			}

			instancia.agregarArista(origen, destino);
			
			_cdVista.agregarArista(origen, destino, "");
		} catch (Exception e) {
			_cdVista.mostrarMensajeError(e.getMessage());
		}
	}

	public void manejarClickEliminarArista(String origen, String destino) {
		instancia.eliminarArista(instancia.getVertice(origen), instancia.getVertice(destino));
		_cdVista.eliminarArista(origen, destino);
	}		
	
	public void manejarClickEliminarVertice(String nombre) {
		Vertice vertice = instancia.getVertice(nombre);
		 
		for (Arista arista : instancia.obtenerAristasDe(vertice)) {
			_cdVista.eliminarArista(arista.getOrigen().getNombre(), arista.getDestino().getNombre());
		}

		instancia.eliminarVertice(vertice);
		_cdVista.eliminarVertice(nombre);
	} 	
	
	public void manejarClickCalcularDominante() {
		try {
	        if (instancia.getVertices().isEmpty()) {
	            _cdVista.mostrarMensajeError("Debe agregar al menos un vértice para calcular el conjunto dominante");
	            return;
	        }

	        PantallaResultado pantallaResultado = gestorInterfaz.crearPantallaResultado();
	        
	        ResultadoPresentador resultadoPresentador = new ResultadoPresentador(
	                pantallaResultado, gestorInterfaz, instancia
	        		);
	        
	        pantallaResultado.setPresentador(resultadoPresentador);
	        resultadoPresentador.calcularSolucionDominante();

	    } catch (Exception e) {
	        _cdVista.mostrarMensajeError(e.getMessage());
	    }
	}
		
	public void manejarClickVolverAlMenu() {
		gestorInterfaz.crearPantallaInicio();
	}	
	
	public void manejarClickBtnSalir() {
		System.exit(0);
	}
}