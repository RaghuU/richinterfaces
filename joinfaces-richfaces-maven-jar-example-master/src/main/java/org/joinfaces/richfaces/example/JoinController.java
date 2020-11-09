package org.joinfaces.richfaces.example;

import org.joinfaces.richfaces.example.dao.UserDao;

import java.util.List;

import org.joinfaces.richfaces.example.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JoinController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping("/welcome")
	public ModelAndView firstPage() {
		System.out.println("NEW");
		List<User> user = userDao.findAll();
		for(User obj:user) {
			System.out.println(obj);
		}
		User user1 = userDao.findByName("mkyong");
		System.out.println(""+user1.toString());
		
		return new ModelAndView("start.xhtml");
	}
}
