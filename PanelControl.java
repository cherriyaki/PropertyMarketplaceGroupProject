import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;

import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.IsoChronology;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.stage.*;
/**
 * Write a description of class PanelControl here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class PanelControl extends Application
{
    // instance variables - replace the example below with your own
    private Scene currentScene;
    private Panel1 panel1;
    private Panel2 panel2;
    private Panel3 panel3;
    private Panel4 panel4;

    /**
     * Constructor for objects of class PanelControl
     */
    public PanelControl()
    {
        panel1 = new Panel1();
        panel2 = new Panel2(this);
        panel3 = new Panel3();
        panel4 = new Panel4();
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        currentScene = panel1.getScene(); //sets initial panel to panel1
        setListener(stage); //programs choice boxes
        setActionButtons(stage); // programs buttons
            
        stage.setTitle("Property Marketplace");
        stage.setScene(currentScene);
        
        //Show the Stage (window)
        stage.show();
    }
    
    private void toggleButtons()
    {
        int toPrice;
        int fromPrice;
        LocalDate toDate;
        LocalDate fromDate;
        if (currentScene.equals(panel1.getScene())) {//checks current panel to get that instance of buttons
            toPrice = panel1.getToPrice();
            fromPrice = panel1.getFromPrice();
            toDate =panel1.getPickerOut().getValue();
            fromDate =panel1.getPickerIn().getValue();// gets values from panel1
            if (toPrice > fromPrice && fromDate != null && toDate != null && fromDate.isBefore(toDate)) {//checks for valid dates and prices
                panel1.getBackButton().setDisable(false);
                panel1.getForwardButton().setDisable(false);
                panel1.getErrorMessage().setVisible(false);// enables buttons and removes error message
            }
            else {
                panel1.getBackButton().setDisable(true);
                panel1.getForwardButton().setDisable(true);
                panel1.getErrorMessage().setVisible(true);
            }
        }
        else if (currentScene.equals(panel2.getScene())) {
            toPrice = panel2.getToPrice();
            fromPrice = panel2.getFromPrice();
            toDate =panel2.getPickerOut().getValue();
            fromDate =panel2.getPickerIn().getValue();
            if (toPrice > fromPrice && fromDate.isBefore(toDate)) {
                panel2.getBackButton().setDisable(false);
                panel2.getForwardButton().setDisable(false);
                panel2.getErrorMessage().setVisible(false);
            }
            else {
                panel2.getBackButton().setDisable(true);
                panel2.getForwardButton().setDisable(true);
                panel2.getErrorMessage().setVisible(true);
            }
        }
    }
    
    private Scene nextScene()//changes to next panel
    {
        if (currentScene.equals(panel1.getScene())) {
            currentScene = panel2.getScene();
        }
        else if (currentScene.equals(panel2.getScene())) {
            currentScene = panel3.getScene();
        }
        else if (currentScene.equals(panel3.getScene())) {
            currentScene = panel4.getScene();
        }
        else if (currentScene.equals(panel4.getScene())) {
            currentScene = panel1.getScene();
        }
        return currentScene;
    }
    
    private Scene previousScene()//changes to previous panel
    {
        if (currentScene.equals(panel1.getScene())) {
            currentScene = panel4.getScene();
        }
        else if (currentScene.equals(panel2.getScene())) {
            currentScene = panel1.getScene();
        }
        else if (currentScene.equals(panel3.getScene())) {
            currentScene = panel2.getScene();
        }
        else if (currentScene.equals(panel4.getScene())) {
            currentScene = panel3.getScene();
        }
        return currentScene;
    }
    
    private void setActionButtons(Stage stage)//programs forwards and back buttons on all panels
    {
        panel1.getBackButton().setOnAction(value -> 
        {
           stage.setScene(previousScene());
        }
        );
        
        panel1.getForwardButton().setOnAction(value -> 
        {
           stage.setScene(nextScene());
        }
        );
        
        panel2.getBackButton().setOnAction(value -> 
        {
           stage.setScene(previousScene());
        }
        );
        
        panel2.getForwardButton().setOnAction(value -> 
        {
           stage.setScene(nextScene());
        }
        );
        
        panel3.getBackButton().setOnAction(value -> 
        {
           stage.setScene(previousScene());
        }
        );
        
        panel3.getForwardButton().setOnAction(value -> 
        {
           stage.setScene(nextScene());
        }
        );
        
        panel4.getBackButton().setOnAction(value -> 
        {
           stage.setScene(previousScene());
        }
        );
        
        panel4.getForwardButton().setOnAction(value -> 
        {
           stage.setScene(nextScene());
        }
        );
    }
    
    private void setListener(Stage stage)//programs choice boxes
    {
        panel1.getPriceToButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel2.setPriceTo(newValue);//give change in data to panel2
                panel2.updateToMap(newValue);
            }
            );
        
        panel1.getPriceFromButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel2.setPriceFrom(newValue);
                panel2.updateFromMap(newValue);
            }
            );
            
        panel2.getPriceToButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel1.setPriceTo(newValue);
                panel2.updateToMap(newValue);
            }
            );
        panel2.getPriceFromButton().getSelectionModel().selectedItemProperty().addListener( 
            (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
            {
                toggleButtons();
                panel1.setPriceFrom(newValue);
                panel2.updateFromMap(newValue);
            }
            );
            
        panel1.getPickerIn().valueProperty().addListener( 
            (observable,  oldValue,  newValue) ->
            {
                toggleButtons();
                panel2.setDateIn(newValue);// gives date to panel 2
            }
            );
        
        panel1.getPickerOut().valueProperty().addListener( 
            (observable,  oldValue,  newValue) ->
            {
                toggleButtons();
                panel2.setDateOut(newValue);
            }
            );
            
        panel2.getPickerIn().valueProperty().addListener( 
            (observable,  oldValue,  newValue) ->
            {
                toggleButtons();
                panel1.setDateIn(newValue);
            }
            );
        panel2.getPickerOut().valueProperty().addListener( 
            (observable,  oldValue,  newValue) ->
            {
                toggleButtons();
                panel1.setDateOut(newValue);
            }
            );
    }
    
    public boolean addToCompare(AirbnbListing boxToCompare)
    {
        // Forwards boxToCompare to panel4
        return panel4.addToCompare(boxToCompare);
    }
    
    public boolean removeFromCompare(AirbnbListing boxToCompare)
    {
        // Forwards boxToCompare to panel4
        return panel4.removeFromCompare(boxToCompare);
    }
}