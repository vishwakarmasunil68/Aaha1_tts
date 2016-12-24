package com.motivator.common;

import java.io.ByteArrayOutputStream;

import javax.crypto.Mac;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.motivator.model.JourneyData;
import com.motivator.wecareyou.Goal;
import com.motivator.wecareyou.R;

public class FacebookWallPost {

	@SuppressWarnings("deprecation")
	public void loginAndPostToWall(final Activity mActivity, final Facebook facebook, final int habitId) {

		// mPrefs = getPreferences(MODE_PRIVATE);
		
		facebook.authorize(mActivity, new String[] {"publish_actions" },new DialogListener()
		{

			@Override
			public void onCancel() {
				// Function to handle cancel event
			}
			
			@Override
			public void onComplete(Bundle values) {
				// Function to handle complete event
				// Edit Preferences and update facebook acess_token
				
//				  SharedPreferences.Editor editor = mPrefs.edit();
//				  editor.putString("access_token", facebook.getAccessToken());
//				  editor.putLong("access_expires", facebook.getAccessExpires()); editor.commit();
				  publishStory(mActivity, facebook, habitId);
				
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
	public static void publishStory(final Activity mActivity, Facebook facebook, int habitId) {

	    Session session = facebook.getSession();

	    if (session != null){
	        // Check for publish permissions    
	       /* List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(getActivity(), PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }*/
	    	
	    	Bitmap bi = BitmapFactory.decodeResource(mActivity.getResources(),R.drawable.ritual_top);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	        byte[] data =  baos.toByteArray();

			Bundle postParams = new Bundle();
	        postParams.putString("name", "New Challenge :"+JourneyData.getGoalTitle(habitId));
	        postParams.putString("description", "Do it three times this week to succeed");
	        postParams.putString("caption", "https://play.google.com/store/apps/");
	        postParams.putString("link", "https://play.google.com/store/apps/");
	        postParams.putString("picture", "http://oldmaker.com/glamberry_v2/newadmin/resources/11/1.jpg");
	       // postParams.putByteArray("picture", data);
	        	         //Bitmap image = 
//    		SharePhoto photo = new SharePhoto.Builder()
//    		        .setBitmap(image)
//    		        .build();
//    		SharePhotoContent content = new SharePhotoContent.Builder()
//    		        .addPhoto(photo)
//    		        .build();

	        
//	        AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
//	        mAsyncRunner.request(null, postParams, "POST", new SampleUploadListener(),null);
	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                    Toast.makeText(mActivity, "Shared on Facebook", 5).show();
	                } catch (JSONException e) {
	                	e.printStackTrace();
	                   // Log.i(TAG,"JSON error "+ e.getMessage());
	                	Toast.makeText(mActivity, R.string.something_went_wrong, 5).show();
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                   // debug.print("erreur");
	                } 
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
}
