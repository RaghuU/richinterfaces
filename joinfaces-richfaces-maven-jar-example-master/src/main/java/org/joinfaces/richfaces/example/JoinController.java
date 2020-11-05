package org.joinfaces.richfaces.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JoinController {

	@RequestMapping("/welcome")
	public ModelAndView firstPage() {
		System.out.println("Hello");
		return new ModelAndView("start.xhtml");
	}
}
