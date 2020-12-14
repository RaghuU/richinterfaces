package org.vunerability.demo.beans;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

@Named
public class JsonPBean {
	
	private String callback = Config.getBusinessCallback();
	private String input;

	
	 public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String referer(String text) {
		//context.getRequest().getUri()
		 ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		 HttpServletRequest request = (HttpServletRequest) context.getRequest();

	        String callback = request.getParameter(this.callback);
	        return Config.json2Jsonp(callback, getUserInfo2JsonStr());
	    }
	 
	 public static String getUserInfo2JsonStr() {
		 ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		 HttpServletRequest request = (HttpServletRequest) context.getRequest();
	        Principal principal = request.getUserPrincipal();
	        String username = principal.getName();
	        Map<String, String> m = new HashMap<>();
	        m.put("Username", username);

	        return JSON.toJSONString(m);
	    }
}
