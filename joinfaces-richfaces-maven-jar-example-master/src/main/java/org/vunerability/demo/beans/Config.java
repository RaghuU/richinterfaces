package org.vunerability.demo.beans;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.util.HtmlUtils;

public class Config {

	private static String businessCallback;
	
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
}
