package com.motivator.wecareyou;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.motivator.common.AppsConstant;
import com.motivator.common.DateUtility;
import com.motivator.common.GeneralUtility;
import com.motivator.common.Pref;
import com.motivator.common.WebServices;
import com.motivator.database.GetData;
import com.motivator.database.PrefData;
import com.motivator.database.PutData;
import com.motivator.database.TableAttributes;
import com.motivator.database.UpdateData;
import com.motivator.model.UserRitualModel;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by sunil on 21-12-2016.
 */

public class KictstartTesting1 extends FragmentActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, TimePickerDialog.OnTimeSetListener, CompoundButton.OnCheckedChangeListener {
    int JOURNEY_MODE = 0;
    boolean isRegistering = false, isStandardLogin = true;
    TextView tvKickStart, tvTellUsAbtYou, tvFeelEnergized, tvLoseWeight, tvSleepBetter;
    ScrollView scReg;
    LinearLayout llUserName, llEmail;
    RelativeLayout rllTime;
    TextView tvTimeQues, tvTime1, tvTime2, tvTime3, tvTimeSet;
    TextView tvUserName, tvEnterUserName, tvEmail, tvOR;
    EditText edtUserName, edtEmail;
    View viewLine;
    Button btnNext, btnLetsDoIt, btnFacebook;
//    com.google.android.gms.common.SignInButton btnGoogle;
    LinearLayout llSocial;

    String userName, userId = "", userEmail = "";
    String userImgURL = "";

    Date dateObj;

    private static String APP_ID = "234992030177331";
    private Facebook facebook = new Facebook(APP_ID);
    private AsyncFacebookRunner mAsyncRunner;


    // Google client to communicate with Google
//    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private boolean signedInUser;
    private ConnectionResult mConnectionResult;

    private SharedPreferences mPrefs;
    PutData putData;
    GetData getData;
    UpdateData updateData;


    LinearLayout age_ll, gender_ll, problems_ll;
    RadioButton less_thirty_check, thirty_fourty_check, great_fourty_check, health_check, diabetes_check, other_check, guy_check, dad_check, girl_check, mom_check, grandad_check, grand_ma;
    Button next_gender_btn;

    RadioButton[] check = new RadioButton[12];

    String user_email, user_name, user_age, user_gender, user_health_condition;
    ProgressDialog progressDialog = null;
    public String alarm_time="";
    public static MediaPlayer mediaPlayer;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        putData = new PutData(KictstartTesting1.this);
        getData = new GetData(KictstartTesting1.this);
        updateData = new UpdateData(KictstartTesting1.this);

        setContentView(R.layout.kick_start);

        //INITIALIZING UI VIEWS
        intializeUI();
        mAsyncRunner = new AsyncFacebookRunner(facebook);
////////////////////////

        InitializeViews();
//		gender_ll.setVisibility(View.VISIBLE);

        initCheckBox();
        Checkvalidation(check);

//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.registration_1);
//        mediaPlayer.start();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        btnSignIn.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());

    }

    public void initCheckBox() {
        check[0] = less_thirty_check;
        check[1] = thirty_fourty_check;
        check[2] = great_fourty_check;
        check[3] = health_check;
        check[4] = diabetes_check;
        check[5] = other_check;
        check[6] = guy_check;
        check[7] = dad_check;
        check[8] = girl_check;
        check[9] = mom_check;
        check[10] = grandad_check;
        check[11] = grand_ma;
    }

    public void InitializeViews() {
        age_ll = (LinearLayout) findViewById(R.id.age_ll);
        gender_ll = (LinearLayout) findViewById(R.id.gender_ll);
        problems_ll = (LinearLayout) findViewById(R.id.problems_ll);

        less_thirty_check = (RadioButton) findViewById(R.id.less_thirty_check);
        thirty_fourty_check = (RadioButton) findViewById(R.id.thirty_fourty_check);
        great_fourty_check = (RadioButton) findViewById(R.id.great_fourty_check);
        health_check = (RadioButton) findViewById(R.id.health_check);
        diabetes_check = (RadioButton) findViewById(R.id.diabetes_check);
        other_check = (RadioButton) findViewById(R.id.other_check);
        guy_check = (RadioButton) findViewById(R.id.guy_check);
        girl_check = (RadioButton) findViewById(R.id.girl_check);
        mom_check = (RadioButton) findViewById(R.id.mom_check);
        grandad_check = (RadioButton) findViewById(R.id.grandad_check);
        grand_ma = (RadioButton) findViewById(R.id.grand_ma);
        dad_check = (RadioButton) findViewById(R.id.dad_check);


        next_gender_btn = (Button) findViewById(R.id.next_gender_btn);


        less_thirty_check.setOnCheckedChangeListener(this);
        thirty_fourty_check.setOnCheckedChangeListener(this);
        great_fourty_check.setOnCheckedChangeListener(this);
        health_check.setOnCheckedChangeListener(this);
        diabetes_check.setOnCheckedChangeListener(this);
        other_check.setOnCheckedChangeListener(this);
        guy_check.setOnCheckedChangeListener(this);
        girl_check.setOnCheckedChangeListener(this);
        mom_check.setOnCheckedChangeListener(this);
        grandad_check.setOnCheckedChangeListener(this);
        grand_ma.setOnCheckedChangeListener(this);
        dad_check.setOnCheckedChangeListener(this);


        next_gender_btn.setOnClickListener(this);
    }

    public boolean Checkvalidation(RadioButton... boxs) {
        for (int i = 0; i < boxs.length; ) {
            RadioButton check = boxs[i];
            if (check.isChecked()) {
                i++;
                return true;
            } else {
                i++;
            }
        }
        return false;
    }

    public void checkCondition(RadioButton check, RadioButton... boxs) {
        for (int i = 0; i < boxs.length; i++) {
            RadioButton checkbox = boxs[i];
            if (checkbox.getId() == check.getId()) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        // TODO Auto-generated method stub
        int id = buttonView.getId();
        RadioButton[] checbox = new RadioButton[3];
        checbox[0] = less_thirty_check;
        checbox[1] = thirty_fourty_check;
        checbox[2] = great_fourty_check;


        RadioButton[] checkbox1 = new RadioButton[3];
        checkbox1[0] = health_check;
        checkbox1[1] = diabetes_check;
        checkbox1[2] = other_check;


        RadioButton[] checkbox2 = new RadioButton[6];
        checkbox2[0] = guy_check;
        checkbox2[1] = dad_check;
        checkbox2[2] = girl_check;
        checkbox2[3] = mom_check;
        checkbox2[4] = grandad_check;
        checkbox2[5] = grand_ma;


        switch (id) {
            case R.id.less_thirty_check:
                if (isChecked) {

                    RadioButton check = (RadioButton) findViewById(id);
                    checkCondition(check, checbox);
                }
                break;
            case R.id.thirty_fourty_check:
                if (isChecked) {
                    RadioButton check1 = (RadioButton) findViewById(id);
                    checkCondition(check1, checbox);
                }
                break;

            case R.id.great_fourty_check:
                if (isChecked) {
                    RadioButton check2 = (RadioButton) findViewById(id);
                    checkCondition(check2, checbox);
                }
                break;

            case R.id.health_check:
                if (isChecked) {
                    RadioButton check3 = (RadioButton) findViewById(id);
                    checkCondition(check3, checkbox1);
                }
                break;

            case R.id.diabetes_check:
                if (isChecked) {
                    RadioButton check4 = (RadioButton) findViewById(id);
                    checkCondition(check4, checkbox1);
                }
                break;

            case R.id.other_check:
                if (isChecked) {
                    RadioButton check5 = (RadioButton) findViewById(id);
                    checkCondition(check5, checkbox1);
                }
                break;

            case R.id.guy_check:
                if (isChecked) {
                    RadioButton check6 = (RadioButton) findViewById(id);
                    checkCondition(check6, checkbox2);
                }
                break;

            case R.id.dad_check:
                if (isChecked) {
                    RadioButton check7 = (RadioButton) findViewById(id);
                    checkCondition(check7, checkbox2);
                }
                break;

            case R.id.girl_check:
                if (isChecked) {
                    RadioButton check8 = (RadioButton) findViewById(id);
                    checkCondition(check8, checkbox2);
                }
                break;

            case R.id.mom_check:
                if (isChecked) {
                    RadioButton check9 = (RadioButton) findViewById(id);
                    checkCondition(check9, checkbox2);
                }
                break;

            case R.id.grandad_check:
                if (isChecked) {
                    RadioButton check10 = (RadioButton) findViewById(id);
                    checkCondition(check10, checkbox2);
                }
                break;

            case R.id.grand_ma:
                if (isChecked) {
                    RadioButton check11 = (RadioButton) findViewById(id);
                    checkCondition(check11, checkbox2);
                }
                break;


            default:
//  			CheckBox check12=(CheckBox) findViewById(id);
//  			checkCondition(check12, this.check);
                break;
        }
    }


    public RadioButton SelectedCheckBox(RadioButton... boxs) {
        for (int i = 0; i < boxs.length; i++) {
            if (boxs[i].isChecked()) {
                return boxs[i];
            }
        }
        return boxs[0];
    }

    private void intializeUI() {
        Typeface tfMontez = Typeface.createFromAsset(KictstartTesting1.this.getAssets(), "fonts/Montez-Regular.ttf");
        Typeface tfPhilospher = Typeface.createFromAsset(KictstartTesting1.this.getAssets(), "fonts/Philosopher-Regular.ttf");

        tvKickStart = (TextView) findViewById(R.id.tv_kick_start);
        tvTellUsAbtYou = (TextView) findViewById(R.id.tv_tell_us_abt_you);
        tvFeelEnergized = (TextView) findViewById(R.id.tv_feel_energized);
        tvLoseWeight = (TextView) findViewById(R.id.tv_lose_weight);
        tvSleepBetter = (TextView) findViewById(R.id.tv_sleep_better);

        scReg = (ScrollView) findViewById(R.id.sv_reg);
        llUserName = (LinearLayout) findViewById(R.id.ll_user_name);
        llEmail = (LinearLayout) findViewById(R.id.ll_email);

        tvUserName = (TextView) findViewById(R.id.tv_user_name);
        edtUserName = (EditText) findViewById(R.id.edt_user_name);
        tvEnterUserName = (TextView) findViewById(R.id.tv_enter_user_name);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        edtEmail = (EditText) findViewById(R.id.edt_email_id);
        tvOR = (TextView) findViewById(R.id.tv_or);

        rllTime = (RelativeLayout) findViewById(R.id.rll_time);
        tvTimeQues = (TextView) findViewById(R.id.tv_time_ques);
        tvTime1 = (TextView) findViewById(R.id.tv_time1);
        tvTime2 = (TextView) findViewById(R.id.tv_time2);
        tvTime3 = (TextView) findViewById(R.id.tv_time3);
        tvTimeSet = (TextView) findViewById(R.id.tv_time_set);

        viewLine = (View) findViewById(R.id.view_separater);
        llSocial = (LinearLayout) findViewById(R.id.ll_social);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnLetsDoIt = (Button) findViewById(R.id.btn_lets_do_it);
        btnSignIn = (SignInButton) findViewById(R.id.btn_google);
        btnFacebook = (Button) findViewById(R.id.btn_facebook_login);


        tvKickStart.setTypeface(tfPhilospher);
        tvTellUsAbtYou.setTypeface(tfPhilospher);

        tvFeelEnergized.setTypeface(tfMontez);
        tvLoseWeight.setTypeface(tfMontez);
        tvSleepBetter.setTypeface(tfMontez);


        tvFeelEnergized.setOnClickListener(this);
        tvLoseWeight.setOnClickListener(this);
        tvSleepBetter.setOnClickListener(this);

        tvTime1.setOnClickListener(this);
        tvTime2.setOnClickListener(this);
        tvTime3.setOnClickListener(this);
        tvTimeSet.setOnClickListener(this);


        btnNext.setOnClickListener(this);
        btnLetsDoIt.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);


        edtUserName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
//        	          btnNext.setFocusable(true);
//        	          btnNext.requestFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            clickNext();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }


    public void registrationValidation() {
        userEmail = edtEmail.getText().toString();
        user_email = userEmail;
        if (userName == null || userName.length() <= 0)
            Toast.makeText(KictstartTesting1.this, R.string.enter_user_name, Toast.LENGTH_SHORT).show();
        else if (userEmail != null && userEmail.trim().length() > 0) {
            if (!(GeneralUtility.isValidEmail1(userEmail)))
                Toast.makeText(KictstartTesting1.this, R.string.enter_email, Toast.LENGTH_SHORT).show();
            else {
                if (GeneralUtility.isNetworkAvailable(KictstartTesting1.this)) {
                    user_age = AppsConstant.user_age;
                    user_gender = AppsConstant.user_gender;
                    user_health_condition = AppsConstant.user_health;

                    Log.d("sunil", "name:-" + user_name);
                    Log.d("sunil", "age:-" + user_age);
                    Log.d("sunil", "gender:-" + user_gender);
                    Log.d("sunil", "health:-" + user_health_condition);
                    Log.d("sunil", "email:-" + user_email);

//                    callService();
                    new RegistrationCall().execute(WebServices.NEW_ACCESS_LOGIN, userName, userEmail);
                } else
                    GeneralUtility.PopUpInfo(KictstartTesting1.this, R.string.network_failure, R.string.no_internet);
            }
        } else {
            if (GeneralUtility.isNetworkAvailable(KictstartTesting1.this)) {
                user_age = AppsConstant.user_age;
                user_gender = AppsConstant.user_gender;
                user_health_condition = AppsConstant.user_health;
                Log.d("sunil", "name:-" + user_name);
                Log.d("sunil", "age:-" + user_age);
                Log.d("sunil", "gender:-" + user_gender);
                Log.d("sunil", "health:-" + user_health_condition);
                Log.d("sunil", "email:-" + user_email);
//                callService();
                new RegistrationCall().execute(WebServices.NEW_ACCESS_LOGIN, userName, userEmail);
            } else
                GeneralUtility.PopUpInfo(KictstartTesting1.this, R.string.network_failure, R.string.no_internet);
        }

    }


    public void callService() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.funhabits.co/aaha/profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.optString("success");
                            if (success.equals("true")) {
                                JSONObject jsonObject1 = jsonObject.optJSONObject("result");

                                PrefData.setStringPref(getApplicationContext(), PrefData.NAME_KEY, user_name);
                                PrefData.setStringPref(getApplicationContext(), PrefData.AGE_KEY, user_age);
                                PrefData.setStringPref(getApplicationContext(), PrefData.HEALTH_KEY, user_health_condition);
                                PrefData.setStringPref(getApplicationContext(), PrefData.GENDER_KEY, user_gender);
                                PrefData.setStringPref(getApplicationContext(), PrefData.EMAIL_KEY, user_email);
                                letsDoITNow();
                                Toast.makeText(getApplicationContext(), "Registered Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sunil", "" + error);
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("pro_name", user_name);
                params.put("pro_age", user_age);
                params.put("pro_gen", user_gender);
                params.put("pro_helth_con", user_health_condition);
                params.put("pro_email", user_email);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        try{
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
        catch (Exception e){

        }

    }


    /**
     * Function to login into facebook
     */
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

    /**
     * Get Profile information by making request to Facebook Graph API
     */
    @SuppressWarnings("deprecation")
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

                    // getting name of the user
                    userId = profile.getString("id");
                    if (profile.toString().contains("email")) {
                        userEmail = profile.getString("email");
                        user_email = userEmail;
                    } else {
                        user_email = "";
                    }
                    user_age = AppsConstant.user_age;
                    user_gender = AppsConstant.user_gender;
                    user_health_condition = AppsConstant.user_health;
                    Log.d("sunil", "fname:-" + user_name);
                    Log.d("sunil", "fage:-" + user_age);
                    Log.d("sunil", "fgender:-" + user_gender);
                    Log.d("sunil", "fhealth:-" + user_health_condition);
                    Log.d("sunil", "femail:-" + user_email);

                    JSONObject picObj = new JSONObject(profile.getString("picture"));
                    JSONObject picData = new JSONObject(picObj.getString("data"));


                    userImgURL = picData.getString("url");


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new RegistrationCall().execute(WebServices.NEW_ACCESS_LOGIN, userName, userEmail);
//                    callService();
                        }

                    });

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




    private void updateProfile(boolean isSignedIn) {
        if (isSignedIn) {

            new RegistrationCall().execute(WebServices.NEW_ACCESS_LOGIN, userName, userEmail);

        } else {

        }
    }

//    private void getGoogleProfileInfo() {
//        try {
//            Log.d("sunil", "getting profile");
//            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
//                Log.d("sunil", "getting profile");
//                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//                //userName = currentPerson.getDisplayName();
//                userImgURL = currentPerson.getImage().getUrl();
//                userEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
//                Log.d("sunil", "email:-" + userEmail);
//                user_email = userEmail;
//                //new LoadProfileImage(image).execute(personPhotoUrl);
//                updateProfile(true);
//                // update profile frame with new info about Google Account
//                // profile
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            updateProfile(true);
//        }
//    }
    private final String TAG="kickstart";

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class RegistrationCall extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        //        ProgressDialog progressDialog;
        String password;

        public RegistrationCall(){

            Log.d(TAG, "call:-in registration call");
            Log.d(TAG, "fname:-" + user_name);
            Log.d(TAG, "fage:-" + AppsConstant.user_age);
            Log.d(TAG, "fgender:-" + AppsConstant.user_gender);
            Log.d(TAG, "fhealth:-" + AppsConstant.user_health);
            Log.d(TAG, "femail:-" + user_email);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Log.d("journeymode","journeymode:-"+ GeneralUtility.getPreferences(KictstartTesting1.this,"journey_mode"));
//            Log.d("journeymode","alarm:-"+alarm_time);
//            progressDialog = ProgressDialog.show(getApplicationContext(), "Please wait...", "");
//            progressDialog.setCancelable(true);


        }

        @Override
        protected String doInBackground(String... params) {

//            nameValuePairs.add(new BasicNameValuePair("pro_name", user_name));
//            nameValuePairs.add(new BasicNameValuePair("pro_age", user_age));
//            nameValuePairs.add(new BasicNameValuePair("pro_gen", user_gender));
//            nameValuePairs.add(new BasicNameValuePair("pro_helth_con", user_health_condition));
//            nameValuePairs.add(new BasicNameValuePair("pro_email", user_email));
//            nameValuePairs.add(new BasicNameValuePair("journey_type", GeneralUtility.getPreferences(KictstartTesting1.this,"journey_mode")+""));
//            nameValuePairs.add(new BasicNameValuePair("alarm_time", ""));
//
//            try {
//                jResult = WebServices.httpCall(params[0], nameValuePairs);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);

//            if (progressDialog != null)
//                progressDialog.dismiss();
//            {
            try {
                Log.d("sunil", jsonResponse);
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String success = jsonObject.optString("success");
                if (success.equals("true")) {
                    JSONObject jsonObject1 = jsonObject.optJSONObject("result");

                    PrefData.setStringPref(getApplicationContext(), PrefData.NAME_KEY, user_name);
                    PrefData.setStringPref(getApplicationContext(), PrefData.AGE_KEY, user_age);
                    PrefData.setStringPref(getApplicationContext(), PrefData.HEALTH_KEY, user_health_condition);
                    PrefData.setStringPref(getApplicationContext(), PrefData.GENDER_KEY, user_gender);
                    PrefData.setStringPref(getApplicationContext(), PrefData.EMAIL_KEY, user_email);
                    letsDoITNow();

//                    startService(new Intent(KictstartTesting1.this, ContactService.class));
//                    Intent i = new Intent(KictstartTesting1.this, Chapter1.class);
//                    startActivity(i);
//                    finish();
                    Toast.makeText(getApplicationContext(), "Registered Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//            try {
//                if (jsonResponse != null && jsonResponse.length() > 0) {
//                    JSONObject jObj = new JSONObject(jsonResponse);
//                    String isSuccess = jObj.getString("isSuccess");
//                    String successMsg = jObj.getString("success_msg");
//
//                    if (isSuccess.equalsIgnoreCase("true")) {
////                        if (isStandardLogin) {
//                        letsDoITNow();
////                        } else {
////                            llEmail.setVisibility(View.GONE);
////                            rllTime.setVisibility(View.GONE);
////                            llSocial.setVisibility(View.GONE);
////                            viewLine.setVisibility(View.GONE);
//////                            btnLetsDoIt.setVisibility(View.VISIBLE);
////                        }
//
//                    } else
//                        Toast.makeText(KictstartTesting1.this, successMsg, Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


        }

    }

    public void letsDoITNow() {
        GeneralUtility.setPreferences(KictstartTesting1.this, AppsConstant.user_name, userName);
        GeneralUtility.setPreferences(KictstartTesting1.this, "user_email", userEmail);
        GeneralUtility.setPreferences(KictstartTesting1.this, "user_id", userId);
        GeneralUtility.setPreferences(KictstartTesting1.this, "user_img_url", userImgURL);

        int res = updateData.updateUserEmail(userName, userEmail);
        user_name = userName;
        user_email = userEmail;

        int numOfEvents = 27;
        long row = putData.insertJourneyInfo(userName, AppsConstant.Interesting_Journey, numOfEvents);
        if (row > 0) {
            putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.drinkWaterId);
            putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.greatBreakFastID);
            putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.danceYourWayID);

            putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.goldenChallengeID);
            putData.insertJourneyHabit(userName, AppsConstant.Interesting_Journey, TableAttributes.SecretLetterID);
        }

        Intent i = new Intent(KictstartTesting1.this, Chapter1.class);
        startActivity(i);
        finish();
    }

    ///////


    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feel_energized:

                if (GeneralUtility.getPreferences(KictstartTesting1.this, AppsConstant.user_name).equalsIgnoreCase("")) {
                    if (!isRegistering) {
                        isRegistering = true;
                        JOURNEY_MODE = AppsConstant.FEEL_ENERGIZED;
                        GeneralUtility.setPreferences(KictstartTesting1.this, "journey_mode", "" + AppsConstant.FEEL_ENERGIZED);
                        tvLoseWeight.setVisibility(View.GONE);
                        tvSleepBetter.setVisibility(View.GONE);

                        setVisibilityofViews();

                    }
                } else {
                    JOURNEY_MODE = AppsConstant.FEEL_ENERGIZED;
                    GeneralUtility.setPreferences(KictstartTesting1.this, "journey_mode", "" + AppsConstant.FEEL_ENERGIZED);
                    Intent i = new Intent(KictstartTesting1.this, Chapter1.class);
                    startActivity(i);
                    finish();
                    //overridePendingTransition(R.anim.right_in_new, R.anim.left_out_new);
                }

                break;

            case R.id.tv_lose_weight:
                if (GeneralUtility.getPreferences(KictstartTesting1.this, AppsConstant.user_name).equalsIgnoreCase("")) {
                    if (!isRegistering) {
                        isRegistering = true;
                        JOURNEY_MODE = AppsConstant.LOSE_WEIGHT;
                        GeneralUtility.setPreferences(KictstartTesting1.this, "journey_mode", "" + AppsConstant.LOSE_WEIGHT);
                        tvFeelEnergized.setVisibility(View.GONE);
                        tvSleepBetter.setVisibility(View.GONE);
                        setVisibilityofViews();
                    }
                } else {
                    JOURNEY_MODE = AppsConstant.LOSE_WEIGHT;
                    GeneralUtility.setPreferences(KictstartTesting1.this, "journey_mode", "" + AppsConstant.LOSE_WEIGHT);
                    Intent i = new Intent(KictstartTesting1.this, Chapter1.class);
                    startActivity(i);
                    finish();
                    //overridePendingTransition(R.anim.right_in_new, R.anim.left_out_new);
                }

                break;

            case R.id.tv_sleep_better:
                if (GeneralUtility.getPreferences(KictstartTesting1.this, AppsConstant.user_name).equalsIgnoreCase("")) {
                    if (!isRegistering) {
                        isRegistering = true;
                        JOURNEY_MODE = AppsConstant.SLEEP_BETTER;
                        GeneralUtility.setPreferences(KictstartTesting1.this, "journey_mode", "" + AppsConstant.SLEEP_BETTER);
                        tvFeelEnergized.setVisibility(View.GONE);
                        tvLoseWeight.setVisibility(View.GONE);

                        setVisibilityofViews();
                    }
                } else {
                    JOURNEY_MODE = AppsConstant.SLEEP_BETTER;
                    GeneralUtility.setPreferences(KictstartTesting1.this, "journey_mode", "" + AppsConstant.SLEEP_BETTER);
                    Intent i = new Intent(KictstartTesting1.this, Chapter1.class);
                    startActivity(i);
                    finish();
                    //overridePendingTransition(R.anim.right_in_new, R.anim.left_out_new);
                }

                break;

            case R.id.btn_next:
//		clickNext();
                user_name=edtUserName.getText().toString();
                scReg.setVisibility(View.VISIBLE);
                llUserName.setVisibility(View.GONE);
                tvEnterUserName.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);


                age_ll.setVisibility(View.VISIBLE);
                problems_ll.setVisibility(View.VISIBLE);
                gender_ll.setVisibility(View.VISIBLE);

                rllTime.setVisibility(View.GONE);
                llEmail.setVisibility(View.GONE);
                btnLetsDoIt.setVisibility(View.GONE);
                llSocial.setVisibility(View.GONE);
                break;

            case R.id.tv_time1:
                tvTime1.setTextColor(getResources().getColor(R.color.time_selected));
                tvTime2.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTime3.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTimeSet.setTextColor(getResources().getColor(R.color.black));
                addTimeToDatabase(tvTime1.getText().toString());
                break;

            case R.id.tv_time2:
                tvTime1.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTime2.setTextColor(getResources().getColor(R.color.time_selected));
                tvTime3.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTimeSet.setTextColor(getResources().getColor(R.color.black));
                addTimeToDatabase(tvTime2.getText().toString());
                break;

            case R.id.tv_time3:
                tvTime1.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTime2.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTime3.setTextColor(getResources().getColor(R.color.time_selected));
                tvTimeSet.setTextColor(getResources().getColor(R.color.black));
                addTimeToDatabase(tvTime3.getText().toString());
                break;

            case R.id.tv_time_set:
                tvTime1.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTime2.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTime3.setTextColor(getResources().getColor(R.color.time_unselected));
                tvTimeSet.setTextColor(getResources().getColor(R.color.time_selected));

                Calendar now = Calendar.getInstance();
                now.setTime(dateObj);
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        KictstartTesting1.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE), false);
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");

                break;

            case R.id.btn_lets_do_it:
                if (isStandardLogin)
                    registrationValidation();
                else
                    letsDoITNow();


                break;

            case R.id.btn_google:
                isStandardLogin = false;
                if (GeneralUtility.isNetworkAvailable(KictstartTesting1.this)) {
                    //mGoogleApiClient.connect();
//                    googlePlusLogin();
                    signIn();
                    //GeneralUtility.setPreferences(KictstartTesting1.this, "google_connected", "true");
                } else
                    Toast.makeText(KictstartTesting1.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_facebook_login:
                isStandardLogin = false;
                if (GeneralUtility.isNetworkAvailable(KictstartTesting1.this))
                    loginToFacebook();
                else
                    Toast.makeText(KictstartTesting1.this, R.string.network_failure, Toast.LENGTH_SHORT).show();
                break;
            case R.id.next_gender_btn:
                RadioButton[] checbox = new RadioButton[3];
                checbox[0] = less_thirty_check;
                checbox[1] = thirty_fourty_check;
                checbox[2] = great_fourty_check;


                RadioButton[] checkbox1 = new RadioButton[3];
                checkbox1[0] = health_check;
                checkbox1[1] = diabetes_check;
                checkbox1[2] = other_check;


                RadioButton[] check2 = new RadioButton[6];
                check2[0] = guy_check;
                check2[1] = dad_check;
                check2[2] = girl_check;
                check2[3] = mom_check;
                check2[4] = grandad_check;
                check2[5] = grand_ma;

                if (Checkvalidation(checbox)) {
                    RadioButton checkbox = SelectedCheckBox(checbox);
                    int check_id = checkbox.getId();
                    switch (check_id) {
                        case R.id.less_thirty_check:
                            Log.d("sun", "less thirty check");
                            AppsConstant.user_age = "<30";
                            break;
                        case R.id.thirty_fourty_check:
                            Log.d("sun", "thirty fourty check");
                            AppsConstant.user_age = "30-45";
                            break;
                        case R.id.great_fourty_check:
                            Log.d("sun", "great fourty check");
                            AppsConstant.user_age = ">45";
                            break;

                        default:
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "please select age", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (Checkvalidation(checkbox1)) {
                    RadioButton checkbox = SelectedCheckBox(checkbox1);
                    int check_id = checkbox.getId();
                    switch (check_id) {
                        case R.id.health_check:
                            Log.d("sun", "health_check");
                            AppsConstant.user_health = "health";
                            break;
                        case R.id.diabetes_check:
                            Log.d("sun", "diabetes_check");
                            AppsConstant.user_health = "diabetes";
                            break;
                        case R.id.other_check:
                            Log.d("sun", "other_check");
                            AppsConstant.user_health = "other";
                            break;

                        default:
                            break;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "please Select the health Problem", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (Checkvalidation(check2)) {
                    RadioButton checkbox = SelectedCheckBox(check2);
                    int check_id = checkbox.getId();
                    switch (check_id) {
                        case R.id.guy_check:
                            Log.d("sun", "Gender:-Guy");
                            AppsConstant.user_gender = "guy";
                            break;
                        case R.id.dad_check:
                            Log.d("sun", "Gender:-Dad");
                            AppsConstant.user_gender = "dad";
                            break;
                        case R.id.girl_check:
                            Log.d("sun", "Gender:-Girl");
                            AppsConstant.user_gender = "girl";
                            break;
                        case R.id.mom_check:
                            Log.d("sun", "Gender:-Mom");
                            AppsConstant.user_gender = "mom";
                            break;

                        case R.id.grandad_check:
                            Log.d("sun", "Gender:-GrandDad");
                            AppsConstant.user_gender = "granddad";
                            break;

                        case R.id.grand_ma:
                            Log.d("sun", "Gender:-GrandMaa");
                            AppsConstant.user_gender = "grandmaa";
                            break;


                        default:
                            Log.d("sun", "Gender:-Guy");
                            AppsConstant.user_gender = "guy";
                            break;
                    }
                    age_ll.setVisibility(View.GONE);
                    gender_ll.setVisibility(View.GONE);
                    problems_ll.setVisibility(View.GONE);

                    SharedPreferences sp = getSharedPreferences("aaha.txt", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("user_age", AppsConstant.user_age);
                    editor.putString("user_health", AppsConstant.user_health);
                    editor.putString("user_gender", AppsConstant.user_gender);
                    editor.commit();

                    clickNext();
//			llUserName.setVisibility(View.VISIBLE);
//			 tvEnterUserName.setVisibility(View.GONE);
//			 btnNext.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Select Your Gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Pref.setBoolean(getApplicationContext(),"googlesignin",true);
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();
            user_email=email;
            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            new RegistrationCall().execute(WebServices.NEW_ACCESS_LOGIN, userName, user_email);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }
    private void clickNext() {
        userName = edtUserName.getText().toString();
        if (userName != null && userName.trim().length() > 0) {
            long inserted = putData.insertUserData(userName);
            if (inserted > 0) {
                long result = putData.addUserRitual(userName, 1, AppsConstant.MORNING_RITUAL, AppsConstant.MORNING_RITUAL,
                        "7:30 AM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
                result = putData.addUserRitual(userName, 2, AppsConstant.LUNCH_RITUAL, AppsConstant.LUNCH_RITUAL,
                        "12:00 PM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
                result = putData.addUserRitual(userName, 3, AppsConstant.EVENING_RITUAL, AppsConstant.EVENING_RITUAL,
                        "10:00 PM", TableAttributes.OFF, TableAttributes.ON, TableAttributes.OFF, 0);
            }

            llUserName.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);

            rllTime.setVisibility(View.VISIBLE);
            llEmail.setVisibility(View.VISIBLE);
            viewLine.setVisibility(View.VISIBLE);
            btnLetsDoIt.setVisibility(View.VISIBLE);
            llSocial.setVisibility(View.VISIBLE);

            UserRitualModel userInfo = new UserRitualModel();
            switch (JOURNEY_MODE) {
                case AppsConstant.FEEL_ENERGIZED:
                    userInfo = getData.getRitualsDetails(userName, AppsConstant.MORNING_RITUAL);
                    String wakeUpTime = userInfo.getRitualTime();
                    tvTimeQues.setText(userName + ", when do you wake up?");
                    tvTime1.setText("7:30 AM");
                    tvTime2.setText("8:00 AM");
                    tvTime3.setText("8:30 AM");
                    if (wakeUpTime != null && wakeUpTime.length() > 0)
                        tvTimeSet.setText(wakeUpTime);
                    else {
                        wakeUpTime = "7:30 AM";
                        tvTimeSet.setText(". . .");
                    }
                    dateObj = DateUtility.getDateObject(wakeUpTime, "hh:mm a");

                    break;

                case AppsConstant.LOSE_WEIGHT:
                    userInfo = getData.getRitualsDetails(userName, AppsConstant.LUNCH_RITUAL);
                    String lunchTime = userInfo.getRitualTime();
                    tvTimeQues.setText(userName + ", when do you have lunch?");
                    tvTime1.setText("12:00 PM");
                    tvTime2.setText("12:30 PM");
                    tvTime3.setText("1:00 PM");
                    if (lunchTime != null && lunchTime.length() > 0)
                        tvTimeSet.setText(lunchTime);
                    else {
                        lunchTime = "12:00 PM";
                        tvTimeSet.setText(". . .");
                    }
                    dateObj = DateUtility.getDateObject(lunchTime, "hh:mm a");
                    break;
                case AppsConstant.SLEEP_BETTER:
                    userInfo = getData.getRitualsDetails(userName, AppsConstant.EVENING_RITUAL);
                    String SleepTime = userInfo.getRitualTime();
                    tvTimeQues.setText(userName + ", when do you sleep generally?");
                    tvTime1.setText("10:00 PM");
                    tvTime2.setText("11:00 PM");
                    tvTime3.setText("12:00 PM");
                    if (SleepTime != null && SleepTime.length() > 0)
                        tvTimeSet.setText(SleepTime);
                    else {
                        SleepTime = "10:00 PM";
                        tvTimeSet.setText(". . .");
                    }
                    dateObj = DateUtility.getDateObject(SleepTime, "hh:mm a");
                    break;
                default:
                    tvTimeQues.setText(userName);
                    break;
            }
        } else
            tvEnterUserName.setVisibility(View.VISIBLE);

    }


    private void setVisibilityofViews() {
        //have to set animation
        tvKickStart.setText(getResources().getString(R.string.kick_start));
        tvTellUsAbtYou.setText(getResources().getString(R.string.tell_us_abt_u));

        scReg.setVisibility(View.VISIBLE);
        llUserName.setVisibility(View.VISIBLE);
        tvEnterUserName.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);


        rllTime.setVisibility(View.GONE);
        llEmail.setVisibility(View.GONE);
        btnLetsDoIt.setVisibility(View.GONE);
        llSocial.setVisibility(View.GONE);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
        facebook.authorizeCallback(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onBackPressed() {
        if (isRegistering) {
            isRegistering = false;
            scReg.setVisibility(View.GONE);
            rllTime.setVisibility(View.GONE);
            llEmail.setVisibility(View.GONE);
            viewLine.setVisibility(View.GONE);
            btnLetsDoIt.setVisibility(View.GONE);
            llSocial.setVisibility(View.GONE);

            tvFeelEnergized.setVisibility(View.VISIBLE);
            tvLoseWeight.setVisibility(View.VISIBLE);
            tvSleepBetter.setVisibility(View.VISIBLE);

            tvKickStart.setText(getResources().getString(R.string.lets_start));
            tvTellUsAbtYou.setText(getResources().getString(R.string.health_goal));
        } else
            super.onBackPressed();

    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = hourString + ":" + minuteString + "m";

        time = DateUtility.getTimeIn12Format(time);

        if (time.startsWith("0:"))
            time = time.replace("0:", "12:");
        tvTimeSet.setText(time);
        addTimeToDatabase(time);

    }

    private void addTimeToDatabase(String time) {
        Log.d("sunil","time added:-"+time);
        switch (JOURNEY_MODE) {
            case AppsConstant.FEEL_ENERGIZED:
                //wakeUpTime = time;
                setAlarm(AppsConstant.MORNING_RITUAL, time);
                break;

            case AppsConstant.LOSE_WEIGHT:
                //lunchTime = time;
                setAlarm(AppsConstant.LUNCH_RITUAL, time);
                break;

            case AppsConstant.SLEEP_BETTER:
                //SleepTime = time;
                setAlarm(AppsConstant.EVENING_RITUAL, time);
                break;
        }
        alarm_time=time;
    }

    private void setAlarm(String selectedRitual, String remTime) {
        Log.d("journey",remTime);
        String time24Format = DateUtility.getTimeIn24Format(remTime);

        String t[] = time24Format.split(":");
        int hourOfDay = Integer.parseInt(t[0]);
        int minute = Integer.parseInt(t[1]);

        Long tsLong = System.currentTimeMillis() / 1000;
        int remID = tsLong.intValue();
        int timeStamp = tsLong.intValue();
        Random r = new Random();

        //Update ritual time
        updateData.updateUserRitualTime(userName, selectedRitual, remTime, remID);

        putData.addReminder(remID, userName, remTime, selectedRitual);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_SUN, TableAttributes.OFF, -1);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_SAT, TableAttributes.OFF, -1);

        //seting alarm for repeating days from monday to friday
        //for(int i=4; i<=6; i++)	//Set alrm from monday(day of week 2)to fri(day of week 6)
        GeneralUtility.setAlarmTime(KictstartTesting1.this, userName, selectedRitual, Calendar.MONDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_MON, TableAttributes.ON, timeStamp);

        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        GeneralUtility.setAlarmTime(KictstartTesting1.this, userName, selectedRitual, Calendar.TUESDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_TUE, TableAttributes.ON, timeStamp);

        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        GeneralUtility.setAlarmTime(KictstartTesting1.this, userName, selectedRitual, Calendar.WEDNESDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_WED, TableAttributes.ON, timeStamp);

        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        GeneralUtility.setAlarmTime(KictstartTesting1.this, userName, selectedRitual, Calendar.THURSDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_THU, TableAttributes.ON, timeStamp);

        tsLong = System.currentTimeMillis() / 1000;
        timeStamp = tsLong.intValue() + r.nextInt(100);
        GeneralUtility.setAlarmTime(KictstartTesting1.this, userName, selectedRitual, Calendar.FRIDAY, hourOfDay, minute, timeStamp, false);
        putData.addReminderDesc(remID, userName, remTime, TableAttributes.REMINDER_FRI, TableAttributes.ON, timeStamp);

    }

}
