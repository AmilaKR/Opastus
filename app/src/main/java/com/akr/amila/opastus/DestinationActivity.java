package com.akr.amila.opastus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.akr.amila.opastus.LoginActivity.myRef;


public class DestinationActivity extends AppCompatActivity implements OnMapReadyCallback {

    PlaceAutocompleteFragment autocompleteFragment;
    GoogleMap map;
    SupportMapFragment mapFragment;
    LatLng marker;
    String name;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        autocompleteFragment = (PlaceAutocompleteFragment)getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_des);
        mapFragment.getMapAsync(this);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("Place", "Place: " + place.getName());
                marker = place.getLatLng();
                name = place.getName().toString();
                setMarker();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error PSL", "An error occurred: " + status);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    private void setMarker(){
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(marker, 15);
        map.animateCamera(location);
        map.addMarker(new MarkerOptions().position(marker));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to add new destination?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myRef.child("dest/lat").setValue(marker.latitude);
                myRef.child("dest/lon").setValue(marker.longitude);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this.getBaseContext(),MainActivity.class);
        startActivity(intent);
    }

}
