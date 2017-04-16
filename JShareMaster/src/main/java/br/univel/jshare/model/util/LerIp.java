package br.univel.jshare.model.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LerIp {

	public static String meuIp() {

		InetAddress IP;
		String IPString = "";

		try {
			IP = InetAddress.getLocalHost();
			IPString = IP.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return IPString;
	}
}
