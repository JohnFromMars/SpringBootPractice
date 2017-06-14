package com.marsbase.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.entity.StatusUpdate;
import com.marsbase.springboot.service.StatusUpdateService;

@Controller
public class StatusUpdateController {

	@Autowired
	private StatusUpdateService statusUpdateService;

	@RequestMapping(value = "/addstatus", method = RequestMethod.GET)
	public ModelAndView addStatus(ModelAndView modelAndView,
			@ModelAttribute("statusUpdate") StatusUpdate statusUpdate) {

		StatusUpdate latestUpdate = statusUpdateService.getLatest();

		modelAndView.setViewName("app.addStatus");
		modelAndView.getModel().put("latestUpdate", latestUpdate);

		return modelAndView;
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	public ModelAndView addStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result)
			throws InterruptedException {

		modelAndView.setViewName("app.addStatus");

		if (!result.hasErrors()) {
			statusUpdateService.save(statusUpdate);
			modelAndView.getModel().put("statusUpdate", new StatusUpdate());

			// redirect to view status
			modelAndView.setViewName("redirect:/viewstatus");
		}

		StatusUpdate latestUpdate = statusUpdateService.getLatest();

		modelAndView.getModel().put("latestUpdate", latestUpdate);

		return modelAndView;
	}

	@RequestMapping(value = "/viewstatus", method = RequestMethod.GET)
	public ModelAndView viewStatus(ModelAndView modelAndView,
			@RequestParam(name = "p", defaultValue = "1") int pageNumber) {

		modelAndView.setViewName("app.viewStatus");

		Page<StatusUpdate> page = statusUpdateService.getPage(pageNumber);

		modelAndView.getModel().put("page", page);

		return modelAndView;
	}

	@RequestMapping(value = "/editstatus", method = RequestMethod.GET)
	public ModelAndView editStatus(ModelAndView modelAndView, @RequestParam(name = "id") long id) {

		StatusUpdate statusUpdate = statusUpdateService.find(id);

		modelAndView.setViewName("app.editStatus");
		modelAndView.getModel().put("statusUpdate", statusUpdate);

		return modelAndView;
	}

	@RequestMapping(value = "/editstatus", method = RequestMethod.POST)
	public ModelAndView editStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {
		modelAndView.setViewName("app.editStatus");

		if (!result.hasErrors()) {
			modelAndView.setViewName("redirect:/viewstatus");
			statusUpdateService.save(statusUpdate);
		}

		return modelAndView;
	}

	@RequestMapping(value = "/deletestatus", method = RequestMethod.GET)
	public ModelAndView deleteStatus(ModelAndView modelAndView, @RequestParam(name = "id") long id) {

		modelAndView.setViewName("redirect:/viewstatus");

		// delete the data
		statusUpdateService.delete(id);

		return modelAndView;
	}

}
