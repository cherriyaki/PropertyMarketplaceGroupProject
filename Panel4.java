import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import javafx.geometry.Insets;
import javafx.geometry.HPos;

import java.util.*;

/**
 * This class creates the gui for panel 4 and fills the grid with
 * necessary data. Panel 4 creates a grid for comparing up to 4 different
 * properties.
 */
public class Panel4
{
    private GridPane grid = new GridPane();
    private ScrollPane gridScroll = new ScrollPane();
    private BorderPane root = new BorderPane();
    private AnchorPane borderAnchor = new AnchorPane();
    private AnchorPane centreAnchor = new AnchorPane();
    private Button backButton = new Button("  <  ");
    private Button forwardButton = new Button("  >  ");
    private List<AirbnbListing> propertyIDs = new ArrayList<>();
    private Scene panel4;
    private Label propertyName;
    private ImageView roomType;
    private Label price;
    private Label minNights;
    private Label numberOfReviews;
    private Label lastReview;
    private PieChart reviewsPerMonth;
    private Label hostListingCount;
    private PieChart annualAvailability;
    private Label noData = new Label("Please enter a property to compare");
    private Label nameLabel, roomTypeLabel, priceLabel, minNightsLabel,
    numberOfReviewsLabel, lastReviewLabel,reviewsPerMonthLabel, 
    hostListingCountLabel, annualAvailabilityLabel;

    /**
     * Constructor for objects of class Panel4
     */
    public Panel4()
    {
        //create grid labels
        nameLabel = new Label("PropertyName");
        roomTypeLabel = new Label("Room Type");
        priceLabel = new Label("Price");
        minNightsLabel = new Label("Minimum Nights");
        numberOfReviewsLabel = new Label("No. of Reviews");
        lastReviewLabel = new Label("Last Review");
        reviewsPerMonthLabel = new Label("Reviews/Month");
        hostListingCountLabel = new Label("Host Listings");
        annualAvailabilityLabel = new Label("Annual Availability");

        //set label font
        nameLabel.setFont(new Font("Helvetica", 14));
        roomTypeLabel.setFont(new Font("Helvetica", 14));
        priceLabel.setFont(new Font("Helvetica", 14));
        minNightsLabel.setFont(new Font("Helvetica", 14));
        numberOfReviewsLabel.setFont(new Font("Helvetica", 14));
        lastReviewLabel.setFont(new Font("Helvetica", 14));
        reviewsPerMonthLabel.setFont(new Font("Helvetica", 14));
        hostListingCountLabel.setFont(new Font("Helvetica", 14));
        annualAvailabilityLabel.setFont(new Font("Helvetica", 14));

        //edit grid padding and gaps
        grid.setPadding(new Insets(20));
        grid.setVgap(15);

        //fill gridScroll with panel and program scroll bars
        gridScroll.setContent(grid);
        gridScroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        gridScroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        //arrange GUI for botton bar
        borderAnchor.setMinSize(800, 55);
        borderAnchor.getChildren().addAll(backButton, forwardButton);
        borderAnchor.setBottomAnchor(backButton, 15.0);
        borderAnchor.setLeftAnchor(backButton, 50.0);
        borderAnchor.setBottomAnchor(forwardButton, 15.0);
        borderAnchor.setRightAnchor(forwardButton, 50.0);

        centreAnchor.getChildren().addAll(noData);
        centreAnchor.setLeftAnchor(noData, 280.0);
        centreAnchor.setTopAnchor(noData, 230.0);

        root.setCenter(noData);
        root.setBottom(borderAnchor);
        panel4 = new Scene(root, 800, 500);
        panel4.getStylesheets().add("panel4noData.css");
    }

    /**
     * checks the list of properties and adds statistics to the grid from
     * them
     */
    
    private void fillGrid()
    {
        if (propertyIDs.size() == 0) {
            grid.getChildren().clear();//empty graph
            noData.setVisible(true);//show error message
            root.setCenter(noData);
            panel4.getStylesheets().add("panel4noData.css");
        }
        else {
            noData.setVisible(false);//hide error message
            grid.getChildren().clear();
            setEmptyGrid();
            root.setCenter(gridScroll);//put in the scroll pane with the grid pane
            panel4.getStylesheets().add("panel4.css");
            for (int i = 0 ; i < propertyIDs.size() ; i++) {//iterate through the list adding data to correct coordinates
                Panel4Data data = new Panel4Data(propertyIDs.get(i));
                assignParameters(data);
                grid.add(propertyName, i + 1, 0);
                grid.add(roomType, i + 1, 1);
                grid.add(price, i + 1, 2);
                grid.add(minNights, i + 1, 3);
                grid.add(numberOfReviews, i + 1, 4);
                grid.add(lastReview, i + 1, 5);
                grid.add(reviewsPerMonth, i + 1, 6);
                grid.add(hostListingCount, i + 1, 7);
                grid.add(annualAvailability, i + 1, 8);
                
                //centre all data
                GridPane.setHalignment(propertyName, HPos.CENTER);
                GridPane.setHalignment(roomType, HPos.CENTER);
                GridPane.setHalignment(price, HPos.CENTER);
                GridPane.setHalignment(minNights, HPos.CENTER);
                GridPane.setHalignment(numberOfReviews, HPos.CENTER);
                GridPane.setHalignment(lastReview, HPos.CENTER);
                GridPane.setHalignment(reviewsPerMonth, HPos.CENTER);
                GridPane.setHalignment(hostListingCount, HPos.CENTER);
                GridPane.setHalignment(annualAvailability, HPos.CENTER);
            }
        }
    }

    /**
     * assignfields their values 
     */
    private void assignParameters(Panel4Data data)
    {
        propertyName = data.getPropertyName();
        roomType = data.getRoomType();
        price = data.getPrice();
        minNights = data.getMinNights();
        numberOfReviews = data.getNumberOfReviews();
        lastReview = data.getLastReview();
        reviewsPerMonth = data.getReviewsPerMonth();
        hostListingCount = data.getHostListingsCount();
        annualAvailability = data.getAnnualAvailability();

        propertyName.setFont(new Font("Helvetica", 14));
        minNights.setFont(new Font("Helvetica", 14));
        numberOfReviews.setFont(new Font("Helvetica", 14));
        lastReview.setFont(new Font("Helvetica", 14));
        hostListingCount.setFont(new Font("Helvetica", 14));
    }

    public boolean addToCompare(AirbnbListing boxToCompare)
    {
        if (propertyIDs.size() < 4) {
            // Add listing box to be compared
            boolean returnBool = propertyIDs.add(boxToCompare);
            fillGrid();
            return returnBool;
        }
        else {
            return false;
        }
    }

    /**
     * initiate with correct row titles
     */
    private void setEmptyGrid()
    {
        grid.add(nameLabel, 0, 0);
        grid.add(roomTypeLabel, 0, 1);
        grid.add(priceLabel, 0, 2);
        grid.add(minNightsLabel, 0, 3);
        grid.add(numberOfReviewsLabel, 0, 4);
        grid.add(lastReviewLabel, 0, 5);
        grid.add(reviewsPerMonthLabel, 0, 6);
        grid.add(hostListingCountLabel, 0, 7);
        grid.add(annualAvailabilityLabel, 0, 8);
    }

    public boolean removeFromCompare(AirbnbListing boxToCompare)
    {
        // Add listing box to be compared
        boolean returnBool = propertyIDs.remove(boxToCompare);
        fillGrid();
        return returnBool;
    }

    public Scene getScene()
    {
        return panel4;
    }

    public Button getBackButton()
    {
        return backButton;
    }

    public Button getForwardButton()
    {
        return forwardButton;
    }
}
