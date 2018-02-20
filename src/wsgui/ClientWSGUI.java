package wsgui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.io.PrintWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.wb.swt.SWTResourceManager;

import productos.*;

public class ClientWSGUI {
	private static Text searchField;
	private static ProductosAPI productosAPI = new ProductosAPI();
	private static Text usuario;
	private static Text pass;

	/**
	 * Launch the application.
	 * @param args
	 */
	
	public static void main(String[] args) {
		ImageUtil imgUtil = new ImageUtil();
		Display display = Display.getDefault();
		Shell shlCatlogoDeProductos = new Shell();
		shlCatlogoDeProductos.setSize(340, 340);
		shlCatlogoDeProductos.setText("Catálogo de Productos WS");
		

	    Image icon = new Image(display, ClientWSGUI.class.getResourceAsStream("/Images/stunnedServicesLogo.png"));
	    Image full_logo = imgUtil.resize(new Image(display, ClientWSGUI.class.getResourceAsStream("/Images/stunnedServices.png")),130,130);

	    
	    shlCatlogoDeProductos.setImage(icon);   
		
		Group group = new Group(shlCatlogoDeProductos, SWT.BORDER | SWT.SHADOW_OUT);
		group.setBounds(10, 83, 320, 209);
		
		searchField = new Text(group, SWT.BORDER | SWT.CENTER);
		searchField.setBounds(26, 57, 255, 25);
		
		Button btnBuscar = new Button(group, SWT.CENTER);
		btnBuscar.setBounds(112, 153, 97, 32);
		btnBuscar.setEnabled(false);
		btnBuscar.setText("Buscar");
		
		Button radioBtnCategoria = new Button(group, SWT.RADIO);
		radioBtnCategoria.setBounds(20, 17, 102, 20);
		radioBtnCategoria.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchField.setFocus();
				searchField.setMessage("Ej. Móviles");
				btnBuscar.setEnabled(true);
			}
		});
		radioBtnCategoria.setText("Categoría");
		
		Button radioBtnClaveProd = new Button(group, SWT.RADIO);
		radioBtnClaveProd.setBounds(128, 17, 131, 20);
		radioBtnClaveProd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchField.setFocus();
				searchField.setMessage("Ej. MVIPHX18");
				btnBuscar.setEnabled(true);
			}
		});
		radioBtnClaveProd.setText("Clave Producto");
		
		usuario = new Text(group, SWT.BORDER | SWT.CENTER);
		usuario.setBounds(26, 91, 255, 25);
		usuario.setMessage("Usuario");
		
		pass = new Text(group, SWT.PASSWORD | SWT.BORDER | SWT.CENTER);
		pass.setBounds(26, 122, 255, 25);
		pass.setMessage("Contraseña");
		
		btnBuscar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Verify text field is in correct format
				if(radioBtnCategoria.getSelection()) {
					ResultsShell cs1 = new ResultsShell(shlCatlogoDeProductos,
					  productosAPI.getProductoByCategoria(searchField.getText(), usuario.getText(), pass.getText())
					);
					
				} else if (radioBtnClaveProd.getSelection()) {
					ResultsShell cs1 = new ResultsShell(shlCatlogoDeProductos,
					  productosAPI.getProductoByClave(searchField.getText(), usuario.getText(), pass.getText())
					);
				}
			}
		});
		
		Group searchType = new Group(shlCatlogoDeProductos, SWT.NONE);
		searchType.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Label lblTitle = 	new Label(shlCatlogoDeProductos, SWT.NONE);
		lblTitle.setFont(SWTResourceManager.getFont("Serif", 12, SWT.BOLD));
		lblTitle.setBounds(31, 10, 282, 51);
		lblTitle.setText("Catálogo de productos");
		lblTitle.setImage(full_logo);

		shlCatlogoDeProductos.open();
		shlCatlogoDeProductos.layout();
		while (!shlCatlogoDeProductos.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}

class ResultsShell {
  ResultsShell(Shell parent, ProductosResponse resp) {
    Shell child = new Shell(parent);
    StyledText styledText = new StyledText(child, SWT.WRAP | SWT.V_SCROLL);
	styledText.setBounds(25, 10, 180, 250);
    if(resp.code == 200) {
    		styledText.setText("Código: " + resp.code
    			+ "\nMensaje: "+resp.message
    			+ "\nXml:\n" + resp.xml);
    		child.setSize(400, 300);
	    	Label lblTitle = new Label(child, SWT.NONE);
	    	lblTitle.setBounds(215, 10, 282, 51);
	    	lblTitle.setText("Guardar respuesta");
	    	Text path = new Text(child, SWT.BORDER | SWT.LEFT);
		path.setBounds(215, 30, 150, 25);
		path.setText("/Users/mrcrjs/xmlResp/resp.xml");
		Button btnSave = new Button(child, SWT.CENTER);
		btnSave.setBounds(215, 60, 100, 32);
		btnSave.setText("Guardar");
		
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedPath = path.getText();
				try {
					PrintWriter writer = new PrintWriter(selectedPath, "UTF-8");
					writer.println(resp.xml);
					writer.close();
	            } catch(java.io.IOException eio){
	            	//
	            }
			}
		});
    } else {
    		child.setSize(250, 300);
    		styledText.setText("Código: " + resp.code
    				+ "\nMensaje: "+resp.message);
    }
    child.open();
  }
  
}
