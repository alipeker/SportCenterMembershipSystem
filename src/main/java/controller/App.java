package controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
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
import org.apache.commons.lang3.time.DateUtils;
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

import model.*;
import database.*;

@Controller
public class App {
	@RequestMapping(value = "/")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("home");
		modelAndView.addObject("error", request.getSession().getAttribute("invalidlogin"));
		request.getSession().setAttribute("invalidlogin", "");
		return modelAndView;
	}

	public static boolean checkIfExists(String[] myStringArray, String stringToLocate) {
		for (String element : myStringArray) {
			if (element.equals(stringToLocate)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/calendar")
	public ModelAndView calendar(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("member/calendar");

			ArrayList<JSONObject> lessons = new ArrayList<JSONObject>();

			LessonDatabase database = new LessonDatabase();
			ArrayList<Lesson> lessonobjects = database.getAllLesson();

			for (int j = 0; j < lessonobjects.size(); j++) {
				Lesson l = lessonobjects.get(j);
				String[] dates = l.getDates().split("-");
				Date start = l.getStartdate();
				Date start2 = l.getStartdate();
				Date end = l.getEnddate();
				int diffInDays = (int) ((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24));
				int k = 0;
				for (int i = 0; i <= diffInDays; i++) {
					if (checkIfExists(dates, (start.getDay() + i) % 7 + 1 + "")) {
						JSONObject lesson = new JSONObject();
						Date sumDate = new Date(start2.getTime() + (i - k) * (1000 * 60 * 60 * 24));
						start2 = sumDate;
						k = i;
						lesson.put("name", l.getCoursename() + "\n" + l.getBranchname());
						lesson.put("date", sumDate);
						lesson.put("url", "./showclass?id=" + l.getId());
						lessons.add(lesson);
					}
				}
			}
			modelAndView.addObject("dersler", lessons);
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}

		return modelAndView;
	}

}