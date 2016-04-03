package org.example.androidsdk.letsgo.model;

public class Place {
    private String title, thumbnailUrl, location, eventId;
    private double latitude,longitude;
    private int price;
    private double rating;
    private boolean favourite;
    private boolean visibility;


    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEventId(String eventId){this.eventId = eventId; }

    public String getEventId(){return eventId; }

    public void setPrice(int price){this.price = price;}

    public int getPrice(){return price;}
    
    public void setFavourite(boolean favourite){this.favourite = favourite;}
    
    public boolean getFavourite(){return favourite;}

    public void setVisibility(boolean visibility){this.visibility = visibility;}

    public boolean getVisibility(){return visibility;}
    
    public void setLatitude(double latitude){this.latitude = latitude;}

    public double getLatitude(){return latitude;}

    public void setLongitude(double longitude){this.longitude = longitude;}

    public double getLongitude(){return longitude;}

}