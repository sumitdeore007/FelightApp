package com.example.trackfelight;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FelightActivity extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener {

	GoogleMap map;
	LatLng felight, saddr, mylocation;
	GoogleApiClient mGoogleApiClient;
	Location mLastLocation;
	Location myLocation;
	double currentLat;
	double currentLng;

	private com.facebook.widget.LoginButton logInWithFacebookBTN;
	private com.facebook.Session.StatusCallback statusCallback;
	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_felight);

		logInWithFacebookBTN = (com.facebook.widget.LoginButton) findViewById(R.id.logInWithFacebookBTN);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setMyLocationEnabled(true);
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		myLocation = map.getMyLocation();
		felight = new LatLng(12.916498, 77.601352);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(felight, 15));
		BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.felight2);
		map.addMarker(new MarkerOptions().position(felight).title("Felight")
				.snippet("No.1 Training and Placement Institute").icon(icon));
	           // mMarker = googleMap.addMarker(markerOptions);
		
		
		
		statusCallback = new com.facebook.Session.StatusCallback() {

			@Override
			public void call(com.facebook.Session session, SessionState state,
					Exception exception) {
				sessionStatusChanged(session, state, exception);

			}
		};

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		logInWithFacebookBTN.setSessionStatusCallback(statusCallback);

		/*
		 * LocationManager locationManager = (LocationManager)
		 * getSystemService(LOCATION_SERVICE); Criteria criteria = new
		 * Criteria(); String bestProvider =
		 * locationManager.getBestProvider(criteria, true); Location location =
		 * LocationServices.FusedLocationApi.getLastLocation( mGoogleApiClient);
		 */
		// Log.i("lat", currentLat+"");
		// buildGoogleApiClient();
		/*
		 * if(myLocation != null){ mylocation=new
		 * LatLng(myLocation.getLatitude(), myLocation.getLongitude());
		 * map.addMarker(new
		 * MarkerOptions().position(mylocation).title("mylocation"
		 * ).snippet("This is me sumit")); }else { Log.i("Location" ,
		 * "Location not available"); }
		 */

		Button b1 = (Button) findViewById(R.id.btMap);
		b1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri gmmIntentUri = Uri
						.parse("google.navigation:q=12.916498,77.601352");
				Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
				mapIntent.setPackage("com.google.android.apps.maps");
				startActivity(mapIntent);
			}
		});
		
		
		Button aboutFelight = (Button) findViewById(R.id.AboutFelight);
		aboutFelight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent aboutFelight = new Intent(FelightActivity.this, AboutFlight.class);
				aboutFelight.putExtra("url", "http://www.felightlabs.com");
				startActivity(aboutFelight);
			}
		});

		Button contactUs = (Button) findViewById(R.id.btContact);
		contactUs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent contact = new Intent(FelightActivity.this, AboutFlight.class);
				contact.putExtra("url", "http://www.felight.com/contactus/");
				startActivity(contact);
			}
		});
		
		Button course = (Button) findViewById(R.id.CoursesOffered);
		course.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent course = new Intent(FelightActivity.this, AboutFlight.class);
				course.putExtra("url", "http://www.felight.com/courses-offered/");
				startActivity(course);
			}
		});
		Button loginfelight = (Button) findViewById(R.id.loginfelight);
		loginfelight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loginfelight = new Intent(FelightActivity.this, AboutFlight.class);
				loginfelight.putExtra("url", "http://www.felight.com/login/");
				startActivity(loginfelight);
			}
		});
	}
	
	

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks((ConnectionCallbacks) this)
				.addOnConnectionFailedListener(
						(OnConnectionFailedListener) this)
				.addApi(LocationServices.API).build();
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {
			currentLat = mLastLocation.getLatitude();
			currentLng = mLastLocation.getLongitude();
		}
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	public void sessionStatusChanged(com.facebook.Session session,
			SessionState state, Exception exception) {

		if (session.isOpened()) {
			Log.d("test", "Log in sucess");
		} else {
			Log.d("test", "Log in failed");
		}

	}

	@Override
	protected void onPause() {
		uiHelper.onPause();
		super.onPause();
	}

	@Override
	protected void onStop() {
		uiHelper.onStop();
		super.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		uiHelper.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

}
