package br.univel.jshare.model.tabelas;

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

		matrix = new Object[tCliente][6];

		List<Cliente> list = new ArrayList<>(dados.keySet());

		list.sort((o1, o2) -> o1.getNome().compareTo(o2.getNome()));

		int lista = 0;

		for (Cliente cliente : list) {

			for (Arquivo arquivo : dados.get(cliente)) {
				matrix[lista][0] = cliente.getId();
				matrix[lista][1] = cliente.getNome();
				matrix[lista][2] = arquivo.getId();
				matrix[lista][3] = arquivo.getNome();
				matrix[lista][4] = arquivo;
				matrix[lista][5] = cliente;
				lista++;
			}
		}
	}

	@Override
	public int getColumnCount() {
		return 5;
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
		return (Cliente) matrix[row][3];
	}

	public Arquivo getArquivo(int row) {
		return (Arquivo) matrix[row][2];
	}

}
