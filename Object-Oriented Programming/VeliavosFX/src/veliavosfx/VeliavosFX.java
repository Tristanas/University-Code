/*
 * Autorius:
 * Vilius Minkevicius
 * PS2
 */
package veliavosfx;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Vilius
 */
//to do - scroll bars, panable, zoomable interfeisai

public class VeliavosFX extends Application {
    String ivestiesFailoPavadinimas = "veliavos.txt", isvedimoFPavadinimas = "veliavos.txt";
    int counter = 0;
    ListView veliavosDuomenys;
    //scena veliavos duomenu ivedimui. Paspaudzius issoks langas, kuriame ivedami duomenys ir paspaudus sukurti langas uzdaromas, nauja veliava atsiranda. 
    //Isjungus langa (close request), veliava neatsiranda (nebent visi laukai uzpildyti)
    GridPane veliavosIvestis = new GridPane();
    TextField salis = new TextField(), imone = new TextField(), kodas = new TextField(), produktai = new TextField();
    Button sukurti = new Button("Sukurti veliava");
    ToolBar toolbar = new ToolBar();
    private static final int MIN_PIXELS = 100;
    CheckBox arPrideda = new CheckBox();
    Label prideti = new Label("Prideti naujas veliavas:");
    Button filtruotojas = new Button("Filtruoti"),
            issaugoti = new Button("Saugoti veliavas"),
            krauti = new Button("Pakrauti veliavas"),
            priartinti = new Button("Padidinti"),
            atitolinti = new Button("Sumazinti");
    Filtruotojas filtruoklis;
    ArrayList<Veliava> veliavos = new ArrayList(), veliavosPakrovimui;
    AnchorPane root = new AnchorPane();
       @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        paruostiScena();
        FileInputStream input = new FileInputStream("mapa-america-central.jpg"),
        flagInput = new FileInputStream("velniava.png");
        Image zemelapis = new Image(input), veliavosPav = new Image(flagInput);
        double veliavosX = veliavosPav.getWidth(), veliavosY = veliavosPav.getHeight(), 
                zemelapioX = zemelapis.getWidth(), zemelapioY = zemelapis.getHeight();
        ImageView paveiksloLaukas = new ImageView(zemelapis);
   
        
        //AnchorPane.setTopAnchor(toolbar, 0.0);
        AnchorPane.setTopAnchor(paveiksloLaukas, 30.0);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Veliavos kurimas");
        Scene langasPildymui = new Scene(veliavosIvestis, 250, 100);
        secondaryStage.setScene(langasPildymui);
        secondaryStage.setOnCloseRequest((WindowEvent ev) -> {
            counter--;
            veliavos.remove(counter);
        });
        Stage filtruotojoLangas = new Stage();
        IvestiesScena gridPane = new IvestiesScena("Filtruoti");
        Scene scenaFiltruotojui = new Scene(gridPane, 250, 100);
        gridPane.getSukurti().setOnMouseClicked((MouseEvent ev) -> {
            filtruoklis = new Filtruotojas(gridPane.getSalis().getText(), gridPane.getImone().getText(), gridPane.getKodas().getText(), gridPane.getProduktai().getText());
            filtruoklis.filtruotiVeliavas(veliavos);
            gridPane.isvalytiLaukus();
            filtruotojoLangas.close();
        });
        filtruotojoLangas.setTitle("Filtravimas");
        filtruotojoLangas.setScene(scenaFiltruotojui);
        root.getChildren().add(toolbar);
        Scene scene = new Scene(root, 1168, 796);
        
        
        //Event handleriai:
        EventHandler<MouseEvent> antVeliavos = (MouseEvent event) -> {
            veliavosDuomenys = new ListView();
            //System.out.println("Pele ant veliavos");
            String duomenys = event.getSource().toString();
            String duomenu[] = duomenys.split(";");
            //double x = event.getSceneX(), y = event.getSceneY();
            Veliava veikiamoji = (Veliava) event.getSource();
            double x = veikiamoji.getX(), y = veikiamoji.getY();
            
            veliavosDuomenys.getItems().add("Salis: " + duomenu[0]);
            veliavosDuomenys.getItems().add("Imone: " + duomenu[1]);
            veliavosDuomenys.getItems().add("Produktai: " + duomenu[3]);
            veliavosDuomenys.getItems().add("Kodas: " + duomenu[2]);
            if (x + 150 > root.getWidth()) x -= 200 + veliavosX;
            if (y + 100 > root.getHeight()) y -= 100;
            veliavosDuomenys.setLayoutX(x + veliavosX);
            veliavosDuomenys.setLayoutY(y);
            veliavosDuomenys.setPrefSize(200, 100);
            root.getChildren().add(veliavosDuomenys);
        },
        palikoVeliava = (MouseEvent event) -> {
            veliavosDuomenys.setVisible(false);
            //System.out.println("Pele paliko veliava");
        },
        paspaudeAntVeliavos = (MouseEvent event) -> {
            //System.out.println("Paspaudei veliava");
        };
        
        EventHandler<MouseEvent> paspaude = (MouseEvent event) -> {
            double proporcija = zemelapioX / paveiksloLaukas.getViewport().getWidth();
            if (arPrideda.isSelected()) {
                Veliava nauja = new Veliava(veliavosPav, event.getX(), event.getY());
                nauja.setXsaugojimui(paveiksloLaukas.getViewport().getMinX() + nauja.getX() / proporcija);
                nauja.setYsaugojimui(paveiksloLaukas.getViewport().getMinY() + nauja.getY() / proporcija);
            veliavos.add(nauja);
            counter++;
            
            nauja.setOnMouseEntered(antVeliavos);
            nauja.setOnMouseExited(palikoVeliava);
            nauja.setOnMouseClicked(paspaudeAntVeliavos);
            secondaryStage.setX(event.getScreenX());
            secondaryStage.setY(event.getScreenY());
            secondaryStage.show();
            //System.out.println("Lango matmenys: " + root.getWidth() + "x" + root.getHeight());
            }
        },
        paspaudeFiltruoti = (MouseEvent event) -> {
          //issoka Langas su laukais, kuriu vertes bus priskirtos filtruotojui
          filtruotojoLangas.show();
        },
        paspaudeSukurti = (MouseEvent event) -> {
            //Cia jau turetu buti veliavos[counter] buti sukurta, tad reikia nustatyti duomenis veliavos[counter-1]
            int i = counter - 1;
            nustatytiLaukus(veliavos.get(i), salis, imone, kodas, produktai);
            if(veliavos.get(i).getValstybe().isEmpty()) veliavos.get(i).setValstybe("-");
            if(veliavos.get(i).getImone().isEmpty()) veliavos.get(i).setImone("-");
            if(veliavos.get(i).getKodas().isEmpty()) veliavos.get(i).setKodas("-");
            if(veliavos.get(i).getProduktai().isEmpty()) veliavos.get(i).setProduktai("-");
            root.getChildren().add(veliavos.get(i));
            salis.clear();
            imone.clear();
            kodas.clear();
            produktai.clear();
            secondaryStage.close();
        };
        
        
       
        //EventHandleriai baigesi
        
        //Pakrovimas is dokumento
        
        sukurti.setOnMouseClicked(paspaudeSukurti);
        filtruotojas.setOnMouseClicked(paspaudeFiltruoti);
        paveiksloLaukas.setOnMouseClicked(paspaude);
        paveiksloLaukas.setPreserveRatio(true);
        double width = zemelapioX, height = zemelapioY;
        reset(paveiksloLaukas, width / 2, height / 2);
        paveiksloLaukas.setViewport(new Rectangle2D(0, 0, zemelapioX, zemelapioY));
        ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();
        
        paveiksloLaukas.setOnMousePressed(e -> {
            
            Point2D mousePress = imageViewToImage(paveiksloLaukas, new Point2D(e.getX(), e.getY()));
            mouseDown.set(mousePress);
        });
        paveiksloLaukas.setOnMouseDragged(e -> {
            Point2D dragPoint = imageViewToImage(paveiksloLaukas, new Point2D(e.getX(), e.getY()));
            shift(paveiksloLaukas, dragPoint.subtract(mouseDown.get()));
            mouseDown.set(imageViewToImage(paveiksloLaukas, new Point2D(e.getX(), e.getY())));
            atnaujintiVeliavuPozicijas(veliavos, paveiksloLaukas);
        });
        paveiksloLaukas.setOnScroll(e -> {
            double paskrolino = e.getDeltaY();
            if(paskrolino > 0) System.out.println("Scrolina i virsu");
            else System.out.println("Scrolina zemyn");
            double delta = e.getDeltaY();
            Rectangle2D viewport = paveiksloLaukas.getViewport();
            double scale = clamp(Math.pow(1.01, delta),
            
                // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
            Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

            // don't scale so that we're bigger than image dimensions:
            Math.max(width / viewport.getWidth(), height / viewport.getHeight())

            );
           
            Point2D mouse = imageViewToImage(paveiksloLaukas, new Point2D(e.getX(), e.getY()));

            double newWidth = viewport.getWidth() * scale;
            double newHeight = viewport.getHeight() * scale;

            // To keep the visual point under the mouse from moving, we need
            // (x - newViewportMinX) / (x - currentViewportMinX) = scale
            // where x is the mouse X coordinate in the image

            // solving this for newViewportMinX gives

            // newViewportMinX = x - (x - currentViewportMinX) * scale 

            // we then clamp this value so the image never scrolls out
            // of the imageview:

            double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale, 
                    0, width - newWidth);
            double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale, 
                    0, height - newHeight);

            paveiksloLaukas.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
            
            atnaujintiVeliavuPozicijas(veliavos, paveiksloLaukas);
        });
        
        Pane container = new Pane(paveiksloLaukas);
        container.setPrefSize(zemelapioX, zemelapioY);
        
        paveiksloLaukas.fitWidthProperty().bind(container.widthProperty());
        paveiksloLaukas.fitHeightProperty().bind(container.heightProperty());
        
        root.getChildren().add(container);
        container.setLayoutY(30);
        
        //Baigiamieji paruosiamieji darbai: ankstesniu veliavu idejimas
        pakrautiIsDokumento(root, antVeliavos, palikoVeliava, paspaudeAntVeliavos, veliavosPav);

        
        primaryStage.setTitle("Zemelapis");
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            System.out.println("Isjungimas pastebetas");
            VeliavuBaze.setVeliavos(veliavos);
            try {
                VeliavuBaze.Issaugoti(isvedimoFPavadinimas);
                System.out.println("Sekmingai issaugotos veliavos");
            } catch (IOException ex) {
                Logger.getLogger(VeliavosFX.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();
        
        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }
    
    public void transformuotiScena(double procentai, AnchorPane talpykla, ImageView zemelapis) {
        for (Node elementas : talpykla.getChildren()) {
            elementas.setScaleX(elementas.getScaleX()*procentai);
            elementas.setScaleY(elementas.getScaleY()*procentai);
            
            if (elementas == zemelapis) {
            
            }
            else {
                
                elementas.relocate(elementas.getLayoutX()*procentai, elementas.getLayoutY()*procentai);
            }
        }
    }
    
     private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }
     
     private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(), 
                viewport.getMinY() + yProportion * viewport.getHeight());
    }
    private double clamp(double value, double min, double max) {

     if (value < min)
         return min;
     if (value > max)
         return max;
     return value;
    }
    private void paruostiScena() {
        toolbar.getItems().addAll(filtruotojas, prideti, arPrideda);
        toolbar.setPrefHeight(30);
        arPrideda.setSelected(true);
        salis.setPromptText("Valstybe");
        imone.setPromptText("Imone");
        kodas.setPromptText("Pasto kodas");
        produktai.setPromptText("Pagr. produktai");
        GridPane.setConstraints(salis, 0, 0);
        GridPane.setConstraints(imone, 1, 0);
        GridPane.setConstraints(kodas, 0, 2);
        GridPane.setConstraints(produktai, 0, 1, 2, 1);
        GridPane.setConstraints(sukurti, 1, 2);
        veliavosIvestis.getChildren().addAll(salis, imone, produktai, kodas, sukurti);
    }
    
   
    
    private void pakrautiIsDokumento(AnchorPane root, EventHandler<MouseEvent> antVeliavos, EventHandler<MouseEvent> palikoVeliava,
            EventHandler<MouseEvent> paspaudeAntVeliavos, Image veliavosPav) throws IOException, FileNotFoundException, ClassNotFoundException
    {
         veliavosPakrovimui = VeliavuBaze.uzkrauti(ivestiesFailoPavadinimas);
        //int veliavuSk = 0;
        
        for (Veliava vel : veliavosPakrovimui) {
            //System.out.println(++veliavuSk);
            Veliava nauja = new Veliava(vel, veliavosPav, vel.getXsaugojimui(), vel.getYsaugojimui());
            
            if (tinkaVeliava(nauja)) veliavos.add(nauja);
            counter++;
            root.getChildren().add(nauja);
            nauja.setOnMouseEntered(antVeliavos);
            nauja.setOnMouseExited(palikoVeliava);
            nauja.setOnMouseClicked(paspaudeAntVeliavos);
            //System.out.println(vel.toString() + " X,Y: " + vel.getX() + ", " + vel.getY());
            nauja.setX(nauja.getXsaugojimui());
            nauja.setY(nauja.getYsaugojimui());
        }
        
    }
    public boolean tinka(String str) {
        return  (str.equalsIgnoreCase(" ") || !str.isEmpty() || str.equalsIgnoreCase("null"));
    }
    
    public boolean tinkaVeliava(Veliava vel) {
        return tinka(vel.getImone()) && tinka(vel.getValstybe()) && tinka(vel.getKodas());
    }
    private void trintiVeliavas(ArrayList<Veliava> masyvas){
        System.out.println("Is pradziu veliavu buvo " + masyvas.size());
        for(Veliava vel : masyvas) {
            if (tinka(vel.getValstybe()) && tinka(vel.getKodas()) && tinka(vel.getImone())) {
                masyvas.remove(vel);
               
            }
        }
        System.out.println("Liko " + masyvas.size());
    }
    private void nustatytiLaukus(Veliava vel, TextField txt1, TextField txt2, TextField txt3, TextField txt4) {
        vel.setValstybe(txt1.getText());
        vel.setImone(txt2.getText());
        vel.setKodas(txt3.getText());
        vel.setProduktai(txt4.getText());
    }
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void atnaujintiVeliavuPozicijas(ArrayList<Veliava> masyvas, ImageView zemelapis) {
        Rectangle2D viewPort = zemelapis.getViewport();
        double minX = viewPort.getMinX(), minY = viewPort.getMinY(), zemelapioX = zemelapis.getImage().getWidth(); 
        double proporcija = zemelapioX / viewPort.getWidth();
        for(Veliava vel : masyvas) {
            double newX = (vel.getXsaugojimui() - minX) * proporcija, newY = (vel.getYsaugojimui() - minY) * proporcija;
            if (vel.getParent().contains(new Point2D(newX, newY))) {
                vel.setVisible(true);
                vel.setX(newX);
                vel.setY(newY);
            }
            else vel.setVisible(false);
        }
    }
    
}
