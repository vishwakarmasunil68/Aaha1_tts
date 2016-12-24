package com.motivator.wecareyou.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.motivator.wecareyou.R;

public class ChatFragment extends Fragment {
	
	
	Context mContext;
	ImageView imvChatScreen;
	ListView list;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	mContext = getActivity();
        // Inflate the layout for this fragment
    	View fragmentView = inflater.inflate(R.layout.chat_next, container, false);
    	imvChatScreen= (ImageView)fragmentView.findViewById(R.id.imv_chat_info);
    	
    	return fragmentView;
    }
    
}