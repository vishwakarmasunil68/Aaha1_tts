package com.motivator.wecareyou;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.motivator.common.AppsConstant;
import com.motivator.common.GeneralUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TTSTesting extends Activity{
//	TextToSpeech tts;
	Button btn;
//	private TextToSpeech tts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tts_testinh);
		btn=(Button) findViewById(R.id.btn);
//		tts = new TextToSpeech(this, this);
		String s="this is sunil vishwakarma";
		if (GeneralUtility.getPreferencesBoolean(getApplicationContext(), AppsConstant.AVS_SOUND)) {
			if(MyApplication.tts_initialized){
				MyApplication.tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
			}
			new CountDownTimer(3000, 100) {

				public void onTick(long millisUntilFinished) {
				}

				public void onFinish() {
				}
			}.start();

		}
//		btn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				if(MyApplication.tts_initialized) {
////					MyApplication.tts.speak("hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma hey this is sunil kumar vishwakarma", TextToSpeech.QUEUE_FLUSH, null);
////				}
////				else{
////					Log.d("sunil","ttsinitialized failed");
////				}
//				runAPI();
////				tts = new TextToSpeech(getApplicationContext(), new OnInitListener() {
////
////					@Override
////					public void onInit(int status) {
////						// TODO Auto-generated method stub
////						convertTextToSpeech(status, "hello this is sunil kumar vishwakarma");
////					}
////
////				});
////				speakOut();
//			}
//		});
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (MyApplication.tts != null) {
			MyApplication.tts.stop();
		}
	}

	public void runAPI(){
		StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://pornvideocum.com/user/login.php",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							Log.d("sunil",response);

							JSONObject obj=new JSONObject(response);
							String success=obj.optString("success");
							if(success.equals("true")){

							}
							else{
								Toast.makeText(TTSTesting.this,"Invalid User Id",Toast.LENGTH_LONG).show();
							}



						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d("sunil", "" + error);

					}
				}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> params = new HashMap<String, String>();
				params.put("log_email", "ram@gmail.com");
				params.put("log_password", "qwerty");
				params.put("log_device_token", "avnalkjvakdv.akbjkva vabvnaohvoi");
				return params;
			}

		};
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);

	}
	//	 private void convertTextToSpeech(int status, String text)
//	 {
//		 if (status == TextToSpeech.SUCCESS)
//		 {
//			 int lang = tts.setLanguage(Locale.US);
//			 if (lang == TextToSpeech.LANG_MISSING_DATA
//					 || lang == TextToSpeech.LANG_NOT_SUPPORTED) {
//				 Log.e("error", "This Language is not supported");
//			 }
//			 else {
//				 if (null == text || "".equals(text)) {
//					 text = "";
//				 }
//				 tts.setPitch(1.0f);
//				 tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//			 }
//		 } else {
//			 Log.e("error", "Initilization Failed!");
//		 }
//	 }

//	@Override
//	public void onDestroy() {
//		// Don't forget to shutdown tts!
//		if (tts != null) {
//			tts.stop();
//			tts.shutdown();
//		}
//		super.onDestroy();
//	}
//
//	@Override
//	public void onInit(int status) {
//
//		if (status == TextToSpeech.SUCCESS) {
//
//			int result = tts.setLanguage(Locale.US);
//
//			if (result == TextToSpeech.LANG_MISSING_DATA
//					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
//				Log.e("TTS", "This Language is not supported");
//			} else {
////				btnSpeak.setEnabled(true);
//				speakOut();
//			}
//
//		} else {
//			Log.e("TTS", "Initilization Failed!");
//		}
//
//	}
//
//	private void speakOut() {
//
//		String text = "hey this is sunil kumar vishwakarma and he is a good android developer.";
//		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//	}


	//Generating Random Number between range
//	 Random rn = new Random();
//		int range = 10 - 1 + 1;
//		int randomNum =  rn.nextInt(range) + 1;
//		System.out.println(randomNum);
	 
	 
	 
	 //Life Encouragements
//	 public String ttsspeaking(){
//		 Random rn = new Random();
//		 int range = 4 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//		 switch (randomNum) {
//		case 1:			
//			return "True strength is measured in how you move on in those bad times. Not how you stand in the good ones. You're doing good.";
//		case 2:			
//			return "Life is tough. There are times when nothing seems to go right. And many of those days, it can be tempting to just throw in the towel. But keep at it. You'll pull through";
//		case 3:			
//			return "You are stronger than you think. You are having a terrible day, but you still checked in. That takes more than most people have";
//		case 4:	
//			return "Just because today is bad, does not mean tomorrow won't be the best day of your life. You just need to make it there";
//		default:
//			return "Just because today is bad, does not mean tomorrow won't be the best day of your life. You just need to make it there";
//		}
//	 }
	 
	 
	 //7.2 Generic chat about updates 
//	 public String ttsspeaking(){
//		 Random rn = new Random();
//		 int range = 18 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//		 switch (randomNum) {
//		case 1:			
//			return "Looks like you have an admirer! You got a message from [Name] while you were out.";
//		case 2:			
//			return "I left your mail on the table";
//		case 3:			
//			return "Here you go. It looks important";
//		case 4:	
//			return "Let's see...  an invitation to a yacht party... a multimillion dollar trade agreement.... oh! here's some stuff for you too. Here you go";
//		case 5:			
//			return "Now what do we have here? ";
//		case 6:			
//			return "Your stalkers left you a message.";
//		case 7:			
//			return "something to do. You have some updates.";
//		case 8:	
//			return "Maybe you'll find these stimulating";
//		case 9:			
//			return "And now a word from our sponsors... Kidding...  Here's your updates";
//		case 10:			
//			return "Someone out there cares about you. There's a message.";
//		case 11:			
//			return "Maybe these will brighten your day a little.";
//		case 12:	
//			return "It's not some sweet fuzzy creature, but this should still lighten your day a little";
//		case 13:			
//			return "I bet you could use a small distraction.";
//		case 14:			
//			return "At least none of this is junk mail... right?";
//		case 15:			
//			return "There are some updates and messages for you. No rush.";
//		case 16:	
//			return "There are some updates and messages for you. No rush.";
//		case 17:			
//			return "waiting for you.";
//		case 18:			
//			return "Don't forget these in all the excitement.";
//		case 19:			
//			return "These messages will self destruct in' No idea. So don't let them sit too long";
//		default:
//			return "These messages will self destruct in' No idea. So don't let them sit too long";
//		}
//	 }
	 
	 
	 
	 //7.3 Generic chit-chat
//	 public String ttsspeaking(){
//	 Random rn = new Random();
//	 int range = 5 - 1 + 1;
//		int randomNum =  rn.nextInt(range) + 1;
//		System.out.println(randomNum);
//	 switch (randomNum) {
//	case 1:			
//		return "So how have you been today? Did you try anything new?";
//	case 2:			
//		return "I really enjoy our meetups. You're pretty cool.";
//	case 3:			
//		return "I really need a day, between Saturday and Sunday";
//	case 4:	
//		return "You are never too old to chase a new goal, or dream a new dream.";
//	case 5:	
//		return "Don't worry about what people think. Most of them don't do it very often.";
//	default:
//		return "So how have you been today? Did you try anything new?";
//	}
// }
	 
	 //7.3.1 Time-based chat
//	 
//	 public String getTimeBasedChats(String time){
//		 int timeInt=0;
//		 if(time.equals("morning")){
//			 timeInt=0;
//		 }
//		 else{
//			 if(time.equals("afternoon")){
//				 timeInt=1;
//			 }
//			 else{
//				 if(time.equals("evening")){
//					 timeInt=2;
//				 }
//			 }
//		 }
//		 switch (timeInt) {
//			case 0:			
//				return ttsspeaking0();
//			case 1:			
//				return ttsspeaking1();
//			case 2:			
//				return ttsspeaking2();
//			default:
//				return ttsspeaking0();
//			}
//	 }
//	 public String ttsspeaking0(){
//		Random rn = new Random();
//		int range = 3 - 1 + 1;
//		int randomNum =  rn.nextInt(range) + 1;
//		System.out.println(randomNum);
//		 switch (randomNum) {
//		case 1:			
//			return "Good Morning";
//		case 2:			
//			return "Time to get started on your day. ";
//		case 3:			
//			return "I was up extra early this morning. Even the coffee looked surprised to see me";
//		default:
//			return "Good Morning";
//	 	}
//	 }
//	 public String ttsspeaking1(){
//			Random rn = new Random();
//			int range = 4 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//			 switch (randomNum) {
//			case 1:			
//				return "The day is half over. You can make it.";
//			case 2:			
//				return "The day is only half over, and I'm ready to go back to bed.";
//			case 3:			
//				return "The world would be a better place, with more coffee breaks.";
//			case 4:	
//				return "It's too early to call it a day, and too late to go back to bed.";
//			default:
//				return "The day is half over. You can make it.";
//		 	}
//		 }
//	 public String ttsspeaking2(){
//			Random rn = new Random();
//			int range = 5 - 1 + 1;
//			int randomNum =  rn.nextInt(range) + 1;
//			System.out.println(randomNum);
//			 switch (randomNum) {
//			case 1:			
//				return "Just a little bit more to go.";
//			case 2:			
//				return "No rest for the wicked. But there's no rest for the rest of us either, right?";
//			case 3:			
//				return "If this day gets any longer, we'll have to make a sequel";
//			case 4:	
//				return "Finally... it's almost over! I can hear my bed crying for me";
//			case 5:	
//				return "Is it bedtime yet?";
//			case 6:
//				return "Where did the day go? I could have sworn there was more of it left";
//			case 7:
//				return "What's for dinner?";
//			default:
//				return "Just a little bit more to go.";
//		 	}
//		 }
	 
	 
	 
}
