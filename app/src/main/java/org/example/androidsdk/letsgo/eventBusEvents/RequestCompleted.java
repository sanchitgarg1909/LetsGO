package org.example.androidsdk.letsgo.eventBusEvents;


import org.example.androidsdk.letsgo.model.Place;

import java.util.ArrayList;

public class RequestCompleted {
    private static ArrayList<Place> arrayList = new ArrayList<>();
    private static String response="{}";
//    private ArrayList<String>
    public RequestCompleted(ArrayList<Place> arrayList){
        this.arrayList = arrayList;
    }
    public RequestCompleted(String response){
        this.response = response;
    }
    public ArrayList<Place> getArrayList(){
        return arrayList;
    }
    public String getResponse(){
        return response;
    }
}
