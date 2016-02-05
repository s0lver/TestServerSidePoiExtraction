package tamps.cinvestav.s0lver;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import tamps.cinvestav.s0lver.entities.GpsFix;
import tamps.cinvestav.s0lver.entities.StayPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private final SimpleDateFormat sdf;
    private final String USER_AGENT = "Mozilla/5.0";
    private ArrayList<GpsFix> gpsFixes;
    Scanner scanner;

    public Main() {
        scanner = new Scanner(System.in);
        sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    }

    public static void main(String[] args) throws Exception {
        Main app = new Main();
        // app.createGpsFixesList();
        app.readGpsFixesFile();
        app.processFixes();
    }

    private void createGpsFixesList() throws ParseException {
        this.gpsFixes = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

        gpsFixes.add(new GpsFix(24.840481, -98.166489, simpleDateFormat.parse("15/05/2012 13:47:20")));
        gpsFixes.add(new GpsFix(24.84123, -98.164726, simpleDateFormat.parse("15/05/2012 13:50:20")));
        gpsFixes.add(new GpsFix(24.841026, -98.163269, simpleDateFormat.parse("15/05/2012 13:52:25")));
        gpsFixes.add(new GpsFix(24.842857, -98.156059, simpleDateFormat.parse("15/05/2012 14:02:10")));
        gpsFixes.add(new GpsFix(24.844316, -98.155846, simpleDateFormat.parse("15/05/2012 14:04:21")));
        gpsFixes.add(new GpsFix(24.845079, -98.155792, simpleDateFormat.parse("15/05/2012 14:05:33")));
        gpsFixes.add(new GpsFix(24.845606, -98.155815, simpleDateFormat.parse("15/05/2012 14:06:38")));
        gpsFixes.add(new GpsFix(24.846004, -98.155792, simpleDateFormat.parse("15/05/2012 14:14:44")));
        gpsFixes.add(new GpsFix(24.849789, -98.155647, simpleDateFormat.parse("15/05/2012 14:15:39")));
        gpsFixes.add(new GpsFix(24.850178, -98.155594, simpleDateFormat.parse("15/05/2012 14:16:32")));
    }

    private void readGpsFixesFile(){
        String csvOrigin = "C:\\Users\\rafael\\desktop\\tmp\\test-server-side\\test-registros.csv";
        SmartphoneFixesFileReader reader = new SmartphoneFixesFileReader(csvOrigin);
        gpsFixes = reader.readFile();
    }

    private void processFixes() throws Exception {
        for (GpsFix gpsFix : gpsFixes) {
            StayPoint stayPoint = sendPost(gpsFix);
            // System.out.print("Press enter to continue: ");
            // scanner.nextLine();
        }

        processLastPart();
    }

    private StayPoint sendPost(GpsFix gpsFix) throws Exception {

        String url = "http://localhost/local-poi/uploadFixMontoliou.php";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("timestamp", sdf.format(gpsFix.getTimestamp())));
        urlParameters.add(new BasicNameValuePair("latitude", String.valueOf(gpsFix.getLatitude())));
        urlParameters.add(new BasicNameValuePair("longitude", String.valueOf(gpsFix.getLongitude())));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
//        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
        return convertStringToStayPoint(result.toString());
    }

    private StayPoint processLastPart() throws IOException {
        String url = "http://localhost/local-poi/uploadFixMontoliou.php";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        post.setHeader("User-Agent", USER_AGENT);
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("lastPart", "ok"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        //System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
        return convertStringToStayPoint(result.toString());
    }

    private StayPoint convertStringToStayPoint(String line) {
        if (line.equals("-1"))
            return null;
        return null;
    }
}
