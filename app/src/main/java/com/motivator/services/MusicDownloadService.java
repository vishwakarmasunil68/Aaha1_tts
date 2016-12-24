package com.motivator.services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.motivator.common.Pref;
import com.motivator.common.WebServices;
import com.motivator.database.NewDataBaseHelper;
import com.motivator.model.MusicPOJO;
import com.motivator.model.music.musicResponse;
import com.motivator.model.music.result;
import com.motivator.support.FileUtils;
import com.motivator.support.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 11-11-2016.
 */
public class MusicDownloadService extends Service {
    final String TAG = "musicdownloadService";
    NewDataBaseHelper helper;
    List<String> all_music_files=new ArrayList<>();
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
        helper = new NewDataBaseHelper(this);
        CheckMusicFiles(WebServices.MUSIC_FILES_URL);

        getFilesFromDir(FileUtils.BASE_FILE_PATH);
        Log.d(TAG,"total file:-"+all_music_files.size());
        for(String file_path:all_music_files){
            Log.d(TAG,file_path);
        }
        return START_STICKY;
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

    private void CheckMusicFiles(final String url_string) {
        new AsyncTask<Void, Void, Void>() {
            StringBuilder sb = new StringBuilder();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d("sunil", "music api execution");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(url_string);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Log.d("sunil", sb.toString());
//                if (sb.toString().equals(Pref.getString(getApplicationContext(), StringUtils.AUDIO_JSON, ""))) {
//
//                } else {

                    Pref.setString(getApplicationContext(), StringUtils.AUDIO_JSON, sb.toString());
                    Gson gson = new Gson();
                    musicResponse pojo = gson.fromJson(sb.toString(), musicResponse.class);
                    List<MusicPOJO> list_music = new ArrayList<MusicPOJO>();
                    List<String> all_music_file_names=new ArrayList<String>();
                    if (pojo != null) {
                        if (pojo.getSuccess().equals("true")) {
                            Log.d(TAG, pojo.toString());
                            if (pojo.getList_result().size() > 0) {
                                for (result response : pojo.getList_result()) {
                                    MusicPOJO musicPOJO = new MusicPOJO();
                                    musicPOJO.setMusic_id(response.getVideo_id());
                                    musicPOJO.setMusic_title(response.getVideo_title());
                                    musicPOJO.setMusic_description(response.getVideo_destription());
                                    musicPOJO.setMusic_name(response.getVideo_name());
                                    musicPOJO.setMusic_category(response.getVideo_cat());

                                    all_music_file_names.add((response.getVideo_name()));
                                    if (musicPOJO.getMusic_category().equals("relaxtion_zone")) {
                                        if (response.getVideo_name().contains("mp3")) {
                                            musicPOJO.setMusic_file_path(FileUtils.GetMusicDirectory(StringUtils.RELAXATION_ZONE_AUDIO) + File.separator + response.getVideo_name());
                                        } else {
                                            if (response.getVideo_name().contains("mp4")) {
                                                musicPOJO.setMusic_file_path(FileUtils.GetMusicDirectory(StringUtils.RELAXATION_ZONE_VIDEO) + File.separator + response.getVideo_name());
                                            }
                                        }
                                    } else {
                                        musicPOJO.setMusic_file_path(FileUtils.GetMusicDirectory(response.getVideo_cat()) + File.separator + response.getVideo_name());
                                    }

                                    if (musicPOJO.getMusic_category().toString().contains("voice")) {
                                        Log.d("musicdownloadservice", "in voice");
                                        String file_url = FileUtils.getMoodFileURL(musicPOJO.getMusic_category(), musicPOJO.getMusic_name());
                                        Log.d("musicdownloadService", musicPOJO.toString());
                                        Log.d("musicdownloadService", file_url);
                                        File f = new File(musicPOJO.getMusic_file_path());
                                        if (f.exists()) {
                                            Log.d("musicdownloadService", "file exist:-" + f.exists());
                                        } else {
                                            Log.d("musicdownloadService", "file exist:-" + f.exists() + "   " + "downloading file");
                                            long val = WebServices.DownloadMusicFile(getApplicationContext(), file_url, musicPOJO);
//                                    if(val>0){
//                                        Log.d("musicdownloadService","file downloaded");
//                                    }
//                                    else{
//                                        Log.d("musicdownloadService","file downloading failed");
//                                    }
                                        }
                                    } else {
                                        list_music.add(musicPOJO);
                                    }
                                }
                            } else {
                                Log.d(TAG, "no list in response");
                            }
                        }
                        if (list_music.size() > 0) {
                            for (MusicPOJO music_pojo : list_music) {
                                String file_url = FileUtils.getMoodFileURL(music_pojo.getMusic_category(), music_pojo.getMusic_name());
                                Log.d("musicdownloadService", music_pojo.toString());
                                Log.d("musicdownloadService", file_url);
                                File f = new File(music_pojo.getMusic_file_path());
                                if (f.exists()) {
                                    Log.d("musicdownloadService", "file exist:-" + f.exists());
                                } else {
                                    Log.d("musicdownloadService", "file exist:-" + f.exists() + "   " + "downloading file");
                                    long val = WebServices.DownloadMusicFile(getApplicationContext(), file_url, music_pojo);
                                }
                            }
                        }
                        for(String file_name:all_music_files){
                            File f=new File(file_name);
                            if(f.exists()) {
                                if (all_music_file_names.contains(f.getName())){
                                    Log.d("file_existing","contains:-"+f.toString());
                                }
                                else{
                                    Log.d("file_existing","not contains:-"+f.toString());
                                    Log.d("file_existing","filedeleted:-"+f.delete());
                                }
                            }
                        }

                    }
            }

        }.execute();
    }
}
