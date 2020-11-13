package org.vunerability.demo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.vunerability.demo.beans.Config;
import org.vunerability.demo.beans.Greeting;
import org.vunerability.demo.beans.User;
import org.vunerability.demo.dao.UserDao;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class VunerabilityRestController {

	@Autowired
	UserDao userDao;
	
	private String callback = Config.getBusinessCallback();
	
	private static final String template = "Hello, %s!";

	private final AtomicLong counter = new AtomicLong();
	
	
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
    
    
/*	Server-side request
	forgery (also known as SSRF) is a web security vulnerability 
	that allows an attacker to induce the server-side application to make 
	HTTP requests to an arbitrary domain of the attacker's choosing.
    In typical SSRF examples, the attacker might cause the server to make a c
    onnection back to itself, or to other web-based services within the organization's 
    infrastructure, or to external third-party systems.
  */  
    
    /**
     * The default setting of followRedirects is true.
     * UserAgent is Java/1.8.0_102.
     *  http://localhost:8080/ssrf/vuln?url=dict://localhost:11211/stat HTTP/1.1
     */
    
   @RequestMapping(value = "/ssrf/vuln", method = {RequestMethod.POST, RequestMethod.GET})
   public String URLConnectionVuln(String url) {
       return Config.URLConnection(url);
   }
   
   @GetMapping("/xstream")
   public String parseXml(HttpServletRequest request) throws Exception {
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

   
   	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/crossorigin")
	public Greeting greeting(@RequestParam(required = false, defaultValue = "World") String name) {
		System.out.println("==== get greeting ====");
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
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
