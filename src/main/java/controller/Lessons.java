package controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import model.Member;
import model.Lesson;
import database.LessonDatabase;
import database.Memberdb;;;

@Controller
public class Lessons {

	@RequestMapping(value = "/showclass")
	public ModelAndView showClass(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		ModelAndView modelAndView;
		LessonDatabase database=new LessonDatabase();
		Lesson l=database.getLesson(request.getParameter("id"));
		
		if (request.getSession().getAttribute("login_user") != null) {
			modelAndView = new ModelAndView("member/lessons");
			modelAndView.addObject("id",l.getId());
			modelAndView.addObject("coursename",l.getCoursename());
			modelAndView.addObject("branchname",l.getBranchname());
			modelAndView.addObject("branchoffice",l.getBranchoffice());
			modelAndView.addObject("trainername",l.getTrainername());
			modelAndView.addObject("lessondate",l.getStartdate().toString());
			modelAndView.addObject("enddate",l.getEnddate().toString());
			modelAndView.addObject("lessondates",l.getDates());
			
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/searchClass")
	public ModelAndView searchClass(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView=null;
		return modelAndView;
	}
	
	@RequestMapping(value = "/searchclassprocess", method = RequestMethod.POST)
	public String searchClassProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		return "redirect:logout";
	}
	
	
}