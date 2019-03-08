package utils;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConexionHTTP {

    public final static String LOGIN = "https://ws.crmolivosvillavicencio.com/app/getUser.php?user_name=";
    public final static String DOWNLOAD = "https://ws.crmolivosvillavicencio.com/app/getCartera1.php?user_id=";
    public final static String RECAUDO = "https://ws.crmolivosvillavicencio.com/app/getRecaudos.php?user_name=";

    private JSONObject response;
    private JSONArray responseArray;
    private boolean finishProcess;
    private HttpURLConnection urlConnection;

    public ConexionHTTP() {
        finishProcess = false;
    }

    public JSONObject getRespuesta() {
        return response;
    }

    public boolean isFinishProcess() {
        return finishProcess;
    }

    public JSONArray getResponseArray() {
        return responseArray;
    }

    public void setResponseArray(JSONArray responseArray) {
        this.responseArray = responseArray;
    }

    private class ConectionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                //httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("Content-Type", "application/json");
                //httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoOutput(true);

                //DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                //wr.writeBytes(params[1]);
                //wr.flush();
                //wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }

                try {
                    finishProcess = true;
                    //System.out.println("--------------------\n1er char: "+data.charAt(0));
                    if(data.charAt(0) == '['){
                        JSONArray ja = new JSONArray(data);
                        response = new JSONObject();
                        response.put("array", (Object)ja);
                    }else{
                        response = new JSONObject(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch(java.net.UnknownHostException ex){
                data = "no internet";
                finishProcess = true;
                ex.printStackTrace();
            } catch(Exception e) {
                data = "no internet";
                finishProcess = true;
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }

    public void login(String user, String lat, String lon) {
        try {
            JSONObject post = new JSONObject();
            post.put("user_name", user);
            post.put("latitud", lat);
            post.put("longitud", lon);
            Log.d("url",LOGIN+user+"&latitud="+lat+"&longitud="+lon);
            new ConectionTask().execute(LOGIN+user+"&latitud="+lat+"&longitud="+lon, post.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String id) {
        try {
            JSONObject post = new JSONObject();
            post.put("id", id);
            System.out.println(DOWNLOAD+id);
            new ConectionTaskMulti().execute(DOWNLOAD+id, post.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void recaudo(String user_name, String lat, String lon, String numerodocumento, String valorrecaudo, String id, String fecha, String numerador_rc, String observaciones){
        try {
            JSONObject post = new JSONObject();
            post.put("user_name", user_name);
            post.put("latitud", lat);
            post.put("longitud", lon);
            post.put("numerodocumento", numerodocumento);
            post.put("valorrecaudo", valorrecaudo);
            post.put("id", id);
            post.put("fecha_hora", fecha);
            post.put("numerador_rc",numerador_rc);
            post.put("observaciones",observaciones);
            System.out.println("URL:"+RECAUDO+user_name+"&latitud="+lat+"&longitud="+lon+"&numerodocumento="+numerodocumento+"&valorrecaudo="+valorrecaudo+"&id="+id+"&rc="+numerador_rc+"&fecha_hora="+fecha);
            new ConectionTask().execute(RECAUDO+user_name+"&latitud="+lat+"&longitud="+lon+"&numerodocumento="+numerodocumento+"&valorrecaudo="+valorrecaudo+"&id="+id+"&rc="+numerador_rc+"&fecha_hora="+fecha);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class ConectionTaskMulti extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String data = "";
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                //httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("Content-Type", "application/json");
                //httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setDoOutput(true);

                //DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                //wr.writeBytes(params[1]);
                //wr.flush();
                //wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }

                try {
                    finishProcess = true;
                    //System.out.println("--------------------\n1er char: "+data.charAt(0));

                    responseArray = new JSONArray(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch(java.net.UnknownHostException ex){
                data = "no internet";
                finishProcess = true;
                ex.printStackTrace();
            } catch(Exception e) {
                data = "no internet";
                finishProcess = true;
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
        }
    }
}
