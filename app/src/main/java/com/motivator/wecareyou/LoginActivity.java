package com.motivator.wecareyou;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.common.WebServices;
import com.motivator.database.GetData;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;
import com.motivator.database.UpdateData;
import com.motivator.model.HabitModel;
import com.motivator.services.AlldataService.GetAllRituals;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class LoginActivity extends FragmentActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    EditText email_et;
    Button login_btn,btn_facebook_login;
    UpdateData updateData;
    PutData putData;
    GetData getData;
    ArrayList<HabitModel> allHabitList = new ArrayList<HabitModel>();
    private final String TAG="loginactivity";
//    CallbackManager callbackManager;
    //Signing Options
    private GoogleSignInOptions gso;
    private SignInButton signInButton;
    //google api client
    private int RC_SIGN_IN = 100;
    private GoogleApiClient mGoogleApiClient;
    private SharedPreferences mPrefs;
    private static String APP_ID = "234992030177331";
    private Facebook facebook = new Facebook(APP_ID);
    private AsyncFacebookRunner mAsyncRunner;
    private MediaPlayer mPlayer1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_et= (EditText) findViewById(R.id.email);
        login_btn= (Button) findViewById(R.id.login);
        btn_facebook_login= (Button) findViewById(R.id.btn_facebook_login);


        putData = new PutData(LoginActivity.this);
        updateData = new UpdateData(LoginActivity.this);
        getData = new GetData(LoginActivity.this);
        SpannableString s = new SpannableString("Login");
        s.setSpan(new com.motivator.support.TypefaceSpan(this, "fonts/Montez-Regular.ttf"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.sky_blue)/*Color.parseColor("#330000ff")*/));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setTitle("Login");
        actionBar.setTitle(s);


        mAsyncRunner = new AsyncFacebookRunner(facebook);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_et.getText().toString().length()>0) {
                    new RegistrationCall(email_et.getText().toString()).execute("http://www.funhabits.co/aaha/login.php");
                }
                else{
                    Log.d("sunil","Please Enter Email First");
                }
            }
        });
        allHabitList = getData.getAllHabits();
        Log.d("sunil",allHabitList.toString());


        btn_facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (GeneralUtility.isNetworkAvailable(LoginActivity.this))
                    loginToFacebook();
//                else
//                    Toast.makeText(LoginActivity.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
            }
        });
        ListFiles(new File(FileUtils.LOGIN_FILE_PATH));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlayer1!=null){
            mPlayer1.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mPlayer1!=null){
            mPlayer1.stop();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void ListFiles(File f){
        File[] files=f.listFiles();
        Log.d(TAG,"length:-"+files.length);
        int val= Pref.getInteger(getApplicationContext(), StringUtils.LOGIN,-1);
        Log.d(TAG,"pref val:-"+val);
        if(files.length>0){

            if((val+1)>=files.length){
                val=0;
            }
            else{
                val=val+1;
            }
        }
        try{
            Log.d(TAG,"final val:-"+val);
            File soundFile=files[val];
            mPlayer1 = new MediaPlayer();
            mPlayer1 .setDataSource(soundFile.toString());
            mPlayer1 .prepare();
            if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
                mPlayer1 .start();
            }
            int MAX_VOLUME = 100;
            final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
            mPlayer1 .setVolume(volume, volume);
            Pref.setInteger(getApplicationContext(),StringUtils.LOGIN,val);
            Log.d(TAG,"pref mood:-"+Pref.getInteger(getApplicationContext(),StringUtils.LOGIN,-1));
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            try {
                Log.d("sunil", "name:-" + acct.getDisplayName());
                Log.d("sunil", "email:-" + acct.getEmail());
//                Log.d("sunil", "image:-" + acct.getPhotoUrl().toString());

                if(acct.getEmail().length()>0){
                    new RegistrationCall(acct.getEmail().toString()).execute("http://www.funhabits.co/aaha/login.php");
                }
                Log.d("sunil", "id:-" + acct.getId());
//                if(acct.getEmail().toString().length()>0) {
//                    new RegistrationCall(acct.getEmail().toString()).execute("http://www.funhabits.co/aaha/login.php");
//                }

            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Your Google account is not configured with google plus account",Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            //Calling signin
            signIn();
        }
    }

    class RegistrationCall extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        ProgressDialog progressDialog;
        String email;
        public RegistrationCall(String email){
            this.email=email;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "");
            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("pro_email", email));

            try {
                jResult = WebServices.httpCall(params[0], nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);
            String user_id="";
            String user_name="";
            if (progressDialog != null)
                progressDialog.dismiss();
            try {
                Log.d("sunil", jsonResponse);
                JSONObject jsonObject=new JSONObject(jsonResponse);
                String success=jsonObject.optString("success");
                if(success.equals("true")) {
                    JSONObject object = jsonObject.optJSONObject("result");
                    String pro_id=object.optString("pro_id");
                    String pro_name=object.optString("pro_name");
                    String pro_age=object.optString("pro_age");
                    String pro_gen=object.optString("pro_gen");
                    String pro_mob=object.optString("pro_mob");
                    String pro_helth_con=object.optString("pro_helth_con");
                    String pro_email=object.optString("pro_email");
                    String journey_type=object.optString("journey_type");
                    String alarm_time=object.optString("alarm_time");
                    String pro_date=object.optString("pro_date");
                    String pro_status=object.optString("pro_status");


                    PrefData.setStringPref(getApplicationContext(), PrefData.USER_ID, pro_id);
                    PrefData.setStringPref(getApplicationContext(), PrefData.NAME_KEY, pro_name);
                    PrefData.setStringPref(getApplicationContext(), PrefData.AGE_KEY, pro_age);
                    PrefData.setStringPref(getApplicationContext(), PrefData.HEALTH_KEY, pro_helth_con);
                    PrefData.setStringPref(getApplicationContext(), PrefData.GENDER_KEY, pro_gen);
                    PrefData.setStringPref(getApplicationContext(), PrefData.EMAIL_KEY, pro_email);
                    GeneralUtility.setPreferences(LoginActivity.this, AppsConstant.user_name,pro_name);
                    if(pro_id.length()>0){
//                        new GetALlDetails(pro_id).execute();
                        new GetAllRituals(LoginActivity.this,pro_id).new GettingUserRitualDetails().execute();
                    }
                }

//                JSONObject jsonObject = new JSONObject(jsonResponse);
//                String success = jsonObject.optString("success");
//                if (success.equals("true")) {
//                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");
//
//                    PrefData.setStringPref(getApplicationContext(), PrefData.NAME_KEY, jsonObject1.optString("pro_name"));
//                    PrefData.setStringPref(getApplicationContext(), PrefData.AGE_KEY, jsonObject1.optString("pro_age"));
//                    PrefData.setStringPref(getApplicationContext(), PrefData.HEALTH_KEY, jsonObject1.optString("pro_helth_con"));
//                    PrefData.setStringPref(getApplicationContext(), PrefData.GENDER_KEY, jsonObject1.optString("pro_gen"));
//                    PrefData.setStringPref(getApplicationContext(), PrefData.EMAIL_KEY, jsonObject1.optString("pro_email"));
//
//
//                    GeneralUtility.setPreferences(LoginActivity.this, AppsConstant.user_name, jsonObject1.optString("pro_name"));
//                    GeneralUtility.setPreferences(LoginActivity.this, "user_email", jsonObject1.optString("pro_email"));
//                    GeneralUtility.setPreferences(LoginActivity.this, "user_id", jsonObject1.optString("pro_id"));
//                    GeneralUtility.setPreferences(LoginActivity.this, "user_img_url", "");
////                    user_name=jsonObject1.optString("pro_name");
////                    user_id=jsonObject1.optString("pro_id");
//                    int res = updateData.updateUserEmail(jsonObject1.optString("pro_name"), jsonObject1.optString("pro_email"));
//                    int numOfEvents = 27;
//                    long row = putData.insertJourneyInfo(jsonObject1.optString("pro_name"), AppsConstant.Interesting_Journey, numOfEvents);
//                    if(row>0){
//                        putData.insertJourneyHabit(jsonObject1.optString("pro_name"), AppsConstant.Interesting_Journey, TableAttributes.drinkWaterId);
//                        putData.insertJourneyHabit(jsonObject1.optString("pro_name"), AppsConstant.Interesting_Journey, TableAttributes.greatBreakFastID);
//                        putData.insertJourneyHabit(jsonObject1.optString("pro_name"), AppsConstant.Interesting_Journey, TableAttributes.danceYourWayID);
//
//                        putData.insertJourneyHabit(jsonObject1.optString("pro_name"), AppsConstant.Interesting_Journey, TableAttributes.goldenChallengeID);
//                        putData.insertJourneyHabit(jsonObject1.optString("pro_name"), AppsConstant.Interesting_Journey, TableAttributes.SecretLetterID);
//                    }
//                    GeneralUtility.setPreferences(LoginActivity.this, "journey_mode", "" + jsonObject1.optString("journey_type"));
//                    long inserted = putData.insertUserData(jsonObject1.optString("pro_name"));
//                    Log.d("sunil","inserted:-"+inserted);
//                    if (inserted > 0) {
//                        long result = putData.addUserRitual(jsonObject1.optString("pro_name"), 1, AppsConstant.MORNING_RITUAL, AppsConstant.MORNING_RITUAL,
//                                "7:30 AM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
//                        Log.d("sunil","result1:-"+result);
//                        result = putData.addUserRitual(jsonObject1.optString("pro_name"), 2, AppsConstant.LUNCH_RITUAL, AppsConstant.LUNCH_RITUAL,
//                                "12:00 PM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
//                        Log.d("sunil","result2:-"+result);
//                        result = putData.addUserRitual(jsonObject1.optString("pro_name"), 3, AppsConstant.EVENING_RITUAL, AppsConstant.EVENING_RITUAL,
//                                "10:00 PM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
//                        Log.d("sunil","result3:-"+result);
//
//                    }
////                    updateData.updateUserRitualTime(userName, selectedRitual, remTime, remID);
////                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
////                    finish();
//                    GeneralUtility.setPreferencesBoolean(getApplicationContext(), AppsConstant.IS_RITUAL_ADDED,true);
//                    GeneralUtility.setPreferencesBoolean(getApplicationContext(),"login",true);
//                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
//
//
//
//                    Log.d("sunil",jsonObject1.optString("pro_id")+"::"+jsonObject1.optString("pro_name"));
//                    new AddHabits(jsonObject1.optString("pro_id"),jsonObject1.optString("pro_name")).execute("http://www.funhabits.co/aaha/getallhabitcategory.php");
//
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//                }
            } catch (Exception e) {
                Log.d("sunil",e.toString());
            }


        }
    }

    class GetALlDetails extends AsyncTask<String,Void,String>{
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        ProgressDialog progressDialog;
        String pro_id;
        public GetALlDetails(String pro_id){
            this.pro_id=pro_id;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please wait...", "");
            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("user_id", pro_id));

            try {
                jResult = WebServices.httpCall(WebServices.GET_ALL_INFO_URL, nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);
            String user_id="";
            String user_name="";
            if (progressDialog != null)
                progressDialog.dismiss();
            try {
                Log.d("sunil", jsonResponse);
                JSONObject jsonObject=new JSONObject(jsonResponse);
                String success=jsonObject.optString("success");
                if(success.equals("true")) {
                    JSONObject object = jsonObject.optJSONObject("result");


                }
            } catch (Exception e) {
                Log.d("sunil",e.toString());
            }


        }
    }



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
        facebook.authorize(this, new String[]{"email", "publish_actions"}, new Facebook.DialogListener() {

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
                editor.putLong("access_expires", facebook.getAccessExpires());
                editor.commit();

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
    }

    public void getProfileInformation() {

        Bundle params = new Bundle();
        params.putString("fields", "id,name,email,picture,gender,first_name");

        mAsyncRunner.request("me", params, new AsyncFacebookRunner.RequestListener() {

            @Override
            public void onComplete(String response, Object state) {
                Log.d("Profile", response);
                String json = response;
                try {
                    // Facebook Profile JSON data
                    JSONObject profile = new JSONObject(json);
                    String userEmail = "";
                    if (profile.toString().contains("email")) {
                         userEmail= profile.getString("email");
                        Log.d("sunil","useremail:-"+userEmail);
                        new RegistrationCall(userEmail).execute("http://www.funhabits.co/aaha/login.php");
                    } else {

                    }
                    final String email=userEmail;

//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                        if(email.length()>0){
//                            new RegistrationCall(email).execute("http://www.funhabits.co/aaha/login.php");
//                        }
////                    callService();
//                        }
//
//                    });

                } catch (JSONException e) {
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

}
