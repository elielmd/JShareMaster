package br.univel.jshare.model.servidor;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.univel.jshare.comum.Arquivo;
import br.univel.jshare.comum.Cliente;
import br.univel.jshare.comum.IServer;
import br.univel.jshare.comum.TipoFiltro;
import br.univel.jshare.model.LeituraEscritaDeArquivos;
import br.univel.jshare.view.PrincipalJShareMaster;

public class ServidorJMaster implements IServer {

	private List<Cliente> listaClientes = new ArrayList<>();
	public Map<Cliente, List<Arquivo>> mapArquivos = new HashMap<>();
	private String ipServidor;
	private Integer portaServidor;
	private PrincipalJShareMaster principalJShareMaster;

	public ServidorJMaster(PrincipalJShareMaster principalJShareMaster, String ipServidor, Integer portaServidor) {
		this.principalJShareMaster = principalJShareMaster;
		this.ipServidor = ipServidor;
		this.portaServidor = portaServidor;

		try {
			inicializaServerRMI();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public String serverToString() {
		return "Servidor IP: " + this.ipServidor + " | PORTA: " + this.portaServidor.toString();
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		listaClientes.add(c);
		principalJShareMaster.mostrar(c.getNome() + "(" + c.getIp() + " - " + c.getPorta() + ")"
				+ " se conectou " + serverToString() + ".");
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista) throws RemoteException {
		mapArquivos.put(c, lista);

		principalJShareMaster.mostrar(c.getNome().concat(" publicou sua lista de arquivos."));
		principalJShareMaster.mostrar("Arquivos disponiveis:");
		for (Arquivo arq : lista) {
			principalJShareMaster.mostrar("\t" + arq.getNome().concat(".").concat(arq.getExtensao()));
		}

		principalJShareMaster.listarArquivos(mapArquivos);
	}

	

	@Override
	public byte[] baixarArquivo(Cliente cli, Arquivo arq) throws RemoteException {

		principalJShareMaster.mostrar("Cliente '" + cli.getNome()
				+ "' esta baixando o arquivo '".concat(arq.getNome()).concat("'.").concat(arq.getExtensao()));
		byte[] dados = null;

		LeituraEscritaDeArquivos io = new LeituraEscritaDeArquivos();
		dados = io
				.leia(new File(arq.getPath().concat("\\").concat(arq.getNome()).concat(".").concat(arq.getExtensao())));

		principalJShareMaster.mostrar("Cliente '" + cli.getNome()
				+ "' baixou o arquivo '".concat(arq.getNome()).concat("'.").concat(arq.getExtensao()));
		return dados;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		listaClientes.remove(c);
		mapArquivos.remove(c);
		principalJShareMaster.mostrar(c.getNome().concat(" se desconectou do JShare."));
	}

	private void inicializaServerRMI() throws RemoteException {
		principalJShareMaster.mostrar(serverToString().concat(" iniciando..."));
		System.setProperty("java.rmi.server.hostname", this.ipServidor);

		IServer servidor = (IServer) UnicastRemoteObject.exportObject(this, 0);

		Registry registry = LocateRegistry.createRegistry(this.portaServidor);

		registry.rebind(IServer.NOME_SERVICO, servidor);

		principalJShareMaster.mostrar(serverToString().concat(" iniciado com sucesso."));
	}

	public Integer getPorta() {
		return portaServidor;
	}

	public void setPorta(Integer porta) {
		this.portaServidor = porta;
	}

	public String getIp() {
		return ipServidor;
	}

	public void setIp(String ip) {
		this.ipServidor = ip;
	}

	public void setTelaPrincipal(PrincipalJShareMaster principalJShareMaster) {
		this.principalJShareMaster = principalJShareMaster;
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String query, TipoFiltro tipoFiltro, String filtro)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}