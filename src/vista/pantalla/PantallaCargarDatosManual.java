package vista.pantalla;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

import presentador.CargarDatosPresentador;
import vista.interfaz.InterfazGestorPantalla;
import vista.interfaz.CargarDatosVista;

@SuppressWarnings("serial")
public class PantallaCargarDatosManual extends JFrame implements CargarDatosVista {

	private CargarDatosPresentador presentador;
	
	private JPanel panelFondo;
	private JButton btnVolver, btnIrResultado, btnAgregarVertice, btnAgregarArista;
	private JLabel lblTitulo, lblNombreVertice, lblOrigen, lblDestino;
	private JTextField textNombreVertice;
	private JTable tablaVertices, tablaAristas;
	private JComboBox<String> comboOrigen, comboDestino;
	private DefaultTableModel modeloVertices, modeloAristas;

	public PantallaCargarDatosManual(InterfazGestorPantalla gestorInterfaz) {
		
		this.presentador = new CargarDatosPresentador(this, gestorInterfaz);
		
		configurarPantalla();
		crearTitulo();
		crearSeccionVertices();
		crearSeccionAristas();
		crearElementos();
	}

	private void configurarPantalla() {
		setTitle("Carga Manual (MVP)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(null);
		setResizable(false);

		panelFondo = new JPanel();
		panelFondo.setBackground(new Color(30, 41, 59));
		setContentPane(panelFondo);
		panelFondo.setLayout(null);
	}

	private void crearTitulo() {
		lblTitulo = new JLabel("CARGA MANUAL DE DATOS", SwingConstants.CENTER);
		estiloEtiqueta(lblTitulo);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitulo.setBounds(50, 20, 800, 35);
		panelFondo.add(lblTitulo);
	}

	private void crearSeccionVertices() {
		lblNombreVertice = new JLabel("NOMBRE DEL VÉRTICE:");
		estiloEtiqueta(lblNombreVertice);
		lblNombreVertice.setBounds(40, 80, 360, 22);
		panelFondo.add(lblNombreVertice);

		crearTextNombreVertice();
		crearBtnAgregarVertice();
		crearTablaVertices();	
	}
	
	private void crearTextNombreVertice() {
		textNombreVertice = new JTextField();
		textNombreVertice.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		textNombreVertice.setForeground(new Color(15, 23, 42));
		textNombreVertice.setBackground(new Color(241, 245, 249));
		textNombreVertice.setBounds(40, 108, 220, 34);
		panelFondo.add(textNombreVertice);
	}
	
	private void crearBtnAgregarVertice() {
		btnAgregarVertice = new JButton("Agregar vértice");
		estiloBotonPrimario(btnAgregarVertice);
		btnAgregarVertice.setBounds(270, 108, 130, 34);
		
		agregarListenerBtnAgregarVertice();
		panelFondo.add(btnAgregarVertice);
	}
	
	private void agregarListenerEliminarVertice() {
		tablaVertices.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tablaVertices.rowAtPoint(e.getPoint());
				int columna = tablaVertices.columnAtPoint(e.getPoint());
				if (fila >= 0 && columna == tablaVertices.getColumnCount() - 1) {
					String nombre = (String) modeloVertices.getValueAt(fila, 0);
					presentador.manejarClickEliminarVertice(nombre);
				}
			}
		});
	}

	private void crearTablaVertices() {
		modeloVertices = crearModeloNoEditable(new String[] { "Vértice", "" });
		tablaVertices = new JTable(modeloVertices);
		estiloTabla(tablaVertices);
		configurarColumnaEliminar(tablaVertices);
		agregarListenerEliminarVertice();
		panelFondo.add(crearScroll(tablaVertices, 40, 160, 360, 290));
	}

	private void agregarListenerBtnAgregarVertice() {
		btnAgregarVertice.addActionListener(e -> {
			presentador.manejarClickAgregarVertice(textNombreVertice.getText());
		});
	}

	private void crearSeccionAristas() {	
		crearSeccionOrigen();
		crearSeccionDestino();
		crearBtnAgregarArista();
		crearTablaAristas();
	}

	private void crearSeccionOrigen() {
		lblOrigen = new JLabel("ORIGEN:");
		estiloEtiqueta(lblOrigen);
		lblOrigen.setBounds(460, 80, 180, 22);
		panelFondo.add(lblOrigen);

		comboOrigen = new JComboBox<String>(new DefaultComboBoxModel<String>());
		estiloCombo(comboOrigen);
		comboOrigen.setBounds(460, 108, 180, 34);
		panelFondo.add(comboOrigen);
	}

	private void crearSeccionDestino() {
		lblDestino = new JLabel("DESTINO:");
		estiloEtiqueta(lblDestino);
		lblDestino.setBounds(660, 80, 180, 22);
		panelFondo.add(lblDestino);

		comboDestino = new JComboBox<String>(new DefaultComboBoxModel<String>());
		estiloCombo(comboDestino);
		comboDestino.setBounds(660, 108, 180, 34);
		panelFondo.add(comboDestino);
	}

	private void crearBtnAgregarArista() {
		btnAgregarArista = new JButton("Agregar arista");
		estiloBotonPrimario(btnAgregarArista);
		btnAgregarArista.setBounds(460, 160, 380, 34);
		
		agregarListenerAgregarArista();
		panelFondo.add(btnAgregarArista);
	}

	private void agregarListenerAgregarArista() {
		btnAgregarArista.addActionListener(e -> {

			String origen = (String) comboOrigen.getSelectedItem();
			String destino = (String) comboDestino.getSelectedItem();
			presentador.manejarClickAgregarArista(origen, destino);
		});
	}

	private void crearTablaAristas() {
		modeloAristas = crearModeloNoEditable(new String[] { "Origen", "Destino", "" });
		tablaAristas = new JTable(modeloAristas);
		estiloTabla(tablaAristas);
		configurarColumnaEliminar(tablaAristas);
		
		agregarListenerEliminarArista();
		
		panelFondo.add(crearScroll(tablaAristas, 460, 210, 380, 240));
	}

	private void agregarListenerEliminarArista() {
		tablaAristas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tablaAristas.rowAtPoint(e.getPoint());
				int columna = tablaAristas.columnAtPoint(e.getPoint());
				if (fila >= 0 && columna == tablaAristas.getColumnCount() - 1) {
					String origen = (String) modeloAristas.getValueAt(fila, 0);
					String destino = (String) modeloAristas.getValueAt(fila, 1);
					presentador.manejarClickEliminarArista(origen, destino);
				}
			}
		});
	}

	private void crearElementos() {
		crearBtnResultado();
		crearBtnVolverMenu();
	}

	private void crearBtnResultado() {
		btnIrResultado = new JButton("Ver Resultado");
		estiloBotonPrimario(btnIrResultado);
		btnIrResultado.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnIrResultado.setBackground(new Color(16, 185, 129));
		btnIrResultado.setBounds(660, 470, 180, 42);
		
		agregarListenerBtnResultado();
		panelFondo.add(btnIrResultado);
	}

	private void agregarListenerBtnResultado() {
		btnIrResultado.addActionListener(e -> {
			presentador.manejarClickCalcularDominante();
		});
	}

	private void crearBtnVolverMenu() {
		btnVolver = new JButton("Volver al Menú");
		estiloBotonPrimario(btnVolver);
		btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 16));
		btnVolver.setBackground(new Color(239, 68, 68));
		btnVolver.setBounds(470, 470, 170, 42);

		agregarListenerBtnVolverMenu();
		panelFondo.add(btnVolver);
	}
	
	public CargarDatosPresentador getPresentador() { 
	    return presentador;
	}

	private void agregarListenerBtnVolverMenu() {
		btnVolver.addActionListener(e -> {
			presentador.manejarClickVolverAlMenu();
		});
	}

	@Override
	public void agregarVertice(String nombre) {
		modeloVertices.addRow(new Object[] { nombre, "X" });
		comboOrigen.addItem(nombre);
		comboDestino.addItem(nombre);
	}

	@Override
	public void agregarArista(String origen, String destino, String peso) {
		modeloAristas.addRow(new Object[] { origen, destino, "X" });
	}

	@Override
	public void eliminarVertice(String nombre) {
		for (int i = 0; i < modeloVertices.getRowCount(); i++) {
			if (modeloVertices.getValueAt(i, 0).equals(nombre)) {
				modeloVertices.removeRow(i);
				break;
			}
		}
		comboOrigen.removeItem(nombre);
		comboDestino.removeItem(nombre);
	}

	@Override
	public void eliminarArista(String origen, String destino) {
		for (int i = 0; i < modeloAristas.getRowCount(); i++) {
			String orig = (String) modeloAristas.getValueAt(i, 0);
			String dest = (String) modeloAristas.getValueAt(i, 1);
			if ((orig.equals(origen) && dest.equals(destino)) || (orig.equals(destino) && dest.equals(origen))) {
				modeloAristas.removeRow(i);
				break;
			}
		}
	}

	@Override
	public void limpiarCampoVertice() {
		textNombreVertice.setText("");
	}

	@Override
	public void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private JScrollPane crearScroll(JTable tabla, int x, int y, int ancho, int alto) {
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.getViewport().setBackground(new Color(15, 23, 42));
		scroll.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85)));
		scroll.setBounds(x, y, ancho, alto);
		return scroll;
	}

	private DefaultTableModel crearModeloNoEditable(String[] columnas) {
		return new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
	}

	private void estiloTabla(JTable tabla) {
		tabla.setShowVerticalLines(false);
		tabla.setGridColor(new Color(51, 65, 85));
		tabla.setBackground(new Color(15, 23, 42));
		tabla.setForeground(new Color(241, 245, 249));
		tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tabla.setRowHeight(28);
		tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tabla.getTableHeader().setBackground(new Color(51, 65, 85));
		tabla.getTableHeader().setForeground(new Color(248, 250, 252));
	}

	private void configurarColumnaEliminar(JTable tabla) {
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		render.setBackground(new Color(15, 23, 42));
		render.setForeground(new Color(239, 68, 68));
		render.setFont(new Font("Segoe UI", Font.BOLD, 15));

		int ultima = tabla.getColumnCount() - 1;
		tabla.getColumnModel().getColumn(ultima).setCellRenderer(render);
		tabla.getColumnModel().getColumn(ultima).setMinWidth(45);
		tabla.getColumnModel().getColumn(ultima).setMaxWidth(45);
	}

	private void estiloEtiqueta(JLabel label) {
		label.setFont(new Font("Segoe UI", Font.BOLD, 13));
		label.setForeground(new Color(203, 213, 225));
	}

	private void estiloCombo(JComboBox<String> combo) {
		combo.setFont(new Font("Segoe UI", Font.BOLD, 14));
		combo.setForeground(new Color(15, 23, 42));
		combo.setBackground(new Color(241, 245, 249));
	}

	private void estiloBotonPrimario(JButton boton) {
		boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
		boton.setForeground(Color.WHITE);
		boton.setBackground(new Color(37, 99, 235));
		boton.setFocusPainted(false);
		boton.setBorderPainted(false);
		boton.setOpaque(true);
		boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}