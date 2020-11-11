package org.vunerability.demo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.vunerability.demo.dao.User;
import org.vunerability.demo.dao.UserDao;


@Controller
public class VunerabilityController {

	@Autowired
	UserDao userDao;
	
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
