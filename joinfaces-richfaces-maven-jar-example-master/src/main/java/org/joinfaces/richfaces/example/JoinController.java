package org.joinfaces.richfaces.example;

import org.joinfaces.richfaces.example.dao.UserDao;

import java.util.List;

import org.joinfaces.richfaces.example.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JoinController {

	@Autowired
	UserDao userDao;
	
	@RequestMapping("/welcome")
	@ResponseBody
	public String firstPage() {
		List<User> user = userDao.findAll();
		for(User obj:user) {
			System.out.println(obj);
		}
		String user1 = userDao.findByName("kumar");
		System.out.println("aaaaaaaa "+user1.toString());
		
		return user1;
	}
}
