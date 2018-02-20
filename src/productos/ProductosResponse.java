package productos;

public class ProductosResponse {
	public int code;
	public String message;
	public String xml;
	
	ProductosResponse(int code, String message, String xml){
		this.code = code;
		this.message = message;
		this.xml = xml;
	}
}
