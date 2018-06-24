package com.kushals.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kushals.service.AppService;

@RestController
@RequestMapping("/activity")
public class ViewingActivityController {

	@Autowired
	AppService appService;

	@PutMapping("/add")
	public String addActivity(@RequestBody Map<String, Object> viewingActivityObj) {

		return appService.insertViewingActivity(viewingActivityObj);
	}

	@GetMapping("/get/{id}")
	public Map<String, Object> getActivityByID(@PathVariable String id) {

		return appService.getActivityByID(id);
	}

	@GetMapping("/get")
	public List<Map<String, Object>> getContentsByActivity(@RequestParam("tag") String tag,
			@RequestParam("location") String location) {
		List<Map<String, Object>> matchingTagContentList = appService.getContentsByViewingActivity(tag, location);
		System.out.println(matchingTagContentList);
		return matchingTagContentList;
	}

}
