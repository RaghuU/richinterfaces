package org.vunerability.demo.beans;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.inject.Named;


@Named
public class FilePathTraversalBean {

	public String output;
	public String path;
	
	
	public String getOutput() {
		return output;
	}


	public void setOutput(String output) {
		this.output = output;
	}

	

	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String executeFilePath(String filepath) throws IOException {
		
		output = getImgBase64(filepath);
	    return "OUTPUT";
	}
	
	private String getImgBase64(String imgFile) throws IOException {


        File f = new File(imgFile);
        if (f.exists() && !f.isDirectory()) {
            byte[] data = Files.readAllBytes(Paths.get(imgFile));
            return new String(Base64.encodeBase64(data));
        } else {
            return "File doesn't exist or is not a file.";
        }
    }
}
