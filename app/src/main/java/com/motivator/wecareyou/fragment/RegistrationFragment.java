package com.motivator.wecareyou.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.motivator.common.GeneralUtility;
import com.motivator.wecareyou.KickStart;
import com.motivator.wecareyou.R;

public class RegistrationFragment extends Fragment implements OnClickListener {
	
	int journey;
	Context mContext;
	KickStart kickStart;
	public static final String JOURNEY_OBJECT = "object";

	//UI VIEWS
	TextView tvUserName, tvEnterUserName, tvEnterEmail;
	EditText edtUserName, edtEmail;
	Button btnNext, btnLetsDoIt, btnGoogle, btnFacebook;
	LinearLayout llSocial;
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
    	mContext = getActivity();
    	kickStart = new KickStart();
        
        // Inflate the layout for this fragment
    	View fragmentView = inflater.inflate(R.layout.reg_frag, container, false);
    	journey = getArguments().getInt(JOURNEY_OBJECT);

    	tvUserName = (TextView)fragmentView.findViewById(R.id.tv_user_name);
        edtUserName = (EditText)fragmentView.findViewById(R.id.edt_user_name);
        tvEnterUserName = (TextView)fragmentView.findViewById(R.id.tv_enter_user_name);
        edtEmail = (EditText)fragmentView.findViewById(R.id.edt_email_id);
        tvEnterEmail = (TextView)fragmentView.findViewById(R.id.tv_enter_email);

        llSocial = (LinearLayout)fragmentView.findViewById(R.id.ll_social);
        btnNext = (Button)fragmentView.findViewById(R.id.btn_next);
        btnLetsDoIt = (Button)fragmentView.findViewById(R.id.btn_lets_do_it);
        btnGoogle = (Button)fragmentView.findViewById(R.id.btn_google);
        btnFacebook = (Button)fragmentView.findViewById(R.id.btn_facebook_login);

        btnNext.setOnClickListener(this);
        btnLetsDoIt.setOnClickListener(this);
        btnGoogle.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);

      /*  mGoogleApiClient = new GoogleApiClient.Builder(mContext).addConnectionCallbacks(this)
        		.addOnConnectionFailedListener(this).addApi(Plus.API, Plus.PlusOptions.builder().build())
        		.addScope(Plus.SCOPE_PLUS_LOGIN).build();*/

        return fragmentView;
    }


	/*private void registrationValidation()
	{
		emailId = edtEmail.getText().toString();
		userName = edtUserName.getText().toString();
		if(emailId==null || emailId.length()<=0 || !(GeneralUtility.isValidEmail1(emailId)))
			Toast.makeText(mContext, R.string.enter_email, 5).show();
		else if(userName==null || userName.length()<=0)
			Toast.makeText(mContext, R.string.enter_user_name, 5).show();
		else
		{
			if(GeneralUtility.isNetworkAvailable(mContext))
		    	new RegistrationCall().execute(WebServices.ACCESS_LOGIN, userName,emailId);
		    else
		    	GeneralUtility.PopUpInfo(mContext, R.string.network_failure, R.string.no_internet);
		}


	}*/


@Override
public void onClick(View v)
{
	String userName, emailId;
	switch (v.getId())
	{

	case R.id.btn_next:

		userName = edtUserName.getText().toString();
		if(userName !=null && userName.trim().length()>0)
		{
			tvEnterEmail.setVisibility(View.GONE);

			edtEmail.setVisibility(View.VISIBLE);
			llSocial.setVisibility(View.VISIBLE);

			btnLetsDoIt.setVisibility(View.VISIBLE);
			btnNext.setVisibility(View.GONE);
		}
		else
			tvEnterEmail.setVisibility(View.VISIBLE);
		break;

	case R.id.btn_lets_do_it:
		emailId = edtEmail.getText().toString();
		userName = edtUserName.getText().toString();
		if(userName==null || userName.length()<=0)
			Toast.makeText(mContext, R.string.enter_user_name, 5).show();
		else if(emailId==null || emailId.length()<=0 || !(GeneralUtility.isValidEmail1(emailId)))
			Toast.makeText(mContext, R.string.enter_email, 5).show();
		else
		{
			//kickStart.registrationValidation(userName, emailId);
		}
		break;

	case R.id.btn_google:
//		kickStart.googlePlusLogin();
		break;
	case R.id.btn_facebook_login:
		kickStart.loginToFacebook();
		break;

	}
	
}

	
 /*@Override
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
