package com.motivator.wecareyou;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.GeneralUtility;
import com.motivator.common.WebServices;


public class Registration extends Activity implements OnClickListener
{
	boolean isSignIn = true;
	TextView tvSignIn, tvSignUp;
	EditText edtUserName, edtEmail, edtPhone,edtPassword;
	Button btnSignUp, btnGoogle, btnFacebook;
	LinearLayout llSocial;
	String userName, userId="", emailId, phone,password;
	
	/*private static String APP_ID = "188618421483655";
	private Facebook facebook = new Facebook(APP_ID);;
	private AsyncFacebookRunner mAsyncRunner;*/
	
	private SharedPreferences mPrefs;
	
	//private static final int RC_SIGN_IN = 0;

	// Google client to communicate with Google
//	private GoogleApiClient mGoogleApiClient;
//
//	private boolean mIntentInProgress;
//	private boolean signedInUser;
//	private ConnectionResult mConnectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.registration);
        
        //mAsyncRunner = new AsyncFacebookRunner(facebook);
        
        tvSignIn = (TextView)findViewById(R.id.tv_choose_sign_in);
        tvSignUp = (TextView)findViewById(R.id.tv_choose_sign_up);
        edtUserName = (EditText)findViewById(R.id.edt_user_name);
        edtEmail = (EditText)findViewById(R.id.edt_email_id);
        edtPhone = (EditText)findViewById(R.id.edt_phone);
        edtPassword = (EditText)findViewById(R.id.edt_password);
        
        llSocial = (LinearLayout)findViewById(R.id.ll_social);
        btnSignUp = (Button)findViewById(R.id.btn_register);
        btnGoogle = (Button)findViewById(R.id.btn_google);
        btnFacebook = (Button)findViewById(R.id.btn_facebook_login);
        
        tvSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
        
       /* mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
        		.addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build())
        		.addScope(Plus.SCOPE_PLUS_LOGIN).build();*/
    	
    }

   
	private void signUPValidation() 
	{
		userName = edtUserName.getText().toString();
		emailId = edtEmail.getText().toString();
		phone = edtPhone.getText().toString();
		password = edtPassword.getText().toString();
		
		if(userName==null || userName.length()<=0)
			Toast.makeText(Registration.this, R.string.enter_user_name, 5).show();
		else if(emailId==null || emailId.length()<=0 || !(GeneralUtility.isValidEmail1(emailId)))
			Toast.makeText(Registration.this, R.string.enter_email, 5).show();
		else if(phone==null || phone.length()<=0)
			Toast.makeText(Registration.this, R.string.enter_phone, 5).show();
		else if(password==null || password.length()<=0)
			Toast.makeText(Registration.this, R.string.enter_password, 5).show();
		else
		{
			if(GeneralUtility.isNetworkAvailable(Registration.this))
		    	new SignUp().execute(WebServices.ACCESS_LOGIN,userName, userId, emailId, phone, password);
		    else
		    	GeneralUtility.PopUpInfo(Registration.this, R.string.network_failure, R.string.no_internet);
		}
		
	}
	
	
	private void registrationValidation() 
	{
		emailId = edtEmail.getText().toString();
		password = edtPassword.getText().toString();
		if(emailId==null || emailId.length()<=0 || !(GeneralUtility.isValidEmail1(emailId)))
			Toast.makeText(Registration.this, R.string.enter_email, 5).show();
		else if(password==null || password.length()<=0)
			Toast.makeText(Registration.this, R.string.enter_password, 5).show();
		else
		{
			if(GeneralUtility.isNetworkAvailable(Registration.this))
		    	new RegistrationCall().execute(WebServices.ACCESS_LOGIN, emailId,password);
		    else
		    	GeneralUtility.PopUpInfo(Registration.this, R.string.network_failure, R.string.no_internet);
		}
		
		
	}
	
/*   
	private void loginWithFacebook()
	{
		
		openActiveSession(this, true, new Session.StatusCallback(){
	
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened())
				{
					Request.newMeRequest(session,new Request.GraphUserCallback(){
						@Override
						public void onCompleted(GraphUser user,Response response) {
							//new CreateFaceBookLogin().execute(response);
							// parseUserFromFQLResponse(response);
						}
					}).executeAsync();
				}
			}
			
		},permissions);
		
		
		Session.openActiveSession(Registration.this, true, new Session.StatusCallback() {
			   
			// callback when session changes state
			public void call(Session session, SessionState state, Exception exception)
			{
				if (session.isOpened()) 
				{		         
					Request.newMeRequest(session, new Request.GraphUserCallback() {
						// callback after Graph API response with user object
			            @Override
			            public void onCompleted(GraphUser user, Response response)
			            {
			               if (user != null) 
			               {		            	   
			            	  // String json = response;
			   				try {
			   					// Facebook Profile JSON data
			   					//JSONObject profile = new JSONObject(json);
			   					
			   					
			   					GraphObject go  = response.getGraphObject();
			   					JSONObject  jso = go.getInnerJSONObject();
			   					JSONArray   arr = jso.getJSONArray( "data" );
			   					for ( int i = 0; i < ( arr.length() ); i++ )
			   					{
			   						JSONObject json_obj = arr.getJSONObject( i );
		
			   						String id     = json_obj.getString("uid");
			   						String name   = json_obj.getString("name" );
			   						String urlImg = json_obj.getString("pic_square");
	
			   						//...
	
			   					}
	
			   					// getting name of the user
	//		   					/////  id = profile.getString("id");//140229419660197
	//		   					  if (profile.toString().contains("email")) {
	//		   					/////	  fbemail = profile.getString("email");
	//		   						}
	//		   					            
	//		   					///////  first_name = profile.getString("first_name");
	//		   					//  gender = profile.getString("gender");
	////		   					  last_name = profile.getString("last_name");
	////		   					  link = profile.getString("link");
	////		   					  locale = profile.getString("locale");
	//		   				//////	  name = profile.getString("name");
	//		   					//  timezone = profile.getString("timezone");
	//		   					
	//		   					// String updated_time = profile.getString("updated_time");
	//		   					// String verified = profile.getString("verified");
	//		   					
	//		   					// String updated_time = profile.getString("updated_time");
	//		   					// String verified = profile.getString("verified");
	//		   					 
	////		   					 ClsGeneral.setPreferences(LoginHomeActivity.this, "f_name",first_name);
	////		   					 ClsGeneral.setPreferences(LoginHomeActivity.this, "email", fbemail);
	////		   					 
	//		   					 
	//		   					    ClsGeneral.setPreferences(LoginHomeActivity.this, "user_id", id);
	
			   					  runOnUiThread(new Runnable() {
	
			   						@Override
			   						public void run() {
			   							Intent j = new Intent(Registration.this, HomeActivity.class);
			   							startActivity(j);
			   							finish();
			   							
			   						}
	
			   					});
	
			   					
			   				} catch (JSONException e) {
			   					e.printStackTrace();
			   				}
			   				
			            	   
			               }
			            }
			         });
			      }
			   }
			});
	}*/



/**
 * Function to login into facebook
 * */
	/*
	@SuppressWarnings("deprecation")
	public void loginToFacebook() {

		 mPrefs = getPreferences(MODE_PRIVATE);
		
		// String access_token = mPrefs.getString("access_token", null);
		// long expires = mPrefs.getLong("access_expires", 0);
		//
		// if (access_token != null) {
		// facebook.setAccessToken(access_token);
		// if (facebook.isSessionValid()) {
		// // getProfileInformation();
		//
		// new LoginwithFb().execute(WebServices.login_with_fb);
		// }
		//
		// Log.d("FB Sessions", "" + facebook.isSessionValid());
		// }
		//
		// if (expires != 0) {
		// facebook.setAccessExpires(expires);
		// }

		// if (!facebook.isSessionValid()) {
		facebook.authorize(this, new String[] { "email", "publish_actions" },new DialogListener()
		{

			@Override
			public void onCancel() {
				// Function to handle cancel event
			}
			
			@Override
			public void onComplete(Bundle values) {
				// Function to handle complete event
				// Edit Preferences and update facebook acess_token
				
				  SharedPreferences.Editor editor = mPrefs.edit();
				  editor.putString("access_token",
				  facebook.getAccessToken());
				  editor.putLong("access_expires", facebook.getAccessExpires()); editor.commit();

				  getProfileInformation();
				
			}
			
			@Override
			public void onError(DialogError error) {
				// Function to handle error
			}
			
			@Override
			public void onFacebookError(FacebookError fberror) {
				// Function to handle Facebook errors
			}

		});
	}*/

	/**
	 * Get Profile information by making request to Facebook Graph API
	 * */
//	@SuppressWarnings("deprecation")
/*	public void getProfileInformation() {

		Bundle params = new Bundle();
		params.putString("fields", "id,name,email,gender,first_name");

		mAsyncRunner.request("me", params, new RequestListener() {

			@Override
			public void onComplete(String response, Object state) {
				Log.d("Profile", response);
				String json = response;
				try {
					// Facebook Profile JSON data
					JSONObject profile = new JSONObject(json);

					// getting name of the user
					userId = profile.getString("id");
					userName = profile.getString("first_name");
					String name = profile.getString("name");
					if (profile.toString().contains("email")) 
						emailId = profile.getString("email");
					
					//URL image_value = new URL("http://graph.facebook.com/"+userId+"/picture" );
					
					// gender = profile.getString("gender");
					// last_name = profile.getString("last_name");
					// link = profile.getString("link");
					// locale = profile.getString("locale");
					
					// timezone = profile.getString("timezone");

					// String updated_time = profile.getString("updated_time");
					// String verified = profile.getString("verified");

					// String updated_time = profile.getString("updated_time");
					// String verified = profile.getString("verified");

//					GeneralUtility.setPreferences(Registeration.this, "user_name", userName);
//					GeneralUtility.setPreferences(Registeration.this, "user_email", emailId);
//					GeneralUtility.setPreferences(Registeration.this, "user_id", userId);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {							
							new RegistrationCall().execute(WebServices.ACCESS_LOGIN, userName, emailId);
							
//							Intent i = new Intent(Registeration.this,HomeActivity.class);
//							startActivity(i);
//							finish();
						}

					});

				} catch (JSONException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onIOException(IOException e, Object state) {
			}

			@Override
			public void onFileNotFoundException(FileNotFoundException e,
					Object state) {
			}

			@Override
			public void onMalformedURLException(MalformedURLException e,
					Object state) {
			}

			@Override
			public void onFacebookError(FacebookError e, Object state) {
			}
		});
	}

	
	private void googlePlusLogin() {
		if (!mGoogleApiClient.isConnecting()) {
			signedInUser = true;
			resolveSignInError();
		}
	}
	
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}
	
	private void updateProfile(boolean isSignedIn) {
		if (isSignedIn) {
			
			new RegistrationCall().execute(WebServices.ACCESS_LOGIN,userName, emailId);
//			Intent i = new Intent(Registration.this,HomeActivity.class);
//			startActivity(i);
//			finish();

		} else {
			
		}
	}

	private void getGoogleProfileInfo() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
				userName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				emailId = Plus.AccountApi.getAccountName(mGoogleApiClient);

				//new LoadProfileImage(image).execute(personPhotoUrl);

				// update profile frame with new info about Google Account
				// profile
				updateProfile(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	

	class SignUp extends AsyncTask<String, Void, String>
	   {
		   String jResult;
		   ProgressDialog progressDialog;
		   ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		   
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = ProgressDialog.show(Registration.this, "Please wait...","");
				progressDialog.setCancelable(true);
			}
		@Override
		protected String doInBackground(String... params) {

			nameValuePairs.add(new BasicNameValuePair("action", "singupstandard"));
			nameValuePairs.add(new BasicNameValuePair("user_name", params[1]));
			nameValuePairs.add(new BasicNameValuePair("user_id", params[2]));
			nameValuePairs.add(new BasicNameValuePair("user_email_id", params[3]));
			nameValuePairs.add(new BasicNameValuePair("user_phone_num", params[4]));
			nameValuePairs.add(new BasicNameValuePair("user_login_type", "standard"));
			nameValuePairs.add(new BasicNameValuePair("user_password", params[5]));
			
			try {
				jResult = WebServices.httpCall(params[0], nameValuePairs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jResult;
		}
		@Override
		protected void onPostExecute(String jResponse) 
		{
			super.onPostExecute(jResponse);
			if(progressDialog!=null)
				progressDialog.dismiss();
			
			if(jResponse!=null && jResponse.length()>0)
			{
				try{
					JSONObject jObj = new JSONObject(jResponse);
					String isSuccess = jObj.getString("isSuccess");
					String successMsg = jObj.getString("success_msg");
					String result = jObj.getString("result");
					if(isSuccess.equalsIgnoreCase("true"))
					{
						userId = "";
						
//						GeneralUtility.setPreferences(Registration.this, "user_name", userName);
//						GeneralUtility.setPreferences(Registration.this, "user_email", emailId);
//						GeneralUtility.setPreferences(Registration.this, "user_id", userId);
						
						Toast.makeText(Registration.this, "Sign up Successful", 5).show();
//						Intent i = new Intent(Registration.this, MasterActivity.class);
//						startActivity(i);
						finish();
					}
					else
						Toast.makeText(Registration.this, successMsg, 5).show();
				}
				catch(JSONException e)
				{
					e.printStackTrace();
				}
			}

		}
		   
	   }
	   
	   
	   class RegistrationCall extends AsyncTask<String, Void, String>
	   {
		   ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		   String jResult;
		   ProgressDialog progressDialog;
		   String password;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = ProgressDialog.show(Registration.this, "Please wait...","");
				progressDialog.setCancelable(true);
			}
		@Override
		protected String doInBackground(String... params) {
			
			nameValuePairs.add(new BasicNameValuePair("action", "singupsocial"));
			nameValuePairs.add(new BasicNameValuePair("user_name", params[1]));
			nameValuePairs.add(new BasicNameValuePair("user_email_id", params[2]));
			nameValuePairs.add(new BasicNameValuePair("user_login_type", "social"));
			
			try {
				jResult = WebServices.httpCall(params[0], nameValuePairs);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jResult;
		}
		
		@Override
		protected void onPostExecute(String jsonResponse) 
		{
			super.onPostExecute(jsonResponse);
			
			if(progressDialog!=null)
				progressDialog.dismiss();
			
			try{
				if(jsonResponse!=null && jsonResponse.length()>0)
				{
					JSONObject jObj = new JSONObject(jsonResponse);
					String isSuccess = jObj.getString("isSuccess");
					String successMsg = jObj.getString("success_msg");
					
					if(isSuccess.equalsIgnoreCase("true"))
					{
						/*JSONArray jarr = jObj.getJSONArray("result");
						for(int i=0; i<jarr.length(); i++)
						{
							JSONObject json_obj = jarr.getJSONObject(i);
							userId = json_obj.getString("user_id");
							userName = json_obj.getString("user_name");
							emailId = json_obj.getString("user_email_id");
							String userLoginType = json_obj.getString("user_login_type");
							String userImage = json_obj.getString("user_image");
						}*/
					
//						GeneralUtility.setPreferences(Registration.this, "user_name", userName);
//						GeneralUtility.setPreferences(Registration.this, "user_email", emailId);
//						GeneralUtility.setPreferences(Registration.this, "user_id", userId);
						
//						Intent i = new Intent(Registration.this, MasterActivity.class);
//						startActivity(i);
						Toast.makeText(Registration.this, "Sign In Successful", 5).show();
						finish();
					}
					else
						Toast.makeText(Registration.this, successMsg, 5).show();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			

		}
		   
	   }
	   
	   
	///////	
	/*protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}
		
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}*/
/*	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this, 0).show();
			return;
		}

		if (!mIntentInProgress) {
			// store mConnectionResult
			mConnectionResult = result;

			if (signedInUser) {
				resolveSignInError();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		signedInUser = false;
		Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
		getGoogleProfileInfo();
	}


	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
		updateProfile(false);
	}*/
	

@Override
public void onClick(View v) 
{
	switch (v.getId()) 
	{
	
	case R.id.tv_choose_sign_in:
		isSignIn = true;
		
		edtEmail.setText("");
		edtUserName.setText("");		
		
		edtEmail.setVisibility(View.VISIBLE);
		edtPassword.setVisibility(View.VISIBLE);
		
		edtUserName.setVisibility(View.GONE);
		edtPhone.setVisibility(View.GONE);
		
		btnSignUp.setText("SIGN IN");
		tvSignIn.setTextColor(Color.parseColor("#4da6ff"));
		tvSignUp.setTextColor(Color.parseColor("#000000"));
		
		break;
	case R.id.tv_choose_sign_up:
		isSignIn = false;
		
		edtUserName.setText("");
		edtEmail.setText("");
		edtPhone.setText("");
		edtPassword.setText("");		
		
		edtUserName.setVisibility(View.VISIBLE);
		edtEmail.setVisibility(View.VISIBLE);
		edtPassword.setVisibility(View.VISIBLE);
		edtPhone.setVisibility(View.VISIBLE);
		
		btnSignUp.setText("SIGN UP");
		tvSignUp.setTextColor(Color.parseColor("#4da6ff"));
		tvSignIn.setTextColor(Color.parseColor("#000000"));
		break;
	

		
	case R.id.btn_register:
		if(isSignIn)
			registrationValidation();
		else
			signUPValidation();
		break;
		
	/*case R.id.btn_google:
		googlePlusLogin();
		break;
	case R.id.btn_facebook_login:
			//loginWithFacebook();
			loginToFacebook();
		break;*/

	}
	
}

/*	
 @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
     // Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
      facebook.authorizeCallback(requestCode, resultCode, data);
      
      switch (requestCode) {
		case RC_SIGN_IN:
			if (resultCode == RESULT_OK) {
				signedInUser = false;

			}
			mIntentInProgress = false;
			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
			break;
		}
  }*/

}
