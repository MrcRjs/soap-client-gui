package productos;

import java.io.StringReader;
import java.rmi.RemoteException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import buap_api.*;

public class ProductosAPI {
	private WebServicesBUAPPortTypeProxy client = new WebServicesBUAPPortTypeProxy();
	
	public ProductosResponse getProductoByCategoria(String categoria, String usuario, String pass) {
		try {
			RespuestaConsulta res = client.getProd(usuario, pass, categoria);
			System.out.println("Codigo: " + res.getCodigo());
			System.out.println("Mensaje: " + res.getMensaje());
			System.out.println("Xml: " + res.getXml());
			return new ProductosResponse(res.getCodigo(), res.getMensaje(), res.getXml());

		} catch (RemoteException e) {
			System.out.println(e.getCause());
			e.printStackTrace();
			return new ProductosResponse(305, "Error de conexión al servidor", "");
		}
	}
	
	public ProductosResponse getProductoByClave(String clave, String usuario, String pass) {
		try {
			RespuestaConsulta res = client.getDetails(usuario, pass, clave);
			System.out.println("Codigo: " + res.getCodigo());
			System.out.println("Mensaje: " + res.getMensaje());
			System.out.println("Xml: " + res.getXml());
			return new ProductosResponse(res.getCodigo(), res.getMensaje(), res.getXml());

		} catch (RemoteException e) {
			System.out.println(e.getCause());
			e.printStackTrace();
			return new ProductosResponse(305, "Error de conexión al servidor", "");
		}
	}
	
	public static Document loadXML(String xml) throws Exception
	{
	   DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
	   DocumentBuilder bldr = fctr.newDocumentBuilder();
	   InputSource insrc = new InputSource(new StringReader(xml));
	   return bldr.parse(insrc);
	}
	
	public static void main(String[] args) {
		WebServicesBUAPPortTypeProxy client = new WebServicesBUAPPortTypeProxy();

		try {
			RespuestaConsulta res = client.getProd("Marco", "1234", "Moviles");

			System.out.println("Codigo: " + res.getCodigo());
			System.out.println("Mensaje: " + res.getMensaje());
			System.out.println("Logo: " + res.getXml());

		} catch (RemoteException e) {
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}
}
