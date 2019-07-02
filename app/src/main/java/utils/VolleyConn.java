package utils;

import android.util.Log;

import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class VolleyConn {

    //SEDES
    private static final String LLANO = "llano";

    //Link LOGIN
    public final static String LOGIN = "https://integracionip.com/app/getUser.php?user_name=";

    //Links LLANO
    public final static String DOWNLOAD_LLANO = "https://ws.crmolivosvillavicencio.com/app/getCartera1.php?user_id=";
    public final static String RECAUDO_LLANO = "https://ws.crmolivosvillavicencio.com/app/getRecaudos.php?user_name=";
    public final static String BUSCAR_CC_EN_CARTERA_LLANO = "https://ws.crmolivosvillavicencio.com/app/getCarterabyCedula.php?user_id=";
    public final static String GESTION_LLANO = "https://ws.crmolivosvillavicencio.com/app/getGestionCartera.php?";
    public final static String UPDATE_LLANO = "https://ws.crmolivosvillavicencio.com/app/getUpdateInfo.php?";

    private RequestQueue queue;
    private JSONObject response;
    private JSONArray responseArray;

//    public void login(String user, String lat, String lon) {
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        String fecha = df.format(Calendar.getInstance().getTime());
//        try {
//            JSONObject post = new JSONObject();
//            post.put("user_name", user);
//            post.put("latitud", lat);
//            post.put("longitud", lon);
//            post.put("fecha", fecha);
//            Log.d("url",LOGIN+user+"&latitud="+lat+"&longitud="+lon+"&fecha="+fecha);
//            new ConexionHTTP.ConectionTask().execute(LOGIN+user+"&latitud="+lat+"&longitud="+lon+"&fecha="+fecha, post.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void downloadFile(String id) {
//        try {
//            JSONObject post = new JSONObject();
//            post.put("id", id);
//            if(sede.equals(LLANO)){
//                System.out.println(DOWNLOAD_LLANO +id);
//                new ConexionHTTP.ConectionTaskMulti().execute(DOWNLOAD_LLANO +id, post.toString());
//            }else{
//                //todo: otras sedes
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void buscarCCenCartera(String id, String cc){
//        try {
//            JSONObject post = new JSONObject();
//            post.put("id", id);
//            post.put("cc", cc);
//            if(sede.equals(LLANO)){
//                System.out.println(BUSCAR_CC_EN_CARTERA_LLANO+id+"&NumeroDocumento="+cc);
//                new ConexionHTTP.ConectionTaskMulti().execute(BUSCAR_CC_EN_CARTERA_LLANO+id+"&NumeroDocumento="+cc, post.toString());
//            }else{
//                //todo: otras sedes
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void recaudo(String user_name, String lat, String lon, String numerodocumento, String valorrecaudo, String id, String fecha, String numerador_rc, String observaciones, String fdp){
//        try {
//            JSONObject post = new JSONObject();
//            post.put("user_name", user_name);
//            post.put("latitud", lat);
//            post.put("longitud", lon);
//            post.put("numerodocumento", numerodocumento);
//            post.put("valorrecaudo", valorrecaudo);
//            post.put("id", id);
//            post.put("fecha_hora", fecha);
//            post.put("numerador_rc",numerador_rc);
//            post.put("observaciones",observaciones);
//            post.put("forma_de_pago",fdp);
//            if(sede.equals(LLANO)){
//                System.out.println("URL:" + RECAUDO_LLANO + user_name + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + numerodocumento + "&valorrecaudo=" + valorrecaudo + "&id=" + id + "&rc=" + numerador_rc + "&fecha_hora=" + fecha + "&detallerecaudo=" + observaciones + "&forma_de_pago=" + fdp);
//                new ConexionHTTP.ConectionTask().execute(RECAUDO_LLANO + user_name + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + numerodocumento + "&valorrecaudo=" + valorrecaudo + "&id=" + id + "&rc=" + numerador_rc + "&fecha_hora=" + fecha + "&detallerecaudo=" + observaciones + "&forma_de_pago=" + fdp);
//
//            }else{
//                //todo: otras sedes
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void gestion(String tipoGestion, String idUsuario, String documento, String fecha, String latitud, String longitud, String acuerdo, String fechaAcuerdo, String valorAcuerdo, String descripcion, String resultadoGestion, String user){
//        try {
//            JSONObject post = new JSONObject();
//            post.put("tipo_gestion", tipoGestion);
//            post.put("id_usuario", idUsuario);
//            post.put("documento", documento);
//            post.put("fecha", fecha);
//            post.put("latitud", latitud);
//            post.put("longitud", longitud);
//            post.put("acuerdo", acuerdo);
//            post.put("fecha_acuerdo", fechaAcuerdo);
//            post.put("valor_acuerdo", valorAcuerdo);
//            post.put("descripcion", descripcion);
//            post.put("resultado_gestion", resultadoGestion);
//            post.put("username", user);
//            if(sede.equals(LLANO)){
//                System.out.println("URL:" + GESTION_LLANO + "user_name="+user + "&latitud=" + latitud + "&longitud=" + longitud + "&numerodocumento=" + documento + "&tipo_gestion=" + tipoGestion + "&fecha=" + fecha + "&acuerdo_pago=" + acuerdo + "&fecha_acuerdo=" + fechaAcuerdo + "&valor_acuerdo=" + valorAcuerdo + "&descripcion=" + descripcion + "&resultado_gestion=" + resultadoGestion + "&user_id=" + idUsuario);
//                new ConexionHTTP.ConectionTask().execute(GESTION_LLANO + "user_name="+user + "&latitud=" + latitud + "&longitud=" + longitud + "&numerodocumento=" + documento + "&tipo_gestion=" + tipoGestion + "&fecha=" + fecha + "&acuerdo_pago=" + acuerdo + "&fecha_acuerdo=" + fechaAcuerdo + "&valor_acuerdo=" + valorAcuerdo + "&descripcion=" + descripcion + "&resultado_gestion=" + resultadoGestion + "&user_id=" + idUsuario);
//            }else{
//                //todo: otras sedes
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void edicion(String user, String id, String nombre, String cedula, String tel1viejo, String tel1nuevo, String tel2viejo, String tel2nuevo, String direccionVieja, String direccionNueva, String fecha, String lat, String lon){
//        try {
//            JSONObject post = new JSONObject();
//            post.put("user", user);
//            post.put("id", id);
//            post.put("nombre", nombre);
//            post.put("cedula", cedula);
//            post.put("tel1viejo", tel1viejo);
//            post.put("tel1nuevo", tel1nuevo);
//            post.put("tel2viejo", tel2viejo);
//            post.put("tel2nuevo", tel2nuevo);
//            post.put("direccion_vieja", direccionVieja);
//            post.put("direccion_nuevo", direccionNueva);
//            post.put("fecha", fecha);
//            post.put("latitud", lat);
//            post.put("longitud", lon);
//            if(sede.equals(LLANO)){
//                System.out.println("URL:" + UPDATE_LLANO + "user_name="+user + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + cedula + "&fecha=" + fecha + "&user_id=" + id + "&celular1_o=" + tel1viejo + "&celular1_n=" + tel1nuevo + "&celular2_o=" + tel2viejo + "&celular2_n=" + tel2nuevo + "&direccion_o=" + direccionVieja + "&direccion_n=" + direccionNueva);
//                System.out.println("-------------\nDireccion nueva: "+direccionNueva+"------------");
//                new ConexionHTTP.ConectionTask().execute(UPDATE_LLANO + "user_name="+user + "&latitud=" + lat + "&longitud=" + lon + "&numerodocumento=" + cedula + "&fecha=" + fecha + "&user_id=" + id + "&celular1_o=" + tel1viejo + "&celular1_n=" + tel1nuevo + "&celular2_o=" + tel2viejo + "&celular2_n=" + tel2nuevo + "&direccion_o=" + direccionVieja + "&direccion_n=" + direccionNueva);
//            }else{
//                //todo: otras sedes
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//

}
