package org.vunerability.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.vunerability.demo.dao.User;
import org.vunerability.demo.dao.UserDao;


@Controller
public class VunerabilityController {

	@Autowired
	UserDao userDao;
	
	
    private static String UPLOADED_FOLDER = "/tmp/";
    
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
	
	 @GetMapping("/fileupload/pic")
	    public String uploadPic() {
	        return "uploadPic"; // return uploadPic.html page
	    }

	    @PostMapping("/fileupload/upload")
	    public String singleFileUpload(@RequestParam("file") MultipartFile file,
	                                   RedirectAttributes redirectAttributes) {
	        if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            return "redirect:/file/status";
	        }

	        try {
	            // Get the file and save it somewhere
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
	            Files.write(path, bytes);

	            redirectAttributes.addFlashAttribute("message",
	                    "You successfully uploaded '" + UPLOADED_FOLDER + file.getOriginalFilename() + "'");

	        } catch (IOException e) {
	            redirectAttributes.addFlashAttribute("message", "upload failed");
	            e.printStackTrace();
	            return "redirect:/file/status";
	        }

	        return "redirect:/file/status";
	    }
    
}
