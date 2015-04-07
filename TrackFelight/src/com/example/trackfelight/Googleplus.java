package com.example.trackfelight;


import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class Googleplus extends ActionBarActivity implements ConnectionCallbacks, OnConnectionFailedListener {
	
	
	SignInButton googleplus;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;


	/* Request code used to invoke sign in user interactions. */
	  private static final int RC_SIGN_IN = 0;

	  /* Client used to interact with Google APIs. */
	  public GoogleApiClient mGoogleApiClient;

	  /* A flag indicating that a PendingIntent is in progress and prevents
	   * us from starting further intents.
	   */
	  private boolean mIntentInProgress;

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	   
	    googleplus=(SignInButton)findViewById(R.id.sign_in_button);
	    googleplus.setOnClickListener((OnClickListener) this);
	    
	    
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks((com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks) this)
	        .addOnConnectionFailedListener((com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) this)
	        .addApi(Plus.API)
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .build();
	    
	   
	  }
	  
	  
	  

	  protected void onStart() {
	    super.onStart();
	    mGoogleApiClient.connect();
	  }

	  protected void onStop() {
	    super.onStop();

	    if (mGoogleApiClient.isConnected()) {
	      mGoogleApiClient.disconnect();
	    }
	  }
	  
	  public void onConnectionFailed(ConnectionResult result) {
		  if (!mIntentInProgress) {
		    // Store the ConnectionResult so that we can use it later when the user clicks
		    // 'sign-in'.
		    mConnectionResult = result;

		    if (mSignInClicked) {
		      // The user has already clicked 'sign-in' so we attempt to resolve all
		      // errors until the user is signed in, or they cancel.
		      resolveSignInError();
		    }
		  }
		}

	  public void  OnClick(View view)
	  {
		  
		  if (view.getId() == R.id.sign_in_button
				    && !mGoogleApiClient.isConnecting()) {
				    mSignInClicked = true;
				    resolveSignInError();
				  }
		  
	  }
	  
	  private void resolveSignInError() {
		  if (mConnectionResult.hasResolution()) {
		    try {
		      mIntentInProgress = true;
		      startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
		          RC_SIGN_IN, null, 0, 0, 0);
		    } catch (SendIntentException e) {
		      // The intent was canceled before it was sent.  Return to the default
		      // state and attempt to connect to get an updated ConnectionResult.
		      mIntentInProgress = false;
		      mGoogleApiClient.connect();
		    }
		  }
		}
	  
	  public void onConnected(Bundle connectionHint) {
		  mSignInClicked = false;
		  Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		}
		
		protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
			  if (requestCode == RC_SIGN_IN) {
			    if (responseCode != RESULT_OK) {
			      mSignInClicked = false;
			    }

			    mIntentInProgress = false;

			    if (!mGoogleApiClient.isConnecting()) {
			      mGoogleApiClient.connect();
			    }
			  }
			}
		public void onConnectionSuspended(int cause) {
			  mGoogleApiClient.connect();
			}

		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}
		
	
}
