package com.tomato.job.quartz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

/**
 * 项目首页
 *
 * @author lizhifu
 * @since 2023/9/17
 */
@Controller
public class IndexController {
	@GetMapping(value = {"/", "index.xhtm"})
	public String index(Model model) {
		model.addAttribute("date", LocalDateTime.now());
		return "index";
	}
	/**
	 * add  new job
	 */
	@GetMapping("add_job.xhtm")
	public String addJob(RedirectAttributes model) {
		return "redirect:index.xhtm";
	}

	/**
	 * remove  existed job
	 */
	@GetMapping("remove_job.xhtm")
	public String removeJob() {
		return "redirect:index.xhtm";
	}
}
