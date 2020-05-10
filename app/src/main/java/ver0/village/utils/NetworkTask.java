package ver0.village.utils;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by user on 2017-12-31.
 */
public class NetworkTask extends AsyncTask<Void, Void, String> {

    private String url = "http://52.78.244.194/",routes,method;
    private ContentValues values;
    private JSONObject jsonobject;

    public NetworkTask(String routes, String method, JSONObject jsonobject) {

        this.routes = routes;
        this.method = method;
        this.jsonobject = jsonobject;
    }

    @Override
    protected String doInBackground(Void... params) {
        url = url + routes;
        URI uri = null;
        URL url = null;//url을 가져온다.
        HttpURLConnection con = null;

        switch (method) {
            case "GET":
                try {
                    /*Uri.Builder builder = Uri.parse(this.url).buildUpon();
                    Iterator keys = this.jsonobject.keys();
                    while(keys.hasNext()){
                        String key = (String) keys.next();
                        builder.appendQueryParameter(key, this.jsonobject.getString(key));
                    }
                    this.url = builder.build().toString();*/
                    url = new URL(this.url);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Accept", "text/html");
                    if(this.jsonobject!=null){
                        Iterator keys = this.jsonobject.keys();
                        while(keys.hasNext()){
                            String key = (String) keys.next();
                            con.setRequestProperty(key, this.jsonobject.getString(key));
                            Log.d("key", key);
                        }
                    }
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    url = new URL(this.url);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod(this.method);//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    /*Iterator keys = this.jsonobject.keys();
                    while(keys.hasNext()){
                        String key = (String) keys.next();
                        con.setRequestProperty(key, this.jsonobject.getString(key));
                        Log.d("key", key);
                    }*/
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect(); //서버로 보내기위해서 스트림 만듬
                    if(this.jsonobject!=null){
                        OutputStream outStream = con.getOutputStream();
                        //버퍼를 생성하고 넣음
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                        try {
                            writer.write(this.jsonobject.toString());
                        } catch (Exception e) {
                        }
                        writer.flush();
                        writer.close();//버퍼를 받아줌
                        outStream.close();
                    }
                    //return "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        StringBuffer buffer = null;
        InputStream stream = null;
        try {
            stream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}