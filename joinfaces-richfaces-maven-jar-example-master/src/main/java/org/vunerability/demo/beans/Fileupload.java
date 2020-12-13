package org.vunerability.demo.beans;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.inject.Named;
import javax.servlet.http.Part;

@Named
public class Fileupload {

	private Part uploadedFile;
	private String folder = "D:\\Files";

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	
	public void saveFile(){
		
		try (InputStream input = uploadedFile.getInputStream()) {
			String fileName = uploadedFile.getSubmittedFileName();
	        Files.copy(input, new File(folder, fileName).toPath());
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}