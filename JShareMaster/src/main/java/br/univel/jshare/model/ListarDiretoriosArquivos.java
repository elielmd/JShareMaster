package br.univel.jshare.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.univel.jshare.comum.Arquivo;
import br.univel.jshare.model.criptografia.Md5Util;

public class ListarDiretoriosArquivos {
	
	public static List<Arquivo> listarArquivos(File dirStart){
		List<Arquivo> listaArquivos = new ArrayList<>();
		
		File arquivos[] = dirStart.listFiles();
		for (int i = 0; i < arquivos.length; i++) {
			File file = arquivos[i];		
			Arquivo arq = new Arquivo();
			arq.setNome(file.getName());
			arq.setTamanho(file.length());
			arq.setPath(file.getParent());
			arq.setDataHoraModificacao(new Date(file.lastModified() * 1000));
			arq.setMd5(Md5Util.getMD5Checksum(file.getPath()));
			arq.setExtensao(file.getName().substring((file.getName().lastIndexOf(".") + 1)));
			arq.setNome(arq.getNome().replace("." + arq.getExtensao(), ""));
			arq.setId(i + 1);
			listaArquivos.add(arq);
		} 
		
		return listaArquivos;		
	}
}
