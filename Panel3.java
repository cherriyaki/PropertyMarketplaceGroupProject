import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.Font;
import java.util.*;

/**
 * Panel3
 * Panel to display overall statistics to the screen
 * This panel creates statBoxes in a grid, each StatBox displays a stat
 */
public class Panel3
{
    private ArrayList<StatBox> statBoxes = new ArrayList<StatBox>(); // List of the StatBoxes to be displayed
    private static final int NUM_BOXES = 4; // Number of stats that can be shown at once 
                                            //(Note, there must be more possible stats to show than number of boxes or will get index out of range error)

    private BorderPane root = new BorderPane(); // Outer border pane
    private Scene panel3;
    private TilePane tilePane = new TilePane(); // Tile pane to tile the StatBoxes
    private AnchorPane borderAnchor = new AnchorPane(); // Anchor pane for forward and back buttons

    private Label title = new Label("Statistics Panel"); // Title fo panel

    // Forward back buttons
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");
    
    // Helper class to calculate the stats to be displayed
    private Panel3Data panel3Data = new Panel3Data();

    /**
     * Constructor for objects of class Panel3
     */
    public Panel3()
    {
        // Create forward nad back buttons
        borderAnchor.setMinSize(800, 55);
        borderAnchor.getChildren().addAll(backButton, forwardButton);
        borderAnchor.setBottomAnchor(backButton, 15.0);
        borderAnchor.setLeftAnchor(backButton, 50.0);
        borderAnchor.setBottomAnchor(forwardButton, 15.0);
        borderAnchor.setRightAnchor(forwardButton, 50.0);
        
        // Create the StatBoxes and add them to tilePane
        createStatBoxes();
        
        // Create title (and format)
        title.setFont(new Font("Helvetica", 24));
        root.setAlignment(title, javafx.geometry.Pos.CENTER);
        root.setMargin(title, new Insets(0, 0, 0, 10));
        title.setStyle("-fx-font-weight: bold");
        
        // Add main parts to the BorderPanel (base panel of window)
        root.setBottom(borderAnchor);
        root.setCenter(tilePane);
        root.setTop(title);

        panel3 = new Scene(root, 800, 500);
        
        // Set css file
        panel3.getStylesheets().add("panel3.css");
    }

    /**
     * Create the stat boxes (1 for each stat) that will be drawn to screen
     * Function creates the stat boxes and adds them to a list as well as their graphics to the tilePane
     */
    private void createStatBoxes()
    {
        // Create number of stat boxes required
        for (int i = 0; i < Panel3.NUM_BOXES; i++) {
            // Create Stat box
            StatBox newStatBox = new StatBox(this, i);
            statBoxes.add(newStatBox);

            // Add Stat box to tilePane
            tilePane.getChildren().add(newStatBox.getPane());
            tilePane.setAlignment(javafx.geometry.Pos.CENTER);
            tilePane.setStyle("-fx-hgap: 5; -fx-vgap: 5");
        }
    }

    /**
     * Returns the next stat to be shown, given that two stats can't be repeated
     */
    public int getNextStat(int currentStat){
        int nextStat = (currentStat+1)%panel3Data.getNumStats(); // Get next stat with modulo arithmetic to loop back to 1st stat after last stat
        while (isAlreadySelected(nextStat)) // Keep looking for stats till one that isn't being shown is found
        {
            nextStat = (nextStat+1)%panel3Data.getNumStats();
        }
        return nextStat;
    }
    
    /**
     * Returns the previous stat to be shown, given that two stats can't be repeated
     */
    public int getPrevStat(int currentStat){
        // Modulo arithmetic to loop to last stat after 1st stat
        int prevStat = (currentStat-1)%panel3Data.getNumStats();
        if (prevStat<0) {
            prevStat = panel3Data.getNumStats()-1; 
        }
        while (isAlreadySelected(prevStat))// Keep looking for stats till one that isn't being shown is found
        {
            // Modulo arithmetic
            prevStat = (prevStat-1)%panel3Data.getNumStats();
            if (prevStat<0) { 
                prevStat = panel3Data.getNumStats()-1; 
            }
        }
        return prevStat;
    }
    
    /**
     * Returns: true if the stat is already being displayed
     *          false if the stat is not being displayed
     */
    private boolean isAlreadySelected(int stat){
        boolean isAlreadySelected = false;
        for (int i = 0; i < statBoxes.size(); i++){ // Check each statbox to see if it's displaying a certain stat
            if (statBoxes.get(i).getCurrentStat() == stat){
                isAlreadySelected = true;
                break;
            }
        }
        return isAlreadySelected;
    }
    
    // Getters
    public Scene getScene()
    {
        return panel3;
    }

    public Button getBackButton()
    {
        return backButton;
    }

    public Button getForwardButton()
    {
        return forwardButton;
    }

    public String getStatDescription(int stat)
    {
        return panel3Data.getStatDescription(stat);
    }

    public String getStatValue(int stat)
    {
        return panel3Data.getStatValue(stat);
    }
}