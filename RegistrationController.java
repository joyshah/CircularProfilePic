package com.infosys.irs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.infosys.irs.exception.UserIdAlreadyPresentException;
import com.infosys.irs.model.User;
import com.infosys.irs.service.RegistrationService;

@Controller
public class RegistrationController {
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private Environment environment;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(Model model) {
		return new ModelAndView("register", "command", new User());
	}
	
	
	@RequestMapping(value = "registerUser", method = RequestMethod.POST)
	public ModelAndView addCustomer(@Valid @ModelAttribute("command") User user, BindingResult result,
			ModelMap model) {
		ModelAndView modelAndView = new ModelAndView();
		if (result.hasErrors()) {

			modelAndView= new ModelAndView("register", "command", user);
		} 
		else{
		try{
			registrationService.registerUser(user);
			//modelAndView = new ModelAndView("registrationSucess");
			modelAndView= new ModelAndView("register", "command", user);
			modelAndView.addObject("successMessage",environment.getProperty("RegistrationController.SUCCESSFUL_REGISTRATION"));
			
		}catch(UserIdAlreadyPresentException e){
		
			if (e.getMessage().contains("RegistrationService")) {
				modelAndView = new ModelAndView("register"); 
				modelAndView.addObject("command",user);
				modelAndView.addObject("message", environment.getProperty(e.getMessage()));
		
			}

			
		}
		}
		return modelAndView;
	}
	/*@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public ModelAndView register(Model model) {

		return new ModelAndView("Register", "command", new Customer());
	}

	@RequestMapping(value = "/Success.htm", method = RequestMethod.POST)
	public ModelAndView addCustomer(@Valid @ModelAttribute("command") Customer customer, BindingResult result,
			ModelMap model) {
		if (result.hasErrors()) {

			return new ModelAndView("Register", "command", customer);
		} else {
			Boolean validUser = restTemplate.postForObject("http://localhost:8080/AirlineRS/Registration/service", customer,
					Boolean.class);
			System.out.println("aftr validUser");
			if (validUser == true) {
				
				return new ModelAndView("Success");
			} else
				return new ModelAndView("Failure", "command", "User with the same username already exists!");

		}
	}*/
}
