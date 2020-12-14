package org.vunerability.demo.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@Named
public class ProcessBuilderBean {
	
	private String url;
	
	private String outputSSRF;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getOutputSSRF() {
		return outputSSRF;
	}

	public void setOutputSSRF(String outputSSRF) {
		this.outputSSRF = outputSSRF;
	}

	public void execute(String value) throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder();

        // Run this on Windows, cmd, /c = terminate after this run "ping -n 3 google.com"
        processBuilder.command("cmd.exe", "/c", value);

        try {

            Process process = processBuilder.start();

            // blocked :(
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
	
	public String rceAttack() throws Exception {
		   User user = new User();
	       user.setId(0);
	       user.setUsername("admin");

	       XStream xstream = new XStream(new DomDriver());
	       String xml = xstream.toXML(user); // Serialize
	       System.out.println(xml);

	       user = (User) xstream.fromXML(xml); // Deserialize
	       System.out.println(user.getId() + ": " + user.getUsername());
	       return xml;
	   }
	
	 public String URLConnectionVuln(String url) {
		 
		 	outputSSRF = Config.URLConnection(url);
	       return "ssrfOutput";
	   }

}