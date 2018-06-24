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
@RequestMapping("/contents")
public class ContentController {

	@Autowired
	AppService appService;

	@GetMapping("/test")
	public String test() {
		return "running";
	}

	@PutMapping("/add")
	public String addContent(@RequestBody Map<String, Object> contentObj) {

		return appService.insertContent(contentObj);
	}

	@GetMapping("/get/{id}")
	public Map<String, Object> getContentByID(@PathVariable String id) {

		return appService.getContentsByID(id);
	}

	@GetMapping("/get")
	public List<Map<String, Object>> getContentsByTag(@RequestParam("tag") String tag) {
		List<Map<String, Object>> matchingTagContentList = appService.getContentsByTag(tag);
		System.out.println(matchingTagContentList);
		return matchingTagContentList;
	}

}
