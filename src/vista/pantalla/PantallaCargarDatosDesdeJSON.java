package vista.pantalla;

import java.awt.*;
import javax.swing.*;
import presentador.CargarDatosDesdeJSONPresentador; 
import vista.interfaz.CargarDatosDesdeJSONVista;
import vista.interfaz.InterfazGestorPantalla;

@SuppressWarnings("serial")
public class PantallaCargarDatosDesdeJSON extends JFrame implements CargarDatosDesdeJSONVista {

	private CargarDatosDesdeJSONPresentador presentador;
	private JPanel panelFondo;
	private JLabel lblTitulo, lblSubtitulo;
	private JComboBox<String> comboJson;
	private JButton btnProcesar, btnVolver;

	public PantallaCargarDatosDesdeJSON(InterfazGestorPantalla gestorInterfaz) {
		this.presentador = new CargarDatosDesdeJSONPresentador(this, gestorInterfaz);
		
		configurarPantalla();
		crearElementos();
	}

	private void configurarPantalla() {
		setTitle("Carga de Grafo mediante JSON — MVP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		panelFondo = new JPanel();
		panelFondo.setBackground(new Color(30, 41, 59));
		setContentPane(panelFondo);
		panelFondo.setLayout(null);
	}

	private void crearElementos() {
		lblTitulo = new JLabel("CARGA DE GRAFO DESDE ARCHIVO JSON", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTitulo.setForeground(new Color(248, 250, 252));
		lblTitulo.setBounds(100, 80, 700, 40);
		panelFondo.add(lblTitulo);

		lblSubtitulo = new JLabel("Seleccione una de las opciones prearmadas para ejecutar el grafo:", SwingConstants.CENTER);
		lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblSubtitulo.setForeground(new Color(222, 233, 221));
		lblSubtitulo.setBounds(100, 150, 700, 30);
		panelFondo.add(lblSubtitulo);

		String[] opcionesJson = {
			"Grafo 1: Red de Numeros (Pequeño - 6 vertices)",
			"Grafo 2: Red de Redes (Mediano - 8 vertices)",
			"Grafo 3: Stack Tecnológico (Mediano - 9 vertices)",
			"Grafo 4: Red Social de Personas (Complejo - 10 vertices)"
		};
		
		comboJson = new JComboBox<>(opcionesJson);
		comboJson.setFont(new Font("Segoe UI", Font.BOLD, 14));
		comboJson.setBackground(Color.WHITE);
		comboJson.setForeground(new Color(15, 23, 42));
		comboJson.setCursor(new Cursor(Cursor.HAND_CURSOR));
		comboJson.setBounds(150, 210, 600, 45);
		panelFondo.add(comboJson);

		btnProcesar = new JButton("Procesar y Calcular Dominante");
		btnProcesar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnProcesar.setForeground(Color.WHITE);
		btnProcesar.setBackground(new Color(16, 185, 129)); 
		btnProcesar.setFocusPainted(false);
		btnProcesar.setBorderPainted(false);
		btnProcesar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnProcesar.setBounds(250, 300, 400, 45);

		btnProcesar.addActionListener(e -> {
			int indiceSeleccionado = comboJson.getSelectedIndex();
			presentador.manejarClickCargarJson(indiceSeleccionado);
		});
		panelFondo.add(btnProcesar);

		btnVolver = new JButton("Volver al Menú");
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBackground(new Color(239, 68, 68)); 
		btnVolver.setFocusPainted(false);
		btnVolver.setBorderPainted(false);
		btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVolver.setBounds(250, 365, 400, 45);

		btnVolver.addActionListener(e -> {
			presentador.manejarClickVolverAlMenu();
		});
		panelFondo.add(btnVolver);
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error de Carga", JOptionPane.ERROR_MESSAGE);
	}
}