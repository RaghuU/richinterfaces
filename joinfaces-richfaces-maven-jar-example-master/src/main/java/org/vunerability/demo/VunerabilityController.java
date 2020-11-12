package org.vunerability.demo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.vunerability.demo.beans.Config;
import org.vunerability.demo.dao.User;
import org.vunerability.demo.dao.UserDao;

import com.alibaba.fastjson.JSON;


@Controller
public class VunerabilityController {

	@Autowired
	UserDao userDao;
	
	private String callback = Config.getBusinessCallback();
	
	@RequestMapping("/welcome")
	public String firstPage(Map<String, Object> model) {
		
		 User userForm = new User();
		 model.put("userForm", userForm);
		
		return "welcome";
	}
	
	@RequestMapping("/sqlinj")
	public String secmap(Map<String, Object> model) {
		
		 User userForm = new User();
		 model.put("userForm", userForm);
		
		return "secdemo";
	}
	@PostMapping("/usersearch")
	public ModelAndView getUserdata(@ModelAttribute("userForm") User user) {
		ModelAndView mv = new ModelAndView("secdemo");
		List<User> usersList=null;
		System.out.println(user.getId());
		if(user.getId()==null ||user.getId().isEmpty() ) {
			usersList = userDao.findAll();
		for(User obj:usersList) {
			System.out.println(obj);
		}
		}else {
			usersList = userDao.findByName(user.getId()+"");
		 System.out.println("after attack ");
		for(User obj:usersList) {
				System.out.println(obj.getName());
			}
		 
		}
		mv.addObject("usersList", usersList);
		mv.addObject("userForm", user);
		return mv;
	}
	
	 /**
     * Vuln Code.
     * ReflectXSS
     * http://localhost:8080/xssattack?xss=<script>alert(1)</script>
     *
     * @param xss unescape string
     */
    @RequestMapping("/xssattack")
    @ResponseBody
    public static String reflect(String xss) {
        return xss;
    }
    
    @RequestMapping("/xsssafe")
    @ResponseBody
    public static String safe(String xss) {
        return encode(xss);
    }
    
    /** Command Injection
     * http://localhost:8080/codeinject?filepath=/tmp;cat /etc/passwd
     *
     * @param filepath filepath
     * @return result
     */
    @GetMapping("/codeinject")
    public String codeInject(String filepath) throws IOException {

        String[] cmdList = new String[]{"sh", "-c", "ls -la " + filepath};
        ProcessBuilder builder = new ProcessBuilder(cmdList);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        return convertStreamToString(process.getInputStream());
    }
    
    /** Redirect vunerability
     * http://localhost:8080/urlRedirect/redirect?url=http://www.google.com
     */
    @GetMapping("/redirect")
    public String redirect(@RequestParam("url") String url) {
        return "redirect:" + url;
    }
    
    /**JSONP vunerability
     * Set the response content-type to application/javascript.
     * <p>
     * http://localhost:8080/vuln/referer?callback_=test
     */
    @RequestMapping(value = "/vuln/referer", produces = "application/javascript")
    public String referer(HttpServletRequest request) {
        String callback = request.getParameter(this.callback);
        return Config.json2Jsonp(callback, getUserInfo2JsonStr(request));
    }
    
    /** PathTraversal
     * http://localhost:8080/path_traversal/vul?filepath=../../../../../etc/passwd
     */
    @GetMapping("/path_traversal/vul")
    public String getImage(String filepath) throws IOException {
        return Config.getImgBase64(filepath);
    }
    
    
    @GetMapping("/rce/exec")
    public String CommandExec(String cmd) {
        Runtime run = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();

        try {
            Process p = run.exec(cmd);
            BufferedInputStream in = new BufferedInputStream(p.getInputStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            String tmpStr;

            while ((tmpStr = inBr.readLine()) != null) {
                sb.append(tmpStr);
            }

            if (p.waitFor() != 0) {
                if (p.exitValue() == 1)
                    return "Command exec failed!!";
            }

            inBr.close();
            in.close();
        } catch (Exception e) {
            return "Except";
        }
        return sb.toString();
    }
    
    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
    public static String getUserInfo2JsonStr(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String username = principal.getName();
        Map<String, String> m = new HashMap<>();
        m.put("Username", username);

        return JSON.toJSONString(m);
    }
    
    private static String encode(String origin) {
        origin = StringUtils.replace(origin, "&", "&amp;");
        origin = StringUtils.replace(origin, "<", "&lt;");
        origin = StringUtils.replace(origin, ">", "&gt;");
        origin = StringUtils.replace(origin, "\"", "&quot;");
        origin = StringUtils.replace(origin, "'", "&#x27;");
        origin = StringUtils.replace(origin, "/", "&#x2F;");
        return origin;
    }
}
