package com.motivator.wecareyou;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.motivator.common.WebServices;
import com.motivator.database.PrefData;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView name, age, health, sex, email;
    FloatingActionButton fab_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        toolbar = (Toolbar) findViewById(R.id.image_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        fab_edit= (FloatingActionButton) findViewById(R.id.fab_edit);

//        ActionBar actionBar = getActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
//        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
//        actionBar.setHomeButtonEnabled(true);
//


        name = (TextView) findViewById(R.id.name_tv);
        age = (TextView) findViewById(R.id.age_tv);
        health = (TextView) findViewById(R.id.health_tv);
        sex = (TextView) findViewById(R.id.sex_tv);
        email = (TextView) findViewById(R.id.email_tv);

        name.setText(PrefData.getStringPref(getApplicationContext(), PrefData.NAME_KEY));
        age.setText(PrefData.getStringPref(getApplicationContext(), PrefData.AGE_KEY));
        health.setText(PrefData.getStringPref(getApplicationContext(), PrefData.HEALTH_KEY));
        sex.setText(PrefData.getStringPref(getApplicationContext(), PrefData.GENDER_KEY));
        email.setText(PrefData.getStringPref(getApplicationContext(), PrefData.EMAIL_KEY));


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPhoneDialog(email.getText().toString());
            }
        });
        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneDialog(email.getText().toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPhoneDialog(final String email) {
        final Dialog dialog1 = new Dialog(ProfileActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
        dialog1.setContentView(R.layout.update_email_dialog);
        dialog1.setTitle("Update Email");
        dialog1.show();
        dialog1.setCancelable(false);
        final EditText title = (EditText) dialog1.findViewById(R.id.email_et);
        Button cancel = (Button) dialog1.findViewById(R.id.cancel);
        final Button ok = (Button) dialog1.findViewById(R.id.done);

        if(email.length()>0){
            title.setText(email);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                //run the api for email
                //new RegistrationCall().execute("url");
            }
        });
    }


    class RegistrationCall extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ProfileActivity.this, "Please wait...", "");
            progressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... params) {

//            nameValuePairs.add(new BasicNameValuePair("pro_email", email));

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
                JSONObject jsonObject = new JSONObject(jsonResponse);



            } catch (Exception e) {
                Log.d("sunil",e.toString());
            }
        }
    }
}
