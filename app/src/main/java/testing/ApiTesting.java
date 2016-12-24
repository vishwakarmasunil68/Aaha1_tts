package testing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.motivator.wecareyou.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunil on 19-09-2016.
 */
public class ApiTesting extends AppCompatActivity{
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop);
        callService();
    }
    public void callService(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://www.funhabits.co/aaha/profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.optString("success");
                            if(success.equals("true")){
                                JSONObject jsonObject1=jsonObject.optJSONObject("result");
                                Toast.makeText(getApplicationContext(),"Registered Successful",Toast.LENGTH_SHORT);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sunil", "" + error);
                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("pro_name", "sunil");
                params.put("pro_age", "<30");
                params.put("pro_gen", "male");
                params.put("pro_helth_con", "fine");
                params.put("pro_email", "vishw@gmail.com");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(ApiTesting.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

}
