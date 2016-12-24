package com.motivator.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.motivator.model.HabitModel;
import com.motivator.wecareyou.R;

import java.util.ArrayList;

public class AppsConstant {
	
	public static final String user_name = "user_name";
	//public static boolean toRemoveNote = false;
	public static final int FEEL_ENERGIZED = 1;
	public static final int LOSE_WEIGHT = 2;
	public static final int SLEEP_BETTER = 3;
	
	public static final int RITUAL_IMG = 100;
	
	public static final String IS_RITUAL_ADDED ="IS_RITUAL_ADDED";
	public static String MORNING_RITUAL = "Morning Routine";
	public static String LUNCH_RITUAL = "Afternoon Routine";
	public static String EVENING_RITUAL = "Evening Routine";
	public static final String SELECTED_RITUAL = "selected_ritual";
	public static String RITUAL_TIME = "ritual_time";
	
	public static final String calling_frag = "calling_frag";
	public static final String selected_journey_step = "selected_journey_step";
	public static final String h_id = "h_id";

	public static final String SAVED_USER_NAME = "user_name";
	public static final String SAVED_USER_EMAIL = "user_email";
	public static final String SAVED_USER_ID = "userId";
	public static final String SAVED_USER_IMG_URL = "userImgURL";
	
//	public static String MORNING_RITUAL_IMG = "MORNING_RITUAL_IMG";
//	public static String LUNCH_RITUAL_IMG = "MORNING_RITUAL_IMG";
//	public static String EVENING_RITUAL_IMG = "MORNING_RITUAL_IMG";
	public static final String TOP_IMG ="TOP_IMG";
	
	public static final String Interesting_Journey = "Interesting Journey";
	public static final String A_Pleasant_Night = "A Pleasant Night";
	public static final String Enjoying_Health_Eating = "Enjoying Health Eating";
	public static String SELECTED_JOURNEY =  Interesting_Journey;
	
	public static final String AVS_SOUND = "AVS_SOUND";
	
	public static ArrayList<HabitModel> savedHabitList = new ArrayList<HabitModel>();
	public static ArrayList<HabitModel> habitList = new ArrayList<HabitModel>();
	

	public static final String sreenshot = "sreenshot";
	
	public static String user_mood="neutral";
	
	public static String user_age="";
	public static String user_gender="";
	public static String user_health="";
	/**
	 * setRitualImg
	 * @param r_img
	 * @return
	 */
	public static void saveIntegerToPref(Context context, String key, int value){
		SharedPreferences sp=context.getSharedPreferences("aaha.txt",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putInt(key,value);
		editor.commit();
	}
	public static int getRitualImg(int r_img) {
		switch (r_img) {
		case 1:
			return R.drawable.morning_ritual;
		case 2:
			return R.drawable.lunch_ritual;
		case 3:
			return R.drawable.evening_ritual;
		case 4:
			return R.drawable.morning_ritual;
		case 5:
			return R.drawable.lunch_ritual;
		case 6:
			return R.drawable.evening_ritual;
//		case 7:
//			return R.drawable.read;
//		case 8:
//			return R.drawable.drink_water;
//		case 9:
//			return R.drawable.exercise;

		default:
			return R.drawable.morning_ritual;
		}
	}
	
	/**
	 * 
	 * @param r_img
	 * @return
	 */
	public static int getRitualTopImg(int r_img) {
		switch (r_img) {
		case 1:
			return R.drawable.ritual_top;
		case 2:
			return R.drawable.ritual_top2;
		case 3:
			return R.drawable.ritual_top3;
		case 4:
			return R.drawable.ritual_top4;
		case 5:
			return R.drawable.ritual_top5;
		case 6:
			return R.drawable.ritual_top6;
//		case 7:
//			return R.drawable.read;
//		case 8:
//			return R.drawable.drink_water;
//		case 9:
//			return R.drawable.exercise;

		default:
			return R.drawable.ritual_top;
		}
	}
	
	public static int getHabitIcon(int h_id) {
		switch (h_id) {
		case 1:
			return R.drawable.disconnect;
		case 2:
			return R.drawable.dark_icon;
		case 3:
			return R.drawable.mediate;
		case 4:
			return R.drawable.be_grateful_icon;
		case 5:
			return R.drawable.drink_water;
		case 6:
			return R.drawable.clean_and_tidy_up;
		case 7:
			return R.drawable.read;
		case 8:
			return R.drawable.floass;
		case 9:
			return R.drawable.exercise;
		case 10:
			return R.drawable.shower;
		case 11:
			return R.drawable.adjust_eview_plans;
		case 12:
			return R.drawable.block_istration;
		case 13:
			return R.drawable.call_to_parents;
		case 14:
			return R.drawable.celebrate;
		case 15:
			return R.drawable.check_my_eight;
		case 16:
			return R.drawable.create_a_timeline;
		case 17:
			return R.drawable.drink_tea;
		case 18:
			return R.drawable.eat_a_great_breakfast;

			
		case 19:
			return R.drawable.skip_cabinet_snacking;
		case 20:
			return R.drawable.avoid_the_dirty_dozen;
		case 21:
			return R.drawable.take_the_stairs;
		case 22:
			return R.drawable.avoid_afternoon_caffeine;
		case 23:
			return R.drawable.soak_in_a_bath;
		case 24:
			return R.drawable.take_a_rest_day;
		case 25:
			return R.drawable.dance;
		case 26:
			return R.drawable.play;
		case 27:
			return R.drawable.write_my_stressors;
		case 28:
			return R.drawable.listen_to_music;
		case 29:
			return R.drawable.have_a_date_night;
		case 30:
			return R.drawable.share_my_feelings;
		case 31:
			return R.drawable.share_my_struggle_n_strength;
		case 32:
			return R.drawable.reflect_on_memories_and_experiences;
		case 33:
			return R.drawable.create_my_bucket_list;
		case 34:
			return R.drawable.take_a_personality_test;
		case 35:
			return R.drawable.donate_one_unused_item;
		case 36:
			return R.drawable.share_the_days_high_and_low;
		default:
			return R.drawable.habit_default_icon;
		}
	}
	
	public static int getHabitTop(int h_id) {
		switch (h_id) {
		case 1:
			return R.drawable.disconnect_and_create_top;
		case 2:
			return R.drawable.darker_top;
		case 3:
			return R.drawable.mediate_top;
		case 4:
			return R.drawable.be_grateful_top;
		case 5:
			return R.drawable.drink_water_top;
		case 6:
			return R.drawable.clean_and_tidy_top;
		case 7:
			return R.drawable.read_top;
		case 8:
			return R.drawable.floss_top;
		case 9:
			return R.drawable.exercise_top;
		case 10:
			return R.drawable.shower_top;
		case 11:
			return R.drawable.adjust_and_review_top;
		case 12:
			return R.drawable.block_destruction_top;
		case 13:
			return R.drawable.call_my_parents_top;
		case 14:
			return R.drawable.celebrate_top;
		
		case 15:
			return R.drawable.check_my_weight_top;
		case 16:
			return R.drawable.schedule_in_time_slots_top;
		case 17:
			return R.drawable.drink_tea_top;
		case 18:
			return R.drawable.eat_a_great_breakfast_top;

		case 19:
			return R.drawable.skip_cabinet_snaking_top;
		case 20:
			return R.drawable.avoid_the_dirty_dozen_top;
		case 21:
			return R.drawable.take_the_stair_top;
		case 22:
			return R.drawable.avoid_afternoon_caffeine_top;
		case 23:
			return R.drawable.soak_in_a_bath_top;
		case 24:
			return R.drawable.take_a_rest_day_top;
		case 25:
			return R.drawable.dance_top;
		case 26:
			return R.drawable.play_top;
		case 27:
			return R.drawable.write_stories_top;
		case 28:
			return R.drawable.listen_to_music_top;
		case 29:
			return R.drawable.have_a_date_night_top;
		case 30:
			return R.drawable.share_my_feeling_top;
		case 31:
			return R.drawable.share_my_struggle_top;
		case 32:
			return R.drawable.reflect_on_memory_top;
		case 33:
			return R.drawable.create_my_bucket_list_top;
		case 34:
			return R.drawable.take_a_personality_test_top;
		case 35:
			return R.drawable.donate_one_unused_item_top;
		case 36:
			return R.drawable.share_a_day_high_low_top;
		default:
			return R.drawable.habit_top;
		}
	}
	
	public static int getHabitImg(int h_id) {
		switch (h_id) {
		case 1:
			return R.drawable.disconnect_andconnect1;
		case 2:
			return R.drawable.darker1;
		case 3:
			return R.drawable.mediate1;
		case 4:
			return R.drawable.be_grateful1;
		case 5:
			return R.drawable.drink_water1;
		case 6:
			return R.drawable.clean_and_tidy_up1;
		////
		case 7:
			return R.drawable.learn_and_study1;
		case 8:
			return R.drawable.floss1;
		case 9:
			return R.drawable.exercise1;
		case 10:
			return R.drawable.showe1;
//		case 11:
//			return R.drawable.morning_pages1;
		case 12:
			return R.drawable.block_destraction1;
		case 13:
			return R.drawable.call_mother_father1;
		//
		case 14:
			return R.drawable.blank_image;
		
		case 15:
			return R.drawable.weigh_myself1;
		case 16:
			return R.drawable.schedule_in_time_slot1;
//		case 17:
//			return R.drawable.drink_water1;
		case 18:
			return R.drawable.eat_great_breakfast1;

		case 19:
			return R.drawable.skip_cabinet_snaking1;
		case 20:
			return R.drawable.avoid_the_dirty_dozen1;
		case 21:
			return R.drawable.take_the_stair1;
		case 22:
			return R.drawable.avoid_afternoon_caffeine1;
		case 23:
			return R.drawable.soak_in_a_bath1;
		case 24:
			return R.drawable.take_a_rest_day1;
		case 25:
			return R.drawable.dance1;
		case 26:
			return R.drawable.play1;
		case 27:
			return R.drawable.write_stories1;
		case 28:
			return R.drawable.listen_to_music1;
		case 29:
			return R.drawable.have_a_date_night1;
		case 30:
			return R.drawable.share_my_feeling1;
		case 31:
			return R.drawable.share_my_struggle1;
		case 32:
			return R.drawable.reflect_on_memory1;
		case 33:
			return R.drawable.create_my_bucket_list1;
		case 34:
			return R.drawable.take_a_personality_test1;
		case 35:
			return R.drawable.donate_one_unused_item1;
		case 36:
			return R.drawable.share_a_day_high_low1;

		default:
			return R.drawable.blank_image;
		}
	}
	
	public static String getHabitBackground(int h_id) {
		switch (h_id) {
		case 1:
			return "#f36523";
		case 2:
			return "#252525";
		case 3:
			return "#00a69c";
		case 4:
			return "#2e3192";
		case 5:
			return "#1d9db6";
		case 6:
			return "#ed008c";
		case 7:
			return "#00746b";
		case 8:
			return "#f36523";
		case 9:
			return "#2e3192";
		case 10:
			return "#458ccc";
//		case 11:
//			return "#00a652";
		case 12:
			return "#9d0b0e";
		case 13:
			return "#00746b";

//		case 14:(Celebration background) handled in java file	
//			return "#2e3192";

		case 15:
			return "#01a479";
		case 16:
			return "#a3610a";
//		case 17:
//			return R.drawable.drink_water1;
		case 18:
			return "#9d8902";

		case 19:
			return "#99cc33";
		case 20:
			return "#ff9933";
		case 21:
			return "#ff3333";
		case 22:
			return "#663300";
		case 23:
			return "#003399";
		case 24:
			return "#33cccc";
		case 25:
			return "#99cc33";
		case 26:
			return "#990033";
		case 27:
			return "#ff6633";
		case 28:
			return "#006666";
		case 29:
			return "#ff0066";
		case 30:
			return "#006666";
		case 31:
			return "#006633";
		case 32:
			return "#996600";
		case 33:
			return "#3399cc";
		case 34:
			return "#330066";
		case 35:
			return "#ff9933";
		case 36:
			return "#663399";

		default:
			return "#1d9db6";
		}
	}
	
	
	public static String getHabitMusic(int h_id) {
		switch (h_id) {
		case 1:
			return "music/play_disconnect.ogg";
//		case 2:
//			return "music/play_disconnect.ogg";
		case 3:
			return "music/play_meditate.ogg";
		case 4:
			return "music/play_begrateful.ogg";
		case 5:
			return "music/play_drinkwater.ogg";
//		case 6:
//			return "music/play_disconnect.ogg";
//		case 7:
//			return "music/play_disconnect.ogg";
//		case 8:
//			return "music/play_disconnect.ogg";
		case 9:
			return "music/play_exercise.ogg";
//		case 10:
//			return "music/play_disconnect.ogg";
//		case 11:
//			return "music/play_disconnect.ogg";
//		case 12:
//			return "music/play_disconnect.ogg";
//		case 13:
//			return "music/play_disconnect.ogg";
//		case 14:
//			return "music/play_disconnect.ogg";
//		case 15:
//			return "music/play_disconnect.ogg";
		case 16:
			return "music/play_writetodo.ogg";
//		case 17:
//			return "music/play_disconnect.ogg";
		case 18:
			return "music/play_breakfast_alt.ogg";
//
//		case 19:
//			return "music/play_disconnect.ogg";
//		case 20:
//			return "music/play_disconnect.ogg";
//		case 21:
//			return "music/play_disconnect.ogg";
//		case 22:
//			return "music/play_disconnect.ogg";
//		case 23:
//			return "music/play_disconnect.ogg";
//		case 24:
//			return "music/play_disconnect.ogg";
//		case 25:
//			return "music/play_disconnect.ogg";
//		case 26:
//			return "music/play_disconnect.ogg";
//		case 27:
//			return "music/play_disconnect.ogg";
//		case 28:
//			return "music/play_disconnect.ogg";
//		case 29:
//			return "music/play_disconnect.ogg";
//		case 30:
//			return "music/play_disconnect.ogg";
//		case 31:
//			return "music/play_disconnect.ogg";
//		case 32:
//			return "music/play_disconnect.ogg";
//		case 33:
//			return "music/play_disconnect.ogg";
//		case 34:
//			return "music/play_disconnect.ogg";
//		case 35:
//			return "music/play_disconnect.ogg";
//		case 36:
//			return "music/play_disconnect.ogg";

		default:
			return "music/play_disconnect.ogg";
		}
	}
}
