/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appinformes;

import static appinformes.AppInformes.conexion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author SAWER
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem listFacturas;
    @FXML
    private MenuItem ventTotal;
    @FXML
    private MenuItem facturaCliente;
    @FXML
    private MenuItem SubInfor;
    @FXML
    private MenuItem salir;
    AppInformes ai = new AppInformes();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void listadoFacturas(ActionEvent event) throws JRException {
        ai.conectaBD();
        ai.generaListadoFactura();
        System.out.println("Generando listado facturas");
    }

    @FXML
    private void ventaTotal(ActionEvent event) {
        ai.conectaBD();
        ai.generaVentaTotal();
        System.out.println("Generando informe");
    }

    @FXML
    private void facturaCliente(ActionEvent event) throws FileNotFoundException, IOException, JRException {
        ai.conectaBD();
        Stage stage = new Stage();
        TextField tituloIntro = new TextField("");

        Button btnGenerar = new Button();
        btnGenerar.setText("Generar informe");

        VBox root = new VBox();
        root.getChildren().addAll(tituloIntro, btnGenerar);

        btnGenerar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ai.conectaBD();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(AppInformes.class.getResource("Genera_Factura_Cliente.jasper"));
                    Map parametros = new HashMap();

                    int nproducto = Integer.valueOf(tituloIntro.getText());
                    parametros.put("id", nproducto);

                    JasperPrint jp = (JasperPrint) JasperFillManager.fillReport(jr, parametros, conexion);
                    JasperViewer.viewReport(jp, false);

                } catch (JRException ex) {
                    System.out.println("Error al recuperar el jasper");
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void subInfor(ActionEvent event) {
        ai.conectaBD();
        ai.generaSubInforme();
        System.out.println("Generando informe");
    }
}
