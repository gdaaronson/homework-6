package edu.lcark.homework6;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Greg on 4/3/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, DialogueFragment.MapListener {

    private MapView mapView;
    private GoogleMap map;
    private LatLng mLatLng;
    private DialogueFragment temp;
    public static final String TAG = "TAG, You are it";
    private SQLHelperLocation sqlHelper;

    private String mUser;

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public static MapFragment newInstance(String user){
        MapFragment frag = new MapFragment();
        Bundle args = new Bundle();
        frag.setUser(user);
        args.putString(LoginFragment.USER, user);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) rootView.findViewById(R.id.fragment_map_mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mLatLng = latLng;
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DialogueFragment df = DialogueFragment.newInstance(mLatLng.toString(), MapFragment.this);
                temp = df;
                df.show(fm, "fragment_edit_name");
            }
        });
        map.setOnMarkerClickListener(this);
        sqlHelper = SQLHelperLocation.getInstance(getContext());
        Location l;
        Cursor cursor = sqlHelper.getReadableDatabase().rawQuery("SELECT * FROM " + Location.TABLE_NAME, null);
        if (cursor.moveToFirst()){
            do {
                if (mUser.equals(cursor.getString(cursor.getColumnIndex(Location.COL_USER)))){
                    Log.d(MapFragment.class.getSimpleName(), "The name has flags");
                    LatLng latLng = new LatLng(cursor.getDouble(cursor.getColumnIndex(Location.COL_LAT)),cursor.getDouble(cursor.getColumnIndex(Location.COL_LONG)));
                    map.addMarker(new MarkerOptions().position(latLng)
                            .title(cursor.getString(cursor.getColumnIndex(Location.COL_NAME)))
                            .snippet(cursor.getString(cursor.getColumnIndex(Location.COL_NOTES)))
                    );
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onOkayButtonPushed(String title, String snippet) {
        map.addMarker(new MarkerOptions().position(mLatLng).title(title).snippet(snippet));
        Location l = new Location(mLatLng.latitude, mLatLng.longitude, mUser);
        l.setName(title);
        l.setNote(snippet);
        sqlHelper.insertLocation(l);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0,0,0, R.string.log_off);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0){
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_framelayout, new LoginFragment());
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
