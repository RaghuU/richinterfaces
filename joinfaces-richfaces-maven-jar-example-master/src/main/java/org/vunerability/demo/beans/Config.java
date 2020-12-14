package org.vunerability.demo.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.util.HtmlUtils;

public class Config {

	private static String businessCallback ="\"This is a quote'";
	
	public static String getBusinessCallback() {
        return businessCallback;
    }
	
	public static String json2Jsonp(String callback, String jsonStr) {
        return HtmlUtils.htmlEscape(callback) + "(" + jsonStr + ")";
    }
	
	public static String getImgBase64(String imgFile) throws IOException {


	        File f = new File(imgFile);
	        if (f.exists() && !f.isDirectory()) {
	            byte[] data = Files.readAllBytes(Paths.get(imgFile));
	            return new String(Base64.encodeBase64(data));
	        } else {
	            return "File doesn't exist or is not a file.";
	        }
	    }
	
	  public static String getRequestBody(HttpServletRequest request) throws IOException {
	        InputStream in = request.getInputStream();
	        return convertStreamToString(in);
	    }
	  
	  public static String convertStreamToString(java.io.InputStream is) {
	        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	        return s.hasNext() ? s.next() : "";
	    }
	
	public static String URLConnection(String url) {
        try {
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //send request
            String inputLine;
            StringBuilder html = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
            return html.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
