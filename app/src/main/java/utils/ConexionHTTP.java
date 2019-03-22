package utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConexionHTTP {

    public final static String LOGIN = "https://ws.crmolivosvillavicencio.com/app/getUser.php?user_name=";
    public final static String DOWNLOAD = "https://ws.crmolivosvillavicencio.com/app/getCartera1.php?user_id=";
    public final static String RECAUDO = "https://ws.crmolivosvillavicencio.com/app/getRecaudos.php?user_name=";
    public final static String BUSCAR_CC_EN_CARTERA = "https://ws.crmolivosvillavicencio.com/app/getCarterabyCedula.php?user_id=";

    private JSONObject response;
    private JSONArray responseArray;
    private boolean finishProcess;
    private HttpURLConnection urlConnection;
    private String dataString;

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

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
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
                        response.put("array", ja);
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
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha = df.format(Calendar.getInstance().getTime());
        try {
            JSONObject post = new JSONObject();
            post.put("user_name", user);
            post.put("latitud", lat);
            post.put("longitud", lon);
            post.put("fecha", fecha);
            Log.d("url",LOGIN+user+"&latitud="+lat+"&longitud="+lon+"&fecha="+fecha);
            new ConectionTask().execute(LOGIN+user+"&latitud="+lat+"&longitud="+lon+"&fecha="+fecha, post.toString());
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

    public void buscarCCenCartera(String id, String cc){
        try {
            JSONObject post = new JSONObject();
            post.put("id", id);
            post.put("cc", cc);
            System.out.println(BUSCAR_CC_EN_CARTERA+id+"&NumeroDocumento="+cc);
            new ConectionTaskMulti().execute(BUSCAR_CC_EN_CARTERA+id+"&NumeroDocumento="+cc, post.toString());
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
            System.out.println("URL:" + RECAUDO + user_name + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + numerodocumento + "&valorrecaudo=" + valorrecaudo + "&id=" + id + "&rc=" + numerador_rc + "&fecha_hora=" + fecha + "&detallerecaudo=" + observaciones);
            new ConectionTask().execute(RECAUDO + user_name + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + numerodocumento + "&valorrecaudo=" + valorrecaudo + "&id=" + id + "&rc=" + numerador_rc + "&fecha_hora=" + fecha + "&detallerecaudo=" + observaciones);
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
                System.out.println("--------------------\nEmpezó");
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

//                int inputStreamData = inputStreamReader.read();
//                System.out.println("--------------------\nInicio del while");
//                while (inputStreamData != -1) {
//                    char current = (char) inputStreamData;
//                    inputStreamData = inputStreamReader.read();
//                    data += current;
//                }


                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();

                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                data = sb.toString();


                try {
                    finishProcess = true;
                    System.out.println("--------------------\nDescargó");
                    System.out.println(data);
                    dataString = data;
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
