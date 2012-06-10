package com.avalons.mast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class SQL2DeviceActivity extends ListActivity {

    String add="http://10.0.2.2/city.php";
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

     new Connect().execute();

  }

private class Connect extends AsyncTask<Void,Void,String>
{     
      private  String result = "";
      private  InputStream is=null;
     private  String city_name="London";
    protected String doInBackground(Void... params) {
     try
   {
           ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
          nameValuePairs.add(new BasicNameValuePair("Name",city_name));
          HttpClient httpclient = new DefaultHttpClient();
         HttpPost httppost = new HttpPost(add);
         httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
       is = entity.getContent();
          }
 catch(Exception e)
    {
        Log.e("log_tag", "Error in http connection "+e.toString());
          }


    //convert response to string
try{
BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
StringBuilder sb = new StringBuilder();
String line = null;
while ((line = reader.readLine()) != null) {
sb.append(line + "\n");
}
 is.close();
 result=sb.toString();
    }
   catch(Exception e){
    Log.e("log_tag", "Error converting result "+e.toString());
        }


   return result;
   }
protected  void onPostExecute(String  result){

 try{
     JSONArray jArray = new JSONArray( result);
     JSONObject json_data=null;
     for(int i=0;i<jArray.length();i++)
     {
         json_data = jArray.getJSONObject(i);
         int  population=json_data.getInt("City_Population");

       TextView City_Name =(TextView)findViewById(R.id.city_name);
                                                                    TextView  City_population=(TextView)findViewById(R.id.city_pop);
                     City_Name.setText(json_data.getString(city_name));
                                                                   City_population.setText(population+"  " );
     }
     }
     catch(JSONException e){
     Log.e("log_tag", "Error parsing data "+e.toString());
     }


              }
                                                           }

                                             }