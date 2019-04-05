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
    public final static String GESTION = "https://ws.crmolivosvillavicencio.com/app/getGestionCartera.php?";
    public final static String UPDATE = "https://ws.crmolivosvillavicencio.com/app/getUpdateInfo.php?";

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

    public void recaudo(String user_name, String lat, String lon, String numerodocumento, String valorrecaudo, String id, String fecha, String numerador_rc, String observaciones, String fdp){
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
            post.put("forma_de_pago",fdp);
            System.out.println("URL:" + RECAUDO + user_name + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + numerodocumento + "&valorrecaudo=" + valorrecaudo + "&id=" + id + "&rc=" + numerador_rc + "&fecha_hora=" + fecha + "&detallerecaudo=" + observaciones + "&forma_de_pago=" + fdp);
            new ConectionTask().execute(RECAUDO + user_name + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + numerodocumento + "&valorrecaudo=" + valorrecaudo + "&id=" + id + "&rc=" + numerador_rc + "&fecha_hora=" + fecha + "&detallerecaudo=" + observaciones + "&forma_de_pago=" + fdp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void gestion(String tipoGestion, String idUsuario, String documento, String fecha, String latitud, String longitud, String acuerdo, String fechaAcuerdo, String valorAcuerdo, String descripcion, String resultadoGestion, String user){
        try {
            JSONObject post = new JSONObject();
            post.put("tipo_gestion", tipoGestion);
            post.put("id_usuario", idUsuario);
            post.put("documento", documento);
            post.put("fecha", fecha);
            post.put("latitud", latitud);
            post.put("longitud", longitud);
            post.put("acuerdo", acuerdo);
            post.put("fecha_acuerdo", fechaAcuerdo);
            post.put("valor_acuerdo", valorAcuerdo);
            post.put("descripcion", descripcion);
            post.put("resultado_gestion", resultadoGestion);
            post.put("username", user);
            System.out.println("URL:" + GESTION + "user_name="+user + "&latitud=" + latitud + "&longitud=" + longitud + "&numerodocumento=" + documento + "&tipo_gestion=" + tipoGestion + "&fecha=" + fecha + "&acuerdo_pago=" + acuerdo + "&fecha_acuerdo=" + fechaAcuerdo + "&valor_acuerdo=" + valorAcuerdo + "&descripcion=" + descripcion + "&resultado_gestion=" + resultadoGestion + "&user_id=" + idUsuario);
            new ConectionTask().execute(GESTION + "user_name="+user + "&latitud=" + latitud + "&longitud=" + longitud + "&numerodocumento=" + documento + "&tipo_gestion=" + tipoGestion + "&fecha=" + fecha + "&acuerdo_pago=" + acuerdo + "&fecha_acuerdo=" + fechaAcuerdo + "&valor_acuerdo=" + valorAcuerdo + "&descripcion=" + descripcion + "&resultado_gestion=" + resultadoGestion + "&user_id=" + idUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void edicion(String user, String id, String nombre, String cedula, String tel1viejo, String tel1nuevo, String tel2viejo, String tel2nuevo, String direccionVieja, String direccionNueva, String fecha, String lat, String lon){
        try {
            JSONObject post = new JSONObject();
            post.put("user", user);
            post.put("id", id);
            post.put("nombre", nombre);
            post.put("cedula", cedula);
            post.put("tel1viejo", tel1viejo);
            post.put("tel1nuevo", tel1nuevo);
            post.put("tel2viejo", tel2viejo);
            post.put("tel2nuevo", tel2nuevo);
            post.put("direccion_vieja", direccionVieja);
            post.put("direccion_nuevo", direccionNueva);
            post.put("fecha", fecha);
            post.put("latitud", lat);
            post.put("longitud", lon);
            System.out.println("URL:" + UPDATE + "user_name="+user + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + cedula + "&fecha=" + fecha + "&user_id=" + id + "&celular1_o=" + tel1viejo + "&celular1_n=" + tel1nuevo + "&celular2_o=" + tel2viejo + "&celular2_n=" + tel2nuevo + "&direccion_o=" + direccionVieja + "&direccion_n=" + direccionNueva);
            new ConectionTask().execute(UPDATE + "user_name="+user + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + cedula + "&fecha=" + fecha + "&user_id=" + id + "&celular1_o=" + tel1viejo + "&celular1_n=" + tel1nuevo + "&celular2_o=" + tel2viejo + "&celular2_n=" + tel2nuevo + "&direccion_o=" + direccionVieja + "&direccion_n=" + direccionNueva);
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
