package br.univel.jshare.model.tabela;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import br.univel.jshare.comum.Arquivo;
import br.univel.jshare.comum.Cliente;

public class ModeloArquivos extends AbstractTableModel implements TableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7669226278646851755L;
	private Object[][] matrix;

	public ModeloArquivos(Map<Cliente, List<Arquivo>> dados) {

		int tCliente = 0;

		for (Entry<Cliente, List<Arquivo>> e : dados.entrySet()) {
			if (e.getValue() != null) {
				tCliente += e.getValue().size();
			}
		}

		matrix = new Object[tCliente][9];

		List<Cliente> list = new ArrayList<>(dados.keySet());

		list.sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));

		int lista = 0;

		for (Cliente cliente : list) {

			for (Arquivo arquivo : dados.get(cliente)) {
				matrix[lista][0] = cliente.getNome();
				matrix[lista][1] = cliente.getIp();
				matrix[lista][2] = arquivo.getId();
				matrix[lista][3] = arquivo.getNome();
				matrix[lista][4] = arquivo.getExtensao();
				matrix[lista][5] = arquivo.getTamanho();				
				matrix[lista][6] = arquivo;
				matrix[lista][7] = cliente;
				matrix[lista][8] = cliente.getId();
				lista++;
			}
		}
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		return matrix.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		return matrix[arg0][arg1];
	}

	public Cliente getCliente(int row) {
		return (Cliente) matrix[row][7];
	}

	public Arquivo getArquivo(int row) {
		return (Arquivo) matrix[row][6];
	}
	
	public Arquivo getMeuItem(int row) {
		Arquivo arquivo = new Arquivo();
		arquivo.setId(row);
		return arquivo;
	}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
			case 0:
				return "CLIENTE"; 
			case 1:
				return "IP CLIENTE"; 
			case 2:
				return "ID ARQUIVO";
			case 3:
				return "ARQUIVO"; 
			case 4:
				return "EXTENSAO"; 
			case 5:
				return "TAMANHO"; 
			default:
				return super.getColumnName(column);
		}
	}	

}
