package presentador;

import datos.CargadorJson;
import modelo.Instancia;
import vista.interfaz.CargarDatosDesdeJSONVista;
import vista.interfaz.InterfazGestorPantalla;
import vista.pantalla.PantallaResultado;

public class CargarDatosDesdeJSONPresentador {

	private CargarDatosDesdeJSONVista vista;
	private InterfazGestorPantalla gestorInterfaz;

	public CargarDatosDesdeJSONPresentador(CargarDatosDesdeJSONVista vista, InterfazGestorPantalla gestorInterfaz) {
		this.vista = vista;
		this.gestorInterfaz = gestorInterfaz;
	}

	public void manejarClickCargarJson(int indiceSeleccionado) {
		try {
			String rutaJson = obtenerRutaPorIndice(indiceSeleccionado);
			
			Instancia instancia = CargadorJson.cargarInstanciaDesdeJson(rutaJson);

			if (instancia == null || instancia.getVertices().isEmpty()) {
				vista.mostrarMensajeError("El grafo cargado desde el archivo JSON está vacío.");
				return;
			}

			PantallaResultado pantallaResultado = gestorInterfaz.crearPantallaResultado();
			
			ResultadoPresentador resultadoPresentador = new ResultadoPresentador(
					pantallaResultado, gestorInterfaz, instancia
			);
			
			pantallaResultado.setPresentador(resultadoPresentador);
			resultadoPresentador.calcularSolucionDominante();

		} catch (Exception e) {
			vista.mostrarMensajeError("Error al procesar el archivo JSON: " + e.getMessage());
		}
	}

	private String obtenerRutaPorIndice(int indice) {
		switch (indice) {
			case 0: return "/JSONs/grafoNumeros.json"; 
			case 1: return "/JSONs/grafoRedes.json";
			case 2: return "/JSONs/grafoTecnologias.json"; 
			case 3: return "/JSONs/grafoPersonas.json"; 
			
			default: return "/JSONs/grafoPersonas.json";
		}
	}

	public void manejarClickVolverAlMenu() {
		gestorInterfaz.crearPantallaInicio();
	}

	public void manejarClickBtnSalir() {
		System.exit(0);
	}
}