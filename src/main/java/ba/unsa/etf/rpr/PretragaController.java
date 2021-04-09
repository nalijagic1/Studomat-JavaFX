package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PretragaController implements Initializable
{
    public TextField fldPretraga;
    private static ObservableList<String> direktoriji = FXCollections.observableArrayList();
    private static  String naziv ="";
    public ListView listPutanje;
    int velicina = 0;

    public static void dodajUListu(){

    }
    public static void searchFile(File f){
        Thread t = new Thread(() -> {
            try
            {
                if(f.isDirectory())
                {
                    File [] fi = f.listFiles();
                    for(int i=0;i<fi.length;i++)
                    {
                        searchFile(fi[i]);
                    }
                }
                else
                    {
                        if(f.getName().toLowerCase().contains(naziv.toLowerCase()))
                    {
                        Platform.runLater(() -> {
                            //System.out.print("file found " + f.getAbsolutePath());
                            direktoriji.add(f.getAbsolutePath());
                        });
                    }
                }
            }
            catch(Exception e)
            {
            }
        });
        t.start();
    }
    public void pretrazi(ActionEvent actionEvent) {
        naziv = fldPretraga.getText();
        searchFile( FileSystemView.getFileSystemView().getHomeDirectory());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        direktoriji.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                listPutanje.setItems(direktoriji);

            }

        });
    }
}
