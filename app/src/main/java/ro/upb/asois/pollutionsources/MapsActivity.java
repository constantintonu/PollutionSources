package ro.upb.asois.pollutionsources;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private ArrayList<PollutionSource> locations;
    private GoogleMap mMap;
    private double zommMargin = 9.75f;
    private double bucharestLat = 44.437646;
    private double bucharestLng = 26.101557;
    private LatLng bucharestLocation = new LatLng(bucharestLat, bucharestLng);


    private void getLocations() {
        this.locations = new ArrayList<>();

        locations.add(new PollutionSource("Henri Coandă International Airport (OTP)", "airport", 44.576334, 26.082758, "Bucuresti"));
        locations.add(new PollutionSource("CET Grozavesti", "power station", 44.440456, 26.063009, "Bucuresti"));
        locations.add(new PollutionSource("CET Sud", "power station", 44.404975, 26.156748, "Bucuresti"));
        locations.add(new PollutionSource("CET Progresu", "power station", 44.373388, 26.107266, "Bucuresti"));
        locations.add(new PollutionSource("Iridex Group Plastic", "fabric", 44.4839108, 26.1651879, "Bucuresti"));
        locations.add(new PollutionSource("Alpla Plastic", "fabric", 44.4545184, 26.2617499, "Bucuresti"));
        locations.add(new PollutionSource("Feronat Reciclare", "Recycling Center", 44.460836, 26.111914, "Bucuresti"));
        locations.add(new PollutionSource("Alba Balkan Recycling Militari", "Recycling Center", 44.432716, 26.041802, "Bucuresti"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getLocations();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

            private float currentZoom = -1;

            @Override
            public void onCameraChange(CameraPosition pos) {
                if (pos.zoom != currentZoom){

                    if(currentZoom < zommMargin && pos.zoom > zommMargin) { // if zoom increased above 9x
                        mMap.clear();
                        addLocationsByIndividual();
                    } else if(currentZoom >= zommMargin && pos.zoom <= zommMargin) { // if zoom dropped below 9x
                        mMap.clear();
                        addLocationsByCategory();
                    }

                    currentZoom = pos.zoom;
                    System.out.println(currentZoom);
                }
            }
        });

        // Add a marker in Sydney and move the camera
        addLocationsByCategory();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucharestLocation, (float)(zommMargin - 0.5f)));
       /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(marker.getTitle().contains(":")) {
                    Intent it = new Intent(getApplicationContext(), TypeInfo.class);
                    it.putExtra("info", marker.getTitle());

                    startActivity(it);
                }
                return true;
            }
        });*/
    }

    private void addLocationsByIndividual() {
        for(PollutionSource s : locations) {
            MarkerOptions mk = new MarkerOptions();
            mk = mk.position(new LatLng(s.lat, s.lng));
            mk = mk.title(s.name);
            mk = mk.snippet(s.type);
            mMap.addMarker(mk);
        }
    }

    private void addLocationsByCategory() {
        HashMap<String, Integer> categories = new HashMap<>();

        for(PollutionSource s : locations) {
            int lastVal;

            if(categories.containsKey(s.type)) {
                lastVal = categories.get(s.type);
            } else {
                lastVal = 0;
                categories.put(s.type, lastVal);
            }

            categories.put(s.type, lastVal + 1);
        }

        double relativeLng = bucharestLng - 2 * 0.04;
        for(Map.Entry<String, Integer> en : categories.entrySet()) {
            MarkerOptions mk = new MarkerOptions();
            mk = mk.position(new LatLng(bucharestLat, relativeLng));
            mk = mk.title(en.getKey() + " : " + en.getValue());
            mMap.addMarker(mk);

            relativeLng += 0.04f;
        }
    }
}
