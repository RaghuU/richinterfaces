package org.joinfaces.richfaces.example;

import org.joinfaces.richfaces.example.dao.UserDao;

import java.util.List;
import java.util.Map;

import org.joinfaces.richfaces.example.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JoinController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping("/welcome")
	public String firstPage(Map<String, Object> model) {
		/*
		 * List<User> user = userDao.findAll(); for(User obj:user) {
		 * System.out.println(obj); } user = userDao.findByName("1 OR 1=1");
		 * System.out.println("after attack "); for(User obj:user) {
		 * System.out.println(obj.getName()); }
		 */
		 User userForm = new User();
		 model.put("userForm", userForm);
		
		return "secdemo";
	}
	@PostMapping("/userserch")
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
}
