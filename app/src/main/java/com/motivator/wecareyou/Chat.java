package com.motivator.wecareyou;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Chat extends Activity {
	
	int sleep = 2000;
	ImageView imvChatScreen;
	FrameLayout frameContainer;
	Fragment fragment;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        
        imvChatScreen= (ImageView)findViewById(R.id.imv_chat_info);
        frameContainer = (FrameLayout)findViewById(R.id.frame_container);
        
        new LongOperation().execute();
        
	}

	
	private class LongOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				Thread.interrupted();
			}
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {
			
//			imvChatScreen.setVisibility(View.GONE);
//			fragment = new ChatFragment();
//			Bundle args = new Bundle();
//			args.putInt(ChatFragment.ARG_OBJECT, 1);
//			fragment.setArguments(args);
//			FragmentManager fragmentManager1 = getFragmentManager();
//			fragmentManager1.beginTransaction().replace(R.id.frame_container, fragment).commit();
			
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}
	
}

