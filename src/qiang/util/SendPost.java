package qiang.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 
 * 鍙戦�乸ost璇锋眰锛岃繑鍥瀓son
 * @author jq
 *
 */

public class SendPost {

	
	public static void main(String[] args) {
		String pp = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
				+ "116.2919156035,39.945697846499996&destinations=116.4599156035,39.882697846&key=AIzaSyD4mlW5hw8i5M_qsmAjG4lkKcIzrAgMcb8";
		sendPost(pp);

	}
	
	public static String sendPost(String url){
		StringBuilder sb = new StringBuilder();
		String urlNameString = url;
		try {
			System.out.println("line:");
			URL realUrl = new URL(urlNameString);
			HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
			String line;
			System.out.println("line:");
			while((line = reader.readLine())!=null){
				sb.append(line);
				System.out.println("line:"+line);
			}
			reader.close();
			httpURLConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}
