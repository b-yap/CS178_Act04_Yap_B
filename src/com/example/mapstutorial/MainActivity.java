package com.example.mapstutorial;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements OnMapClickListener
{
  static final LatLng bronzeHorseman = new LatLng(59.9366, 30.3022);
  static final LatLng USCTC = new LatLng(10.35410, 123.91145);
  static final LatLng USCMC = new LatLng(10.30046, 123.88822);

  private int checker = 0;
  private int maximum = 3;
  private GoogleMap map;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
	  super.onCreate(savedInstanceState);
    
	  setContentView(R.layout.activity_main);
    
	    if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == ConnectionResult.SUCCESS)
	    {
	    	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	    
	    	if(a != null)
	    	{
	    		Marker usctc = map.addMarker(new MarkerOptions().position(USCTC).title("USC-TC"));
	    		Marker uscmain = map.addMarker(new MarkerOptions().position(USCMC).title("USC-MAIN"));
	    		Marker bronze = map.addMarker(new MarkerOptions().position(bronzeHorseman).title("Peter the Great in St. Petersburg"));
	    	    	
	    		map.setOnMapClickListener(this);
	    		map.animateCamera(CameraUpdateFactory.newLatLngZoom(USCTC, 15), 2000, null);
	    	}
	    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) 
  {
    getMenuInflater().inflate(R.menu.activity_main, menu);
    return true;
  }

@Override
public void onMapClick(LatLng point) {
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int check) {
	        switch (check)
	        {
	        	case DialogInterface.BUTTON_POSITIVE:
	            
	        	switch(checker)
	            {
		            case 0:	map.animateCamera(CameraUpdateFactory.newLatLngZoom(USCTC, 15), 2000, null); 
		            	break;
		            case 1:	map.animateCamera(CameraUpdateFactory.newLatLngZoom(USCMC, 15), 2000, null);
		            	break;
		            case 2:	map.animateCamera(CameraUpdateFactory.newLatLngZoom(bronzeHorseman, 15), 2000, null); 
	            }
	            
	            checker = (checker + 1) % maximum;
	            break;

	        	case DialogInterface.BUTTON_NEGATIVE:
	            break;
	            
	            default: break;
	        }
	    }
	};

	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Go to the next destination?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();	
}

} 
