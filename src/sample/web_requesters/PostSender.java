package sample.web_requesters;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import sample.data.Const;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PostSender {

    private static String readAll(Reader rd) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static List<NameValuePair> params = new ArrayList<NameValuePair>(2);

    public static void add_param(String id, String value){
        params.add(new BasicNameValuePair(id, value));
    }

    public static void remove_all_params(){
        params.clear();
    }

    public static void sendPost(String url) throws IOException {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials
                = new UsernamePasswordCredentials(Const.server_auth_username, Const.server_auth_password);
        provider.setCredentials(AuthScope.ANY, credentials);

        HttpClient httpclient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(provider)
                .build();

        HttpPost httppost = new HttpPost(url);

        String encoding = new String(Base64.getEncoder().encode((Const.server_auth_username + ":" + Const.server_auth_password).getBytes()));
        httppost.setHeader("Authorization", "Basic " + encoding);
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                System.out.println(jsonText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}