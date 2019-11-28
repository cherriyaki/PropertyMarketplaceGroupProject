import java.util.*;

/**
 * Panel3Data
 *  Helper class for Panel3 that calculates the statistics to be shown in Panel3
 * @author (AirYeet)
 */
public class Panel3Data
{
    // The stat values and descriptions of the values to be displayed are stored in these lists
    private ArrayList<String> statDescriptions = new ArrayList<String>(); // List of the statistic descriptions
    private ArrayList<String> statValues = new ArrayList<String>(); // List of the statistic values to show
    
    private ArrayList<AirbnbListing> allTheProperties; // All the data that will be analysed to find the statistics
    
    // Data
    private int numProperties=0; // Total number of properties
    private double avgNumReviews=0; // Average review of all properties
    private int notPrivateRooms=0; // Number of Homes/Apartments
    private String boroughWithMostexpensiveProperty; // Borough with most expensive property
    AirbnbListing expensiveProperty = null; // Listing of most expensive property
    String mostExpensiveBorough = ""; // Borough most expensive by average property price
    private int highestHostListing = 0; // Number of properties whose host has listing above 50
    private int maxEntireHomePrice = 0; // Price of most expensive home
    private String maxEntireHomePropertyId = null; // most expensive home id
    private AirbnbDataLoader propertyList; // Data loader
    private String currentNeighbourhood; // Temp variable for looping
    private String boroughWithMaxReviewsPerMonth = ""; // Highest Avaliable Borough

    // the constructor of panel 3
    /**
     * This is the constructor of Panel 3 and it initliases all the array lists and fields.
     * It also loads the CSV file to be read using the AirbnbLoader.
     */
    public Panel3Data()
    {
        propertyList = new AirbnbDataLoader(); // Load data
        allTheProperties =propertyList.load();
        
        // Perform analysis
        countProperties();
        getMostExpensiveBorough();
        boroughWithMaxReviewsPerMonth();
        
        // Store output of analysis so it can be read out with getters
        createOutputLists();
    }
    
    private void createOutputLists(){
        // 1st 4 stats in the list are shown at start
        // Number of Properties
        statDescriptions.add("Number of Properties:");
        statValues.add(Integer.toString(numProperties));
        
        // Average review per property
        statDescriptions.add("Avg reviews per property:");
        statValues.add(Double.toString(avgNumReviews));
        
        // Number of properties that are a homes or apartments
        statDescriptions.add("Properties which are only homes and apartments:");
        statValues.add(Integer.toString(notPrivateRooms));
        
        // Most Expensive Borough
        statDescriptions.add("Most expensive borough:"); 
        statValues.add(mostExpensiveBorough);
        
        // Borough with the most expensive property
        statDescriptions.add("Borough with the most expensive property:");
        statValues.add(mostExpensiveBorough);
        
        // Most expensive entire home
        statDescriptions.add("Most expensive entire home:");
        statValues.add(maxEntireHomePropertyId);
        
        // Number of properties whose host listing is above 50
        statDescriptions.add("Number of properties with a host listing above 50:");
        statValues.add(Integer.toString(highestHostListing));
        
        //borough with highest average review
        statDescriptions.add("Higest available borough:");
        statValues.add(boroughWithMaxReviewsPerMonth);
    }

    /**
     * Gets the number of available properties.
     * @return the available properties as a int value.
     */
    public void countProperties(){
        long numberOfReviews=0; // Total number of reviews
        long maxPrice=0; // temp store for max price found so far
        
        // Reset output data stores to 0
        notPrivateRooms = 0;
        highestHostListing = 0;
        maxEntireHomePrice = 0;
        
        // Strings for string matching fields
        String privateRoom="Private room";
        String entireHome = "Entire home/apt";
        
        
        for(AirbnbListing property : allTheProperties){
            // count avaliable properties (that have avaliability >0 days out of 365)
            if(property.getAvailability365()>0){
                numProperties++;
            }
            //count the total number of reviews for all properties
            numberOfReviews+=property.getNumberOfReviews();    

            //count not private rooms
            if(!property.getRoom_type().equals(privateRoom))
            {
                notPrivateRooms++;
            }
            
            // find Most Expensive property. Price of property is minimum nights * price
            long currentAmount = property.getMinimumNights()*property.getPrice();
            if(currentAmount>maxPrice){
                maxPrice=currentAmount;
                expensiveProperty=property;
            }

            //highest host listing
            //returns the no. of properties with host listing count over 50.
            if(property.getCalculatedHostListingsCount() >50){
                highestHostListing++;
            }

            // Find the most expensive property that is an entire home and save its name
            if((property.getRoom_type()).equals(entireHome)){ // If entire home
                int currentEntireHomePrice = property.getPrice();
                if(currentEntireHomePrice>maxEntireHomePrice) // If most expensive seen so far
                {
                    maxEntireHomePrice=currentEntireHomePrice;
                    maxEntireHomePropertyId=property.getName(); 
                    // write a method to get this
                }
            } 
        }
        // review per property is calculated by dividing the total no of reviews by the property size.
        avgNumReviews=numberOfReviews/(allTheProperties.size());
        //gets the neighbourhood of the most expensive property.
        boroughWithMostexpensiveProperty = expensiveProperty.getNeighbourhood();
    }

    /**
     * Gets the name of the most expensive borough.
     * @return the most expensive borough.
     */
    public void getMostExpensiveBorough(){
        ArrayList<String> allBoroughs = new ArrayList<>(); // List of all boroughs that have properties
        HashMap<String,Integer> pricesOfBorough = new HashMap<>(); // Sum of price of all properties in borough
        HashMap<String,Integer> noOfPropertiesPerBorough = new HashMap<>(); // Number of properties in borough
        
        // Reset out data stores
        mostExpensiveBorough = "";
        int mostExpensiveBoroughAvgPrice = 0;
        
        // adds the neighbourhood to those boroughs without one after looping through all the properties.
        for (AirbnbListing listing : allTheProperties) {
            if (!allBoroughs.contains(listing.getNeighbourhood())) {
                allBoroughs.add(listing.getNeighbourhood());
            }
        }

        for (String borough : allBoroughs) {
            pricesOfBorough.put(borough,0);
            noOfPropertiesPerBorough.put(borough,0);
        }
        // loops through the second hashmap to check for a match.
        for (AirbnbListing listing : allTheProperties) {
            for (HashMap.Entry<String, Integer> entry : noOfPropertiesPerBorough.entrySet()) {
                Integer value = entry.getValue();
                if (entry.getKey().equals(listing.getNeighbourhood())) {
                    entry.setValue(value+1);
                }
            }
            // finds the overall price by multiplying the no of nights with the price.
            for (HashMap.Entry<String, Integer> entry : pricesOfBorough.entrySet()) {
                Integer value = entry.getValue();
                if (entry.getKey().equals(listing.getNeighbourhood())) {
                    entry.setValue(value+(listing.getPrice() * listing.getMinimumNights()));
                }
            }
        }
        // loops through the first hashmap.
        for (HashMap.Entry<String, Integer> entry : pricesOfBorough.entrySet()) {
            Integer value = entry.getValue();
            String key = entry.getKey();
            for (HashMap.Entry<String, Integer> entryTwo : noOfPropertiesPerBorough.entrySet()) {
                Integer valueTwo = entryTwo.getValue();
                // checks if the two keys match, if they do then the if statement is executed.
                if (key.equals(entryTwo.getKey())) {
                    entry.setValue(value / valueTwo);
                }
            }
            if (entry.getValue() > mostExpensiveBoroughAvgPrice) {
                mostExpensiveBoroughAvgPrice = entry.getValue();
                mostExpensiveBorough = entry.getKey();
            }
        }
    }

    /**
     * Gets the name of the borough that has the maximum average reviews per month.
     * @return the name of the borough with the maximum average reviews pers month.
     */
    public void boroughWithMaxReviewsPerMonth()
    {
        HashMap<String,Double> overallReviews = new HashMap<>(); // sum of reviews per month for all properties by borough
        HashMap<String, Integer> noOfPropertiesInBorough = new HashMap<>(); // Number of properties in borough
        int maxAvgPrice = 0; // temp store for finding max price
        double currentReview;
        double existingReview;
        double avgNumReviews;
        int existingCount;
        double maxavgNumReviews = 0; // data out store to 0
        double sumOfReviews;
        int countOfProperties;
        for(AirbnbListing property : allTheProperties){
            currentNeighbourhood = property.getNeighbourhood();
            currentReview = property.getReviewsPerMonth();

            // Sum up reviews per month in each borough
            // For each property, check if record has been made in map
            // If record has been made, update it to add extra reviews
            // If record hasn't been made, create field and set its value
            if (overallReviews.containsKey(currentNeighbourhood))
            {
                existingReview = overallReviews.get(currentNeighbourhood);
                existingReview = existingReview + currentReview;
                overallReviews.replace(currentNeighbourhood, existingReview);
            }
            else{
                overallReviews.put(currentNeighbourhood, currentReview);
            }
            // this checks whether the record is there or not, if it is not then it is added.
            if (noOfPropertiesInBorough.containsKey(currentNeighbourhood))
            {
                noOfPropertiesInBorough.put(currentNeighbourhood, noOfPropertiesInBorough.get(currentNeighbourhood)+1);
            }
            else
            {
                noOfPropertiesInBorough.put(currentNeighbourhood, 1);
            }
        }
        // loops through the hashmap of overall reviews
        for (HashMap.Entry<String, Double> entry : overallReviews.entrySet()){
            currentNeighbourhood = entry.getKey();
            sumOfReviews = entry.getValue();
            countOfProperties = noOfPropertiesInBorough.get(currentNeighbourhood);
            avgNumReviews = sumOfReviews/countOfProperties;
            if(avgNumReviews > maxavgNumReviews ){
                maxavgNumReviews = avgNumReviews;
                boroughWithMaxReviewsPerMonth = currentNeighbourhood;
            }
        }
    }

    /**
     * Gets the number of reviews per property.
     * @return the number of reviews as a double.
     */
    public double getavgNumReviews()
    {
        return avgNumReviews;
    }

    /**
     * Gets the number of properties that are not private rooms.
     * @return the number of properties which are not private rooms.
     */
    public int getnotPrivateRooms()
    {
        return notPrivateRooms;
    }

    /**
     * Gets the name of the borough that has the most expensive property.
     * @return the name of the borough with the most expensive property in it.
     */
    public String getboroughWithMostexpensiveProperty()
    {
        return boroughWithMostexpensiveProperty;
    }

    /**
     * Gets the number of properties that have a host listing above 50.
     * @return the number of properties with a host listing above 50.
     */
    public int gethighestHostListing()
    {
        return highestHostListing;
    }

    /**
     * Gets the ID of the most expensive entire home.
     * @return the ID of the most expensive entire home.
     */
    public String getMostExpensiveEntireHomePropertyId()
    {
        return maxEntireHomePropertyId;
    }
    
    /**
     * Get the number of statistics to be shown to the panel
     */
    public int getNumStats()
    {
        return statDescriptions.size();
    }
    
    /**
     * Gets the value of the stat
     * Different stats differentiated by array index 'stat'
     */
    public String getStatValue(int stat)
    {
        return statValues.get(stat);
    }
    
    /**
     * Gets the description of the stat
     * Different stats differentiated by array index 'stat'
     */
    public String getStatDescription(int stat)
    {
        return statDescriptions.get(stat);
    }

}
