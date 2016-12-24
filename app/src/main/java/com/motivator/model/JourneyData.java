package com.motivator.model;

import java.util.ArrayList;

import com.motivator.database.TableAttributes;
import com.motivator.wecareyou.R;


public class JourneyData 
{
	//Drink WaterText
	static final String letter_drink_water = "Get your morning ritual in place";
	static final String  goal_drink_water = "Drink water for 3-days in a row";
	static final String  action_drink_water = "Buy a Water bottle to make it easier on yourself to drink water";
	static final String  motivator_drink_water = "The Secret Google Experiment that can make you drink more water";
	static final String  action2_drink_water = "Put the water bottle by your bedside";
	
	//Great BreakFast Text
	static final String letter_breakfast = "Get your morning ritual in place";
	static final String  goal_breakfast = "Get a Great BreakFast for 3-days in a row";
	static final String  action_breakfast = "Buy a Fruits, Eggs & Mixed nuts for the week";
	static final String  motivator_breakfast = "Healthy Breakfast Recipes for people in a Hurry";
	static final String  action2_breakfast = "Remove cookies, crackers and junk food from your home";
	
	static final String  motivator2_breakfast = "Prepare Yourself for Success";
	static final String  motivator3_breakfast = "Healthy & Energizing Breakfast Recipes";
	
	//danceYourWay Text
	static final String letter_dance = "Seize the day!";
	static final String  goal_dance = "Exercise 3-times in a week";
	static final String  action_dance = "Decide which exercise to do and prepare ahead";
	static final String  motivator_dance = "Start Small, Very Small";
	static final String  action2_dance = "Reduce Activation Energy to start Exercising";
	
	static final String  motivator2_dance = "Excercise that you can do in the morning";
	static final String  motivator3_dance = "Why should you exercise in the morning?";
	static final String  motivator4_dance = "Don't Break the Chain";
	static final String  motivator5_dance = "Two strange ways (that work) to make exercising into a habit";
	static final String  motivator6_dance = "Let's get serious";
	
	//Golde goal Text
	static final String letter_goldenGoal = "Wrap-up : Week one Golden Triangle";
	static final String  goal_goldenGoal = "Exercise, Drink Water & Get a Great Breakfast";
	
	//Secret Text
	static final String letter_secret = "Get your morning ritual in place";
	static final String  goal_secret = "Celebrate for 3-days in a row";
	static final String  action_secret = "Choose Your Celebration";
	
	public static int getJourneySteps(int id)
	{
		switch (id) {
		
		case TableAttributes.drinkWaterId:
			return 5;
		case TableAttributes.greatBreakFastID:
			return 7;
		case TableAttributes.danceYourWayID:
			return 10;
		case TableAttributes.goldenChallengeID:
			return 2;
		case TableAttributes.SecretLetterID:
			return 3;
		default :
			return 5;
		}
	}
	public static ArrayList<String> getJourneyStepsText(int id)
	{
		ArrayList<String> journeySteps = new ArrayList<String>();
		if(id == TableAttributes.drinkWaterId)
		{
			journeySteps.add(letter_drink_water);
			journeySteps.add(goal_drink_water);
			journeySteps.add(action_drink_water);
			journeySteps.add(motivator_drink_water);
			journeySteps.add(action2_drink_water);
		}
		else if(id == TableAttributes.greatBreakFastID)
		{
			journeySteps.add(letter_breakfast);
			journeySteps.add(goal_breakfast);
			journeySteps.add(action_breakfast);
			journeySteps.add(motivator_breakfast);
			journeySteps.add(action2_breakfast);
			
			journeySteps.add(motivator2_breakfast);
			journeySteps.add(motivator3_breakfast);
		}
		else if(id == TableAttributes.danceYourWayID)
		{
			journeySteps.add(letter_dance);
			journeySteps.add(goal_dance);
			journeySteps.add(action_dance);
			journeySteps.add(motivator_dance);
			journeySteps.add(action2_dance);
			
			journeySteps.add(motivator2_dance);
			journeySteps.add(motivator3_dance);
			journeySteps.add(motivator4_dance);
			journeySteps.add(motivator5_dance);
			journeySteps.add(motivator6_dance);
		}
		
		else if(id == TableAttributes.goldenChallengeID)
		{
			journeySteps.add(letter_goldenGoal);
			journeySteps.add(goal_goldenGoal);
		}
		
		else if(id == TableAttributes.SecretLetterID)
		{
			journeySteps.add(letter_secret);
			journeySteps.add(goal_secret);
			journeySteps.add(action_secret);
		}
		return journeySteps;
				
	}
	
	public static String getGoalOnCard(int id)
	{
		String result = "" ;
		switch (id) {
		case TableAttributes.drinkWaterId:
			result = goal_drink_water;
			break;
			
		case TableAttributes.greatBreakFastID:
			result = goal_breakfast;
			break;
			
		case TableAttributes.danceYourWayID:
			result = goal_dance;			
			break;
			
		case TableAttributes.goldenChallengeID:
			result = goal_goldenGoal;
			break;
			
		case TableAttributes.SecretLetterID:
			result = goal_secret;
			break;

		default:
			result = "";
			break;
		}
		return result;
	}
	
	public static String getActionOnCard(int id, int num)
	{
		String result = "" ;
		switch (id) {
		case TableAttributes.drinkWaterId:
			result = num==1?action_drink_water:action2_drink_water;
			break;
			
		case TableAttributes.greatBreakFastID:
			result = num==1?action_breakfast:action2_breakfast;
			break;
			
		case TableAttributes.danceYourWayID:
			result = num==1?action_dance:action2_dance;
			
			break;
			
		case TableAttributes.goldenChallengeID:
			result = "";
			break;
			
		case TableAttributes.SecretLetterID:
			result = num==1?action_secret:"";
			break;

		default:
			result = "";
			break;
		}
		return result;
				
	}
	
	public static String getMotivatorOnCard(int id, int num)
	{
		String result = "" ;
		switch (id) {
		case TableAttributes.drinkWaterId:
			if(num==1)
				result = motivator_drink_water;
			else 
				result= "";
			break;
			
		case TableAttributes.greatBreakFastID:
			if(num==1)
				result = motivator_breakfast;
			else if(num==2)
				result = motivator2_breakfast;
			else if(num==3)
				result = motivator3_breakfast;
			else
				result= "";
			break;
			
		case TableAttributes.danceYourWayID:
			if(num==1)
				result = motivator_dance;
			else if(num==2)
				result = motivator2_dance;
			else if(num==3)
				result = motivator3_dance;
			if(num==4)
				result = motivator4_dance;
			else if(num==5)
				result = motivator5_dance;
			else if(num==6)
				result = motivator6_dance;
			else
				result= "";
			break;
			
		case TableAttributes.goldenChallengeID:
			result = "";
			break;
			
		case TableAttributes.SecretLetterID:
			result = "";
			break;

		default:
			result = "";
			break;
		}
		return result;
				
	}
	
	public static String getJourneyTitle(int id)
	{
		String title = "";
		switch (id) {
		case TableAttributes.drinkWaterId:
			title = "Drink Water";
			return title;

		case TableAttributes.greatBreakFastID:
			title = "Great Breakfast";
			return title;
		case TableAttributes.danceYourWayID:
			title = "Dance Your Way";
			return title;
			
		case TableAttributes.goldenChallengeID:
			title = "Golden Challenge";
			return title;
		case TableAttributes.SecretLetterID:
			title = "Secret Letter";
			return title;
			
		default:
			return title;
		}
	}
	/**
	 * Returns the tile to be display in goal activity
	 * @param id
	 * @return
	 */
	public static String getGoalTitle(int id)
	{
		String goal = "";
		switch (id) {
		case TableAttributes.drinkWaterId:
			goal = "Drink Water";
			return goal;

		case TableAttributes.greatBreakFastID:
			goal = "Great Breakfast";
			return goal;
		case TableAttributes.danceYourWayID:
			goal = "Dance Your Way";
			return goal;
			
		case TableAttributes.goldenChallengeID:
			goal = "Golden Challenge";
			return goal;
		case TableAttributes.SecretLetterID:
			goal = "Secret Letter";
			return goal;
			
		default:
			return goal;
		}
	}
	public static String getGoalText(int id)
	{
		String goal = "";
		switch (id) {
		case TableAttributes.drinkWaterId:
			goal = "For the next 3 days, Drink Water when you wake up to kickstart yout body and start your day with a success!";
			break;

		case TableAttributes.greatBreakFastID:
			goal = "Eat a great breakfast in the morning to feel energized for the whole day";
			break;
			
		case TableAttributes.danceYourWayID:
			goal = "Eat a great breakfast in the morning to feel energized for the whole day";
			break;
			
		case TableAttributes.goldenChallengeID:
			goal = "Get the Golden Triangle in place.";
			break;
		
		case TableAttributes.SecretLetterID:
			goal = "Celebrate immediately after completing a healthy habit creates a memory imprint in your mind." +
					" Over time you learn to associate the habit with the positive emotion.";
			break;
		default:
			break;
		}
		return goal;
	}

	/**
	 * Returns the tile to be display in onTimeActivity
	 * @param id
	 * @return
	 */
	public static String getActionTitle(int id)
	{
		String goal = "";
		switch (id) {
		case TableAttributes.drinkWaterId:
			goal = "Buy a water bottle";
			break;

		case TableAttributes.greatBreakFastID:
			goal = "Great Breakfast";
			break;
			
		case TableAttributes.danceYourWayID:
			goal = "Buy a water bottle";
			break;
		case TableAttributes.SecretLetterID:
			goal = "Choose your Celebration";
			break;
		default:
			break;
		}
		return goal;
	}
	
	public static int getJourneyImg(int id) {
		switch (id) {
		case TableAttributes.drinkWaterId:
			return R.drawable.drink_water_journey1;
		case TableAttributes.greatBreakFastID:
			return R.drawable.eat_great_breakfast1;
		case TableAttributes.danceYourWayID:
			return R.drawable.dance1;
		case TableAttributes.goldenChallengeID:
			return R.drawable.golden_challenge;
//		case TableAttributes.SecretLetterID:
//			return R.drawable.yoga1;	
		
		default:
			return R.drawable.drink_water1;
		}
	}
	public static String getJourneyBackground(int id) {
		switch (id) {
		
		case TableAttributes.drinkWaterId:
			return "#1d9db6";
		case TableAttributes.greatBreakFastID:
			return "#9d8902";
		case TableAttributes.danceYourWayID:
			return "#99cc33";
		case TableAttributes.goldenChallengeID:
			return "#33cc66";
		case TableAttributes.SecretLetterID:
			return "#33cccc";
			
		default:
			return "#1d9db6";
			
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static String getActionBackground(int id) {
		switch (id) {
		
		case TableAttributes.drinkWaterId:
			return "#66ccff";
		case TableAttributes.greatBreakFastID:
			return "#66ccff";
		case TableAttributes.danceYourWayID:
			return "#99cc33";
		case TableAttributes.goldenChallengeID:
			return "#33cc66";
		case TableAttributes.SecretLetterID:
			return "#33cccc";
			
		default:
			return "#1d9db6";
			
		}
	}
	public static int getActionImg(int id) {
		switch (id) {
		case TableAttributes.drinkWaterId:
			return R.drawable.bottle_image;
		case TableAttributes.greatBreakFastID:
			return R.drawable.fruits;
		case TableAttributes.danceYourWayID:
			return R.drawable.dance1;
		case TableAttributes.goldenChallengeID:
			return R.drawable.golden_challenge;
//		case TableAttributes.SecretLetterID:
//			return R.drawable.yoga1;	
		
		default:
			return R.drawable.drink_water1;
		}
	}
	
	public static int getMotivatorImg(int id) {
		switch (id) {
		case TableAttributes.drinkWaterId:
			return R.drawable.letter_top;
//		case TableAttributes.greatBreakFastID:
//			return R.drawable.eat_great_breakfast1;
//		case TableAttributes.danceYourWayID:
//			return R.drawable.dance1;
//		case TableAttributes.goldenChallenge:
//			return R.drawable.eat_great_breakfast1;
//		case TableAttributes.SecretLetterID:
//			return R.drawable.yoga1;	
		
		default:
			return R.drawable.letter_top;
		}
	}
	
	public static int getNoteImg(int id) {
		switch (id) {
		case TableAttributes.drinkWaterId:
			return R.drawable.drink_water_journey;
		case TableAttributes.greatBreakFastID:
			return R.drawable.great_breakfast_journey;
		case TableAttributes.danceYourWayID:
			return R.drawable.dance_journey;
		case TableAttributes.goldenChallengeID:
			return R.drawable.golden_challenge_journey;
		case TableAttributes.SecretLetterID:
			return R.drawable.secret_letter;	
		
		default:
			return android.R.color.transparent;
		}
	}
	
}