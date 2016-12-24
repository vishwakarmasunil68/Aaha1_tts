package com.motivator.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.tamir7.contacts.Contact;
import com.github.tamir7.contacts.Contacts;
import com.github.tamir7.contacts.Query;
import com.google.gson.Gson;
import com.motivator.common.GeneralUtility;
import com.motivator.common.WebServices;
import com.motivator.model.ContactDetails;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 11-11-2016.
 */
public class ContactService extends Service {
    final String TAG = "contactservice";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "service started");
        SynchronizeContacts();
        return START_STICKY;
    }

    private void SynchronizeContacts() {
        Query q = Contacts.getQuery();
        q.include(Contact.Field.DisplayName, Contact.Field.PhoneNumber, Contact.Field.PhoneNormalizedNumber, Contact.Field.Email);
        String user_id= GeneralUtility.getPreferences(getApplicationContext(), "user_id");
        List<Contact> contacts = q.find();
        List<ContactDetails> list=new ArrayList<>();
        for(Contact contact:contacts) {
            Log.e(TAG, new Gson().toJson(contact));
            try {
                JSONObject object=new JSONObject((new Gson().toJson(contact)).toString());
                String name=object.optString("displayName");
                String phoneno="";
                String email="";
                try{
                    email=object.optJSONArray("emails").optJSONObject(0).optString("address");
                }
                catch (Exception e){

                }
                try{
                    phoneno=object.optJSONArray("phoneNumbers").optJSONObject(0).optString("normalizedNumber");
                }
                catch (Exception e){

                }
                ContactDetails cd=new ContactDetails();
                cd.setDisplayname(name);
                cd.setEmail(email);
                cd.setPhonenumber(phoneno);
                list.add(cd);
            } catch (JSONException e) {
                Log.d(TAG,e.toString());
            }
        }
        for(ContactDetails cd:list){
            new RegistrationCall(user_id,cd.getDisplayname(),cd.getPhonenumber(),cd.getPhonenumber()).execute();
        }
        Log.d(TAG,list.toString());
    }
    class RegistrationCall extends AsyncTask<String, Void, String> {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        String jResult;
        String name="";
        String number="";
        String email="";
        String user_id="";

        public RegistrationCall(String user_id,String name,String number,String email){
            this.user_id=user_id;
            this.name=name;
            this.number=number;
            this.email=email;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            nameValuePairs.add(new BasicNameValuePair("con_user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("con_user_name", name));
            nameValuePairs.add(new BasicNameValuePair("con_user_phone", number));
            nameValuePairs.add(new BasicNameValuePair("con_user_email", email));
            nameValuePairs.add(new BasicNameValuePair("con_user_notes", ""));
            nameValuePairs.add(new BasicNameValuePair("con_user_postal_address",""));
            nameValuePairs.add(new BasicNameValuePair("con_user_instant_mess",""));
            nameValuePairs.add(new BasicNameValuePair("con_organization", ""));
            try {
                jResult = WebServices.httpCall("http://www.funhabits.co/aaha/contect_user.php", nameValuePairs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jResult;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);


        }

    }
}
