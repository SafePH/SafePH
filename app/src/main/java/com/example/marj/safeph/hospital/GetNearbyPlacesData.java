package com.example.marj.safeph.hospital;

import android.os.AsyncTask;
import android.util.Log;

import com.example.marj.safeph.hotline.HotlineActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Marj on 4/2/2018.
 */

class GetNearbyPlacesData extends AsyncTask<Object,String,String>{
    String googlePlaceData;
    GoogleMap mMap;
    String url;

    private ArrayList<HospitalModel> hospitals = new ArrayList<>();
    HospitalManager hospitalManager;

    public GetNearbyPlacesData(HospitalManager hospitalManager) {
        this.hospitalManager = hospitalManager;
    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap)objects[0];
        url = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googlePlaceData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList = null;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList){
        for (int i=0; i < nearbyPlaceList.size(); i++){
            HashMap<String,String> googlePlace = nearbyPlaceList.get(i);

            Log.d("GetNearbyPlacesData","googlePlace ="+googlePlace);
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            hospitals.add(new HospitalModel(placeName,vicinity,lat,lng));
        }
        hospitalManager.setHospitals(hospitals);
    }
}
