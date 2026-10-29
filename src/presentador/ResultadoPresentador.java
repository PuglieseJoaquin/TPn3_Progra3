package presentador;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import estructuraDatosAux.Par;
import estructuraDatosAux.VerticeVisual;
import modelo.BackTracking;
import modelo.Instancia;
import modelo.Solucion;
import modelo.SolverGoloso;
import modelo.Vertice;

import vista.interfaz.InterfazGestorPantalla;
import vista.interfaz.ResultadoVista;

public class ResultadoPresentador {

	private ResultadoVista _rVista;
	private InterfazGestorPantalla gestorInterfaz;
	private Instancia instancia;
	private List<Solucion> soluciones;
	private static final List<String> NOMBRES_ESTRATEGIAS = List.of(
			"Óptima (Backtracking)",
			"Mayor Cobertura",
			"Menor Cobertura",
			"Mayor Grado",
			"Menor Grado",
			"Nombre A-Z",
			"Nombre Z-A",
			"Peso por vecino");

	public ResultadoPresentador(ResultadoVista resultadoVista, InterfazGestorPantalla gestorInterfaz, Instancia instancia) {
		this._rVista = resultadoVista;
		this.gestorInterfaz = gestorInterfaz;
		this.instancia = instancia;
	}

	public void calcularSolucionDominante() {

		SolverGoloso solverGoloso = new SolverGoloso(instancia);
		
		soluciones = new ArrayList<>();
		
		soluciones.add(resolverBacktracking());
		soluciones.addAll(solverGoloso.resolver());

		completarComboSoluciones();
		construirStringDeRespuestas();
	}
	
	private Solucion resolverBacktracking() {
		BackTracking bt = new BackTracking();
		
		Set<Vertice> verticesOptimos = bt.resolverGrafo(instancia.getGrafo());
		
		Solucion solBackTracking = new Solucion(instancia);
		
		for (Vertice v : verticesOptimos) {
			solBackTracking.agregar(v);
		}
		return solBackTracking;
	}
	
	private void completarComboSoluciones() {
		_rVista.completarComboSoluciones(NOMBRES_ESTRATEGIAS);
	}

	private void construirStringDeRespuestas() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < soluciones.size(); i++) {
			
			Solucion sol = soluciones.get(i);
			
			sb.append(NOMBRES_ESTRATEGIAS.get(i))
			.append("\n");

			sb.append("Total de vértices en la solución: ")
			.append(sol.cardinal())
			.append("\n");

		    sb.append("Vértices seleccionados:\n{");

		    for (Vertice v : sol.getVertices()) {
		    		sb.append(v.getNombre()).append(", ");
		    }

		    if (!sol.getVertices().isEmpty()) {
		    		sb.delete(sb.length() - 2, sb.length());
		    }

		    sb.append("}\n\n");
		}
		_rVista.mostrarResultado(sb.toString());;
	}

	public void mostrarSolucion(int indiceEstrategiaElegida) {
		
		Solucion sol = soluciones.get(indiceEstrategiaElegida);
				
		List<VerticeVisual> vertices = instancia.getVertices().stream()
										.map(v -> new VerticeVisual(
												v.getNombre(),
												sol.getVertices().contains(v)
												)).toList();

		List<Par<String, String>> aristas = instancia.getAristasComoNombres();

		_rVista.mostrarGrafo(vertices, aristas);	
	}
	
	public void manejarClickModificarDatos() {
		gestorInterfaz.mostrarPantallaCargaManualConInstancia(instancia);
	}

	public void manejarClickVolverAlMenu() {
		gestorInterfaz.crearPantallaInicio();
	}

	public void manejarClickBtnSalir() {
		System.exit(0);
	}
}