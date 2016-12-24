package com.motivator.careplan;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.motivator.support.FileUtils;
import com.motivator.wecareyou.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends Activity {
    private final String TAG="contactactivity";
    List<String> all_music_files=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
//        Query q = Contacts.getQuery();
//        q.include(Contact.Field.DisplayName, Contact.Field.PhoneNumber, Contact.Field.PhoneNormalizedNumber, Contact.Field.Email);
//
//        List<Contact> contacts = q.find();
//        Log.e(TAG, new Gson().toJson(contacts));
//        List<ContactDetails> list=new ArrayList<>();
//        for(Contact contact:contacts) {
//            Log.e(TAG, new Gson().toJson(contact));
//            try {
//                JSONObject object=new JSONObject((new Gson().toJson(contact)).toString());
//                String name=object.optString("displayName");
//                String phoneno="";
//                String email="";
//                try{
//                    email=object.optJSONArray("emails").optJSONObject(0).optString("address");
//                }
//                catch (Exception e){
//
//                }
//                try{
//                    phoneno=object.optJSONArray("phoneNumbers").optJSONObject(0).optString("normalizedNumber");
//                }
//                catch (Exception e){
//
//                }
//                ContactDetails cd=new ContactDetails();
//                cd.setDisplayname(name);
//                cd.setEmail(email);
//                cd.setPhonenumber(phoneno);
//                list.add(cd);
//            } catch (JSONException e) {
//                Log.d(TAG,e.toString());
//            }
//        }
//        Log.d(TAG,list.toString());
        getFilesFromDir(FileUtils.BASE_FILE_PATH);
        Log.d(TAG,"total file:-"+all_music_files.size());
        for(String file_path:all_music_files){
            Log.d(TAG,file_path);
        }
    }
    public void getFilesFromDir(String dir){
        File[] list_files=new File(dir).listFiles();
        for(File f:list_files){
            if(f.isDirectory()){
                getFilesFromDir(f.toString());
            }
            else{
                all_music_files.add(f.toString());
            }
        }
    }
}
