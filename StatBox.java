import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * StatBox
 * Shows a single stat
 * Has 2 buttons for selecting a new stat
 * Mustn't show the same stat as another statBox in the same panel
 */
public class StatBox
{
    private Pane myPane = new Pane();
    private static final int PREF_SIZE[] = { 350, 200 }; // Size of StatBox (pixels)
    private Panel3 panel3;

    // Button variables
    private Button prevStatButton = new Button("<");
    private Button nextStatButton = new Button(">");

    // Info VBox
    private GridPane infoBox = new GridPane();
    private Label selectedStat = new Label("Selected Stat Label Placeholder");
    private Label statValue = new Label("Statistic Placeholder");

    private int currentStat; // Current stat this box shows

    /**
     * Constructor for objects of class StatBox
     */
    public StatBox(Panel3 panel3, int currentStat)
    {
        this.panel3 = panel3;
        this.currentStat = currentStat;

        myPane.setPrefSize(StatBox.PREF_SIZE[0], StatBox.PREF_SIZE[1]); // Set size of statbox
        myPane.setStyle("-fx-background-color: whitesmoke; -fx-background-radius: 25;" + 
            "-fx-border-color: black; -fx-border-radius:25"); // Format statbox
        addObjects(); // Add the buttons and info labels
        addButtonActionListeners(); // Add button action event listeners
        myPane.getChildren().addAll(prevStatButton, nextStatButton, infoBox); // Add the elements to the main pane
        updateInfoDisplay(); // Update the info displayed
    }
    
    /**
     * Creates and formats all the elements of the statbox
     *  - Prev and Next stat button
     *  - Statistic description label and value label
     */
    private void addObjects()
    {
        // Add prev stat button
        prevStatButton.relocate( 25, 25);
        prevStatButton.setPrefSize( 50 , 150 );
        prevStatButton.setStyle("-fx-background-radius: 25");

        // Add next stat button
        nextStatButton.relocate( 275 , 25);
        nextStatButton.setPrefSize( 50 , 150 );
        nextStatButton.setStyle("-fx-background-radius: 25");
        
        // Add central info
        infoBox.relocate( 100, 0 );
        infoBox.setPrefSize(150, 200 );
        
        // Format Stat description label
        selectedStat.setWrapText(true);
        selectedStat.setPrefWidth(150);
        selectedStat.setMaxWidth(150);
        selectedStat.setMaxHeight(100);
        selectedStat.setTextAlignment(TextAlignment.CENTER);
        selectedStat.setAlignment(javafx.geometry.Pos.CENTER);
        selectedStat.setStyle("-fx-font-weight: bolder; -fx-font-size: 16");
        
        // Format Stat value label
        statValue.setWrapText(true);
        statValue.setPrefWidth(150);
        statValue.setMaxWidth(150);
        statValue.setMaxHeight(100);
        statValue.setTextAlignment(TextAlignment.CENTER);
        statValue.setAlignment(javafx.geometry.Pos.CENTER);
        statValue.setStyle("-fx-font-weight: bold; -fx-font-size: 16");

        // Add the stats to the info pane
        infoBox.add(selectedStat, 0, 0);
        infoBox.add(statValue, 0, 1);
        infoBox.setStyle("-fx-alignment: center; -fx-vgap: 20;");
    }

    /**
     * Adds the listeners to next and prev stat button
     */
    private void addButtonActionListeners()
    {
        nextStatButton.setOnAction(value -> { nextStat(); });
        prevStatButton.setOnAction(value -> { prevStat(); });
    }

    /**
     * Refreshes the displayed stat
     */
    private void updateInfoDisplay(){
        selectedStat.setText(panel3.getStatDescription(currentStat));
        statValue.setText(panel3.getStatValue(currentStat));
    }

    /**
     * Function activated when next stat button clicked
     * Changes StatBox to display next stat
     */
    private void nextStat(){
        currentStat = panel3.getNextStat(currentStat);
        updateInfoDisplay();
    }

    /**
     * Function activated when prev stat button pressed
     * Changes StatBox to display prev stat
     */
    private void prevStat(){
        currentStat = panel3.getPrevStat(currentStat);
        updateInfoDisplay();
    }
    
    // Getters
    public int getCurrentStat(){
        return currentStat;
    }

    public Pane getPane(){
        return myPane;
    }
}