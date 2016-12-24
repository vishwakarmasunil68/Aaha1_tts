package com.motivator.support;

import android.os.Environment;

import java.io.File;

/**
 * Created by sunil on 11-11-2016.
 */
public class FileUtils {
    public static final String BASE_FILE_PATH= Environment.getExternalStorageDirectory()+ File.separator+
                                                "aaha";
    public static final String MOOD_FILE_PATH=BASE_FILE_PATH+File.separator+"mood";

    public static final String VOICE_FILE_PATH=BASE_FILE_PATH+File.separator+"voice";

    public static final String RELAXED_FILE_PATH=MOOD_FILE_PATH+File.separator+"relaxed";
    public static final String SAD_FILE_PATH=MOOD_FILE_PATH+File.separator+"sad";
    public static final String TENSED_FILE_PATH=MOOD_FILE_PATH+File.separator+"tensed";
    public static final String STRESSED_FILE_PATH=MOOD_FILE_PATH+File.separator+"stressed";
    public static final String BORED_FILE_PATH=MOOD_FILE_PATH+File.separator+"bored";
    public static final String CALM_FILE_PATH=MOOD_FILE_PATH+File.separator+"calm";
    public static final String CHEER_FILE_PATH=MOOD_FILE_PATH+File.separator+"cheer";
    public static final String EXCITED_FILE_PATH=MOOD_FILE_PATH+File.separator+"excited";
    public static final String IRRITATED_FILE_PATH=MOOD_FILE_PATH+File.separator+"irritated";
    public static final String NEUTRAL_FILE_PATH=MOOD_FILE_PATH+File.separator+"neutral";
    public static final String CALENDAR_FILE_PATH=MOOD_FILE_PATH+File.separator+"calendar";
    public static final String CARE_PLAN_FILE_PATH=BASE_FILE_PATH+File.separator+"careplan";
    public static final String RITUALS_FILE_PATH=BASE_FILE_PATH+File.separator+"rituals";
    public static final String CONVERTED_FILE_PATH=BASE_FILE_PATH+File.separator+"converted";
    public static final String SIGNUP_FILE_PATH=BASE_FILE_PATH+File.separator+"signup";
    public static final String MYHEALTH_FILE_PATH=BASE_FILE_PATH+File.separator+"myhealth";
    public static final String HEALTH_LIST_FILE_PATH=BASE_FILE_PATH+File.separator+"healthlist";
    public static final String JOURNEYLETTER_FILE_PATH=BASE_FILE_PATH+File.separator+"journeyletter";
    public static final String STORY_FILE_PATH=BASE_FILE_PATH+File.separator+"story";
    public static final String STRESS_RELIEF_FILE_PATH=BASE_FILE_PATH+File.separator+"stressrelief";
    public static final String RELAXATION_ZONE_FILE_PATH=BASE_FILE_PATH+File.separator+"relaxationzone";
    public static final String RELAXATION_ZONE_AUDIO_FILE_PATH=RELAXATION_ZONE_FILE_PATH+File.separator+"audio";
    public static final String RELAXATION_ZONE_VIDEO_FILE_PATH=RELAXATION_ZONE_FILE_PATH+File.separator+"video";
    public static final String WALKTHROUGH_FILE_PATH=BASE_FILE_PATH+File.separator+"walkthrough";
    public static final String LOGIN_FILE_PATH=BASE_FILE_PATH+File.separator+"login";
    public static final String JOURNEY_CHALLENGE=BASE_FILE_PATH+File.separator+"journeychallenge";
    public static final String SKIP_FILE_PATH=BASE_FILE_PATH+File.separator+"skip";
    public static final String CLICK_FILE_PATH=BASE_FILE_PATH+File.separator+"click_tap";


    //for voice files

    public static final String MOOD_VOICE_FILE_PATH=VOICE_FILE_PATH+File.separator+"mood";
    public static final String RELAXED_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"relaxed";
    public static final String SAD_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"sad";
    public static final String TENSED_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"tensed";
    public static final String STRESSED_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"stressed";
    public static final String BORED_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"bored";
    public static final String CALM_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"calm";
    public static final String CHEER_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"cheer";
    public static final String EXCITED_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"excited";
    public static final String IRRITATED_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"irritated";
    public static final String NEUTRAL_MOOD_VOICE_FILE_PATH=MOOD_VOICE_FILE_PATH+File.separator+"neutral";


    public static final String RITUAL_LIST_VOICE_FILE_PATH=VOICE_FILE_PATH+File.separator+"ritual_list";
    public static final String REMINDER_MYHABIT_FILE_PATH=VOICE_FILE_PATH+File.separator+"reminders";

    public static final String RITUAL_SETTING_FILE_PATH=VOICE_FILE_PATH+File.separator+"ritual_setting";
    public static final String MYHABIT_AV_SCREEN_VOICE_FILE_PATH=VOICE_FILE_PATH+File.separator+"myhabitav_screen";



    public static void CreateALlMoodMusicDirectories(){
        File f=new File(MOOD_FILE_PATH);
        f.mkdirs();
        File relaxed_file=new File(RELAXED_FILE_PATH);
        relaxed_file.mkdirs();
        File sad_file=new File(SAD_FILE_PATH);
        sad_file.mkdirs();
        File tensed_file=new File(TENSED_FILE_PATH);
        tensed_file.mkdirs();
        File stressed_file=new File(STRESSED_FILE_PATH);
        stressed_file.mkdirs();
        File bored_file=new File(BORED_FILE_PATH);
        bored_file.mkdirs();
        File calm_file=new File(CALM_FILE_PATH);
        calm_file.mkdirs();
        File cheer_file=new File(CHEER_FILE_PATH);
        cheer_file.mkdirs();
        File excited_file=new File(EXCITED_FILE_PATH);
        excited_file.mkdirs();
        File irritated_file=new File(IRRITATED_FILE_PATH);
        irritated_file.mkdirs();
        File neutral_file=new File(NEUTRAL_FILE_PATH);
        neutral_file.mkdirs();
        File careplan_file=new File(CARE_PLAN_FILE_PATH);
        careplan_file.mkdirs();
        File rituals_file=new File(RITUALS_FILE_PATH);
        rituals_file.mkdirs();
        File converted_file=new File(CONVERTED_FILE_PATH);
        converted_file.mkdirs();
        File signup_file_path=new File(SIGNUP_FILE_PATH);
        signup_file_path.mkdirs();
        File calendar_file_path=new File(CALENDAR_FILE_PATH);
        calendar_file_path.mkdirs();
        File my_health_file_path=new File(MYHEALTH_FILE_PATH);
        my_health_file_path.mkdirs();
        File journeyletter=new File(JOURNEYLETTER_FILE_PATH);
        journeyletter.mkdirs();
        File story_file_path=new File(STORY_FILE_PATH);
        story_file_path.mkdirs();
        File stress_relief_file_path=new File(STRESS_RELIEF_FILE_PATH);
        stress_relief_file_path.mkdirs();
        File relaxation_zone=new File(RELAXATION_ZONE_FILE_PATH);
        relaxation_zone.mkdirs();
        File relaxation_zone_audio=new File(RELAXATION_ZONE_AUDIO_FILE_PATH);
        relaxation_zone_audio.mkdirs();
        File relaxation_zone_video=new File(RELAXATION_ZONE_VIDEO_FILE_PATH);
        relaxation_zone_video.mkdirs();
        File health_list_file_path=new File(HEALTH_LIST_FILE_PATH);
        health_list_file_path.mkdirs();
        File walkthrough_file_path=new File(WALKTHROUGH_FILE_PATH);
        walkthrough_file_path.mkdirs();
        File login_file_path=new File(LOGIN_FILE_PATH);
        login_file_path.mkdirs();
        File journey_challenge_file_path=new File(JOURNEY_CHALLENGE);
        journey_challenge_file_path.mkdirs();
        File skip_file_path=new File(SKIP_FILE_PATH);
        skip_file_path.mkdirs();
        File click_file_path=new File(CLICK_FILE_PATH);
        click_file_path.mkdirs();

        File voice_file_path=new File(VOICE_FILE_PATH);
        voice_file_path.mkdirs();
        File mood_voice_file_path=new File(MOOD_VOICE_FILE_PATH);
        mood_voice_file_path.mkdirs();
        File relaxed_mood_voice_path=new File(RELAXED_MOOD_VOICE_FILE_PATH);
        relaxed_mood_voice_path.mkdirs();
        File sad_mood_voice_file_path=new File(SAD_MOOD_VOICE_FILE_PATH);
        sad_mood_voice_file_path.mkdirs();
        File tensed_mood_voice_file_path=new File(TENSED_MOOD_VOICE_FILE_PATH);
        tensed_mood_voice_file_path.mkdirs();
        File stressed_mood_voice_file_path=new File(STRESSED_MOOD_VOICE_FILE_PATH);
        stressed_mood_voice_file_path.mkdirs();
        File bored_mood_voice_file_path=new File(BORED_MOOD_VOICE_FILE_PATH);
        bored_mood_voice_file_path.mkdirs();
        File calm_mood_voice_file_path=new File(CALM_MOOD_VOICE_FILE_PATH);
        calm_mood_voice_file_path.mkdirs();
        File cheer_mood_voice_file_path=new File(CHEER_MOOD_VOICE_FILE_PATH);
        cheer_mood_voice_file_path.mkdirs();
        File excited_mood_voice_file_path=new File(EXCITED_MOOD_VOICE_FILE_PATH);
        excited_mood_voice_file_path.mkdirs();
        File irritated_mood_voice_file_path=new File(IRRITATED_MOOD_VOICE_FILE_PATH);
        irritated_mood_voice_file_path.mkdirs();
        File neutral_mood_voice_file_path=new File(NEUTRAL_MOOD_VOICE_FILE_PATH);
        neutral_mood_voice_file_path.mkdirs();

        File ritual_list_voice_file_path=new File(RITUAL_LIST_VOICE_FILE_PATH);
        ritual_list_voice_file_path.mkdirs();


        File reminder_my_habit_file_path=new File(REMINDER_MYHABIT_FILE_PATH);
        reminder_my_habit_file_path.mkdirs();
        File ritual_setting_file_path=new File(RITUAL_SETTING_FILE_PATH);
        ritual_setting_file_path.mkdirs();

        File myhabit_ac_screen_voice_file_path=new File(MYHABIT_AV_SCREEN_VOICE_FILE_PATH);
        myhabit_ac_screen_voice_file_path.mkdirs();
    }


    public static String GetMusicDirectory(String category){
        switch (category){
            case StringUtils.BORED:
                return BORED_FILE_PATH;
            case StringUtils.CALM:
                return CALM_FILE_PATH;
            case StringUtils.EXCITED:
                return EXCITED_FILE_PATH;
            case StringUtils.CHEERFUL:
                return CHEER_FILE_PATH;
            case StringUtils.IRRITATED:
                return IRRITATED_FILE_PATH;
            case StringUtils.NEUTRAL:
                return NEUTRAL_FILE_PATH;
            case StringUtils.RELAXED:
                return RELAXED_FILE_PATH;
            case StringUtils.SAD:
                return SAD_FILE_PATH;
            case StringUtils.STRESSED:
                return STRESSED_FILE_PATH;
            case StringUtils.CALENDAR:
                return CALENDAR_FILE_PATH;
            case StringUtils.CAREPLAN:
                return CARE_PLAN_FILE_PATH;
            case StringUtils.RITUALS:
                return RITUALS_FILE_PATH;
            case StringUtils.CONVERTED:
                return CONVERTED_FILE_PATH;
            case StringUtils.SIGNUP:
                return SIGNUP_FILE_PATH;
            case StringUtils.MYHEALTH:
                return MYHEALTH_FILE_PATH;
            case StringUtils.JOURNEYLETTER:
                return JOURNEYLETTER_FILE_PATH;
            case StringUtils.STORY:
                return STORY_FILE_PATH;
            case StringUtils.STRESS_RELIEF:
                return STRESS_RELIEF_FILE_PATH;
            case StringUtils.RELAXATION_ZONE_AUDIO:
                return RELAXATION_ZONE_AUDIO_FILE_PATH;
            case StringUtils.RELAXATION_ZONE_VIDEO:
                return RELAXATION_ZONE_VIDEO_FILE_PATH;
            case StringUtils.HABITLIST:
                return HEALTH_LIST_FILE_PATH;
            case StringUtils.WALKTHROUGH:
                return WALKTHROUGH_FILE_PATH;
            case StringUtils.LOGIN:
                return LOGIN_FILE_PATH;
            case StringUtils.JOURNEYCHALLENGE:
                return JOURNEY_CHALLENGE;
            case StringUtils.SKIP:
                return SKIP_FILE_PATH;
            case StringUtils.CLICKS_TAP_SELECT:
                return CLICK_FILE_PATH;

            //for mood voice file paths
            case StringUtils.VOICE_BORED:
                return BORED_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_CALM:
                return CALM_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_CHEER:
                return CHEER_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_EXCITED:
                return EXCITED_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_IRRITATED:
                return IRRITATED_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_NEUTRAL:
                return NEUTRAL_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_RELAXED:
                return RELAXED_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_SAD:
                return SAD_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_STRESSED:
                return STRESSED_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_TENSED:
                return TENSED_MOOD_VOICE_FILE_PATH;
            case StringUtils.VOICE_RITUAL_LIST_HOME:
                return RITUAL_LIST_VOICE_FILE_PATH;
            case StringUtils.REMINDER_MY_FILES:
                return REMINDER_MYHABIT_FILE_PATH;
            case StringUtils.RITUAL_SETTING_VOICE:
                return RITUAL_SETTING_FILE_PATH;
            case StringUtils.MYHABIT_AV_SCREEN_VOICE:
                return MYHABIT_AV_SCREEN_VOICE_FILE_PATH;
            default:
                return "";
        }
    }

    public static String getMoodFileURL(String category,String file_name){
        switch (category){
            case StringUtils.BORED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/bored/"+file_name;
            case StringUtils.CALM:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/calm/"+file_name;
            case StringUtils.EXCITED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/excited/"+file_name;
            case StringUtils.IRRITATED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/irritated/"+file_name;
            case StringUtils.NEUTRAL:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/neutral/"+file_name;
            case StringUtils.RELAXED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/relaxed/"+file_name;
            case StringUtils.SAD:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/sad/"+file_name;
            case StringUtils.STRESSED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/stressed/"+file_name;
            case StringUtils.TENSED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/tense/"+file_name;
            case StringUtils.CALENDAR:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/calendar/"+file_name;
            case StringUtils.CAREPLAN:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/Careplan/"+file_name;
            case StringUtils.RITUALS:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/rituals/"+file_name;
            case StringUtils.WALKTHROUGH:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/walkthrough/"+file_name;
            case StringUtils.CONVERTED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/converted/"+file_name;
            case StringUtils.SIGNUP:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/registration/"+file_name;
            case StringUtils.MYHEALTH:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/my-health/"+file_name;
            case StringUtils.JOURNEYLETTER:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/journey-letter/"+file_name;
            case StringUtils.STORY:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/story/"+file_name;
            case StringUtils.STRESS_RELIEF:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/stress-relief-wizard/"+file_name;
            case StringUtils.RELAXATION_ZONE:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/mood/relaxtion_zone/"+file_name;
            case StringUtils.HABITLIST:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/health_list/"+file_name;
            case StringUtils.JOURNEYCHALLENGE:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/journey-challen/"+file_name;
            case StringUtils.SKIP:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/skip/"+file_name;
            case StringUtils.CLICKS_TAP_SELECT:
                return "https://s3-us-west-2.amazonaws.com/aahadev/music/aaha_music_file/click-tap-sele/"+file_name;

            //for mood voice file path
            case StringUtils.VOICE_BORED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/bored/"+file_name;
            case StringUtils.VOICE_CALM:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/calm/"+file_name;
            case StringUtils.VOICE_CHEER:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/cheer/"+file_name;
            case StringUtils.VOICE_EXCITED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/excited/"+file_name;
            case StringUtils.VOICE_IRRITATED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/irritated/"+file_name;
            case StringUtils.VOICE_NEUTRAL:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/neutral/"+file_name;
            case StringUtils.VOICE_RELAXED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/relaxed/"+file_name;
            case StringUtils.VOICE_SAD:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/sad/"+file_name;
            case StringUtils.VOICE_STRESSED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/stressed/"+file_name;
            case StringUtils.VOICE_TENSED:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/mood-files/tensed/"+file_name;
            case StringUtils.VOICE_RITUAL_LIST_HOME:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/ritval-list-screen/"+file_name;
            case StringUtils.REMINDER_MY_FILES:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/alarm-reminder-files/"+file_name;
            case StringUtils.RITUAL_SETTING_VOICE:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/ritval-settings/"+file_name;
            case StringUtils.MYHABIT_AV_SCREEN_VOICE:
                return "https://s3-us-west-2.amazonaws.com/aahadev/voice/my-habitab-screen/"+file_name;
            default:
                return "";
        }
    }
}
