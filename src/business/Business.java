package business;

public class Business {

    //Instance variables
    private final String id;
    private final String name;
    private final String categories;
    private final double latitude;
    private final double longitude;

    //Constructor
    public Business(String id, String name, String categories, double latitude, double longitude){
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategories(){
        return categories;

    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
