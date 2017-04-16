package br.univel.jshare.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.univel.jshare.comum.Arquivo;
import br.univel.jshare.comum.Cliente;
import br.univel.jshare.comum.IServer;
import br.univel.jshare.model.JNumberField;
import br.univel.jshare.model.LerIp;
import br.univel.jshare.model.ListarDiretoriosArquivos;
import br.univel.jshare.model.servidor.ServidorJMaster;
import br.univel.jshare.model.tabelas.ModeloArquivos;

public class PrincipalJShareMaster extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3333226296402664888L;
	private JPanel contentPane;
	private JTextField tfIp;
	private JNumberField tfPorta;
	private JTextField tfNome;
	private JSplitPane splitPane;
	private Cliente cliente;
	private JTextField textField;
	private JRadioButton rdbtnCliente;
	private JRadioButton rdbtnServidor;
	private JButton btnDesconectar;
	private JButton btnConectar;
	private JTextArea textArea;
	private JTextField textField_1;
	private JTable tabelaArquivos;
	private ServidorJMaster servidor;
	private Registry registry;
	private IServer iServer;
	public static final String PASTA = "C:/Shared/";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalJShareMaster frame = new PrincipalJShareMaster();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalJShareMaster() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("src\\readicon.png"));
		setTitle("MofoShare");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerSize(10);
		contentPane.add(splitPane, BorderLayout.CENTER);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);

		JPanel panelConexao = new JPanel();
		tabbedPane.addTab("Conexão", new ImageIcon("src\\icon_connect.gif"), panelConexao, null);
		GridBagLayout gbl_panelConexao = new GridBagLayout();
		gbl_panelConexao.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panelConexao.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panelConexao.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelConexao.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelConexao.setLayout(gbl_panelConexao);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.gridheight = 3;
		gbc_panel_5.insets = new Insets(5, 5, 0, 5);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panelConexao.add(panel_5, gbc_panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("src\\fileshare.png"));
		panel_5.add(lblNewLabel, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Funcionamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 2;
		gbc_panel_3.insets = new Insets(5, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 0;
		panelConexao.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_3.rowHeights = new int[] { 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);

		rdbtnCliente = new JRadioButton("Apenas Cliente");
		rdbtnCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_rdbtnCliente = new GridBagConstraints();
		gbc_rdbtnCliente.anchor = GridBagConstraints.WEST;
		gbc_rdbtnCliente.gridx = 1;
		gbc_rdbtnCliente.gridy = 0;
		panel_3.add(rdbtnCliente, gbc_rdbtnCliente);

		rdbtnServidor = new JRadioButton("Servidor e Cliente");
		rdbtnServidor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnServidor.setSelected(true);
		GridBagConstraints gbc_rdbtnServidor = new GridBagConstraints();
		gbc_rdbtnServidor.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnServidor.gridx = 0;
		gbc_rdbtnServidor.gridy = 0;
		panel_3.add(rdbtnServidor, gbc_rdbtnServidor);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnServidor);
		bg.add(rdbtnCliente);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(
				new TitledBorder(null, "Par\u00E2metros", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridwidth = 2;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 1;
		panelConexao.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lbNome = new JLabel("Nome");
		GridBagConstraints gbc_lbNome = new GridBagConstraints();
		gbc_lbNome.insets = new Insets(0, 0, 5, 5);
		gbc_lbNome.anchor = GridBagConstraints.EAST;
		gbc_lbNome.gridx = 0;
		gbc_lbNome.gridy = 0;
		panel_4.add(lbNome, gbc_lbNome);

		tfNome = new JTextField();
		tfNome.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_tfNome = new GridBagConstraints();
		gbc_tfNome.gridwidth = 3;
		gbc_tfNome.insets = new Insets(0, 0, 5, 0);
		gbc_tfNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfNome.gridx = 1;
		gbc_tfNome.gridy = 0;
		panel_4.add(tfNome, gbc_tfNome);
		tfNome.setColumns(10);

		JLabel tfEndereco = new JLabel("Endereço");
		GridBagConstraints gbc_tfEndereco = new GridBagConstraints();
		gbc_tfEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_tfEndereco.anchor = GridBagConstraints.EAST;
		gbc_tfEndereco.gridx = 0;
		gbc_tfEndereco.gridy = 1;
		panel_4.add(tfEndereco, gbc_tfEndereco);

		tfIp = new JTextField();
		tfIp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_tfIp = new GridBagConstraints();
		gbc_tfIp.insets = new Insets(0, 0, 5, 5);
		gbc_tfIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfIp.gridx = 1;
		gbc_tfIp.gridy = 1;
		panel_4.add(tfIp, gbc_tfIp);
		tfIp.setColumns(10);

		JLabel lbPorta = new JLabel("Porta");
		GridBagConstraints gbc_lbPorta = new GridBagConstraints();
		gbc_lbPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lbPorta.anchor = GridBagConstraints.EAST;
		gbc_lbPorta.gridx = 2;
		gbc_lbPorta.gridy = 1;
		panel_4.add(lbPorta, gbc_lbPorta);

		tfPorta = new JNumberField();
		tfPorta.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_tfPorta = new GridBagConstraints();
		gbc_tfPorta.insets = new Insets(0, 0, 5, 0);
		gbc_tfPorta.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfPorta.gridx = 3;
		gbc_tfPorta.gridy = 1;
		panel_4.add(tfPorta, gbc_tfPorta);
		tfPorta.setColumns(10);

		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnDesconectar = new GridBagConstraints();
		gbc_btnDesconectar.anchor = GridBagConstraints.NORTH;
		gbc_btnDesconectar.insets = new Insets(0, 0, 0, 5);
		gbc_btnDesconectar.gridx = 1;
		gbc_btnDesconectar.gridy = 2;
		panelConexao.add(btnDesconectar, gbc_btnDesconectar);
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desconectarDoServidor();
			}
		});

		btnConectar = new JButton("Conectar");
		btnConectar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_btnConectar = new GridBagConstraints();
		gbc_btnConectar.anchor = GridBagConstraints.NORTH;
		gbc_btnConectar.gridx = 2;
		gbc_btnConectar.gridy = 2;
		panelConexao.add(btnConectar, gbc_btnConectar);
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conectarNoServidor();
			}
		});

		JPanel panelBusca = new JPanel();
		tabbedPane.addTab("Busca", new ImageIcon("src\\search.png"), panelBusca, null);
		GridBagLayout gbl_panelBusca = new GridBagLayout();
		gbl_panelBusca.columnWidths = new int[] { 328, 10, 0 };
		gbl_panelBusca.rowHeights = new int[] { 10, 0, 0, 0, 0 };
		gbl_panelBusca.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelBusca.rowWeights = new double[] { 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		panelBusca.setLayout(gbl_panelBusca);

		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 0;
		panelBusca.add(panel_6, gbc_panel_6);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		panelBusca.add(panel, gbc_panel);

		JLabel lbBusca = new JLabel("Arquivo");
		lbBusca.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lbBusca);

		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(textField);
		textField.setColumns(35);

		JComboBox cbFiltro = new JComboBox();
		cbFiltro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(cbFiltro);

		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("Buscar");
		panel.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		panelBusca.add(panel_1, gbc_panel_1);

		JScrollPane scrollPaneArq = new JScrollPane();
		panel_1.add(scrollPaneArq);

		tabelaArquivos = new JTable();
		scrollPaneArq.setViewportView(tabelaArquivos);

		JPanel panelTransferencia = new JPanel();
		tabbedPane.addTab("Transferências", new ImageIcon("src\\download.png"), panelTransferencia, null);

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		textArea = new JTextArea();
		textArea.setForeground(new Color(50, 205, 50));
		textArea.setBackground(Color.BLACK);
		scrollPane.setViewportView(textArea);
		splitPane.setDividerLocation(200);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				relocatedivider();
			}
		});
		configuraDadosPadrao();

	}

	protected void configuraDadosPadrao() {
		rdbtnCliente.setSelected(false);
		tfIp.setText(LerIp.meuIp());
		tfNome.setText("Eliel");
		tfPorta.setText("1818");
		btnDesconectar.setEnabled(false);
	}

	protected void desconectarDoServidor() {
		// TODO Auto-generated method stub

	}

	protected void conectarNoServidor() {
		cliente = new Cliente();
		cliente.setId(1);
		cliente.setIp(LerIp.meuIp());
		cliente.setNome(tfNome.getText());
		cliente.setPorta(tfPorta.getNumber());

		servidor = new ServidorJMaster(getPrincipalJShareMaster(), cliente.getIp(), cliente.getPorta());

		if (rdbtnCliente.isSelected()) {
			try {
				try {
					registry = LocateRegistry.getRegistry(tfIp.getText(), tfPorta.getNumber());
					iServer = (IServer) registry.lookup(IServer.NOME_SERVICO);
				} catch (RemoteException | NotBoundException e) {
					e.printStackTrace();
				}
				iServer.registrarCliente(cliente);
				File pastaArquivo = new File(PASTA);
				if (!pastaArquivo.exists()) {
					pastaArquivo.mkdir();
				}

				List<Arquivo> lista = ListarDiretoriosArquivos.listarArquivos(pastaArquivo);
				
				iServer.publicarListaArquivos(cliente, lista);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else {
			try {
				servidor.registrarCliente(cliente);
				List<Arquivo> lista = ListarDiretoriosArquivos.listarArquivos(new File("Share"));
				servidor.publicarListaArquivos(cliente, lista);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		btnConectar.setEnabled(false);
		btnDesconectar.setEnabled(true);
	}

	protected void relocatedivider() {
		splitPane.setDividerLocation(this.getHeight() - 150);
	}

	public void listarArquivos(Map<Cliente, List<Arquivo>> mapArquivos) {

		ModeloArquivos modeloArq = new ModeloArquivos(mapArquivos);
		tabelaArquivos.setRowSorter(null);
		tabelaArquivos.setModel(modeloArq);
	}

	public void mostrar(String string) {
		textArea.append(" -> ");
		textArea.append(string);
		textArea.append("\n");
	}

	private PrincipalJShareMaster getPrincipalJShareMaster() {
		return this;
	}
}
