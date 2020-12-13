package org.vunerability.demo.beans;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;

@Named
public class JsonPBean {
	
	private String callback = Config.getBusinessCallback();

	 public String referer(HttpServletRequest request) {
		  request = (HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest());

	        String callback = request.getParameter(this.callback);
	        return Config.json2Jsonp(callback, getUserInfo2JsonStr(request));
	    }
	 
	 public static String getUserInfo2JsonStr(HttpServletRequest request) {

	        Principal principal = request.getUserPrincipal();
	        String username = principal.getName();
	        Map<String, String> m = new HashMap<>();
	        m.put("Username", username);

	        return JSON.toJSONString(m);
	    }
}
