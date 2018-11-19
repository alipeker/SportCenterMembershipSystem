package controller;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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

import model.Lesson;
import model.Member;
import database.*;

@Controller
public class MemberController {
	private final String UPLOAD_DIRECTORY = "D:\\javaworks\\SportCenter\\src\\main\\webapp\\resources\\images\\";

	@RequestMapping(value = "/loginprocess", method = RequestMethod.POST)
	public String loginprocess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		request.getSession().invalidate();

		Memberdb database = new Memberdb();
		boolean activelogin = database.logincontrol(request.getParameter("email"), request.getParameter("password"));

		if (activelogin) {
			request.getSession().setAttribute("login", "true");
			request.getSession().setAttribute("login_user", request.getParameter("email"));
		} else {
			request.getSession().setAttribute("invalidlogin", "Email address and password do not match!");
		}
		return "redirect:profile";
	}

	@RequestMapping(value = "/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("member/profile");

			try {
				Memberdb database = new Memberdb();
				Member user = database.profile(request.getSession().getAttribute("login").toString(),
						request.getSession().getAttribute("login_user").toString());
				modelAndView.addObject("error", request.getSession().getAttribute("invalidpassword"));
				request.getSession().setAttribute("invalidpassword", "");
				if (user != null) {
					modelAndView.addObject("email", user.getEmail());
					modelAndView.addObject("name", user.getName());
					modelAndView.addObject("surname", user.getSurname());
					modelAndView.addObject("phone_number", user.getMobile_number());
					modelAndView.addObject("address", user.getAddress());
					modelAndView.addObject("birthday", user.getDate());
					modelAndView.addObject("picture", user.getPicture());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/editprofile")
	public ModelAndView editprofile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("member/editprofile");

			try {
				Memberdb database = new Memberdb();
				Member user = database.profile(request.getSession().getAttribute("login").toString(),
						request.getSession().getAttribute("login_user").toString());

				if (user != null) {
					modelAndView.addObject("email", user.getEmail());
					modelAndView.addObject("name", user.getName());
					modelAndView.addObject("surname", user.getSurname());
					modelAndView.addObject("phone_number", user.getMobile_number());
					modelAndView.addObject("address", user.getAddress());
					modelAndView.addObject("birthday", user.getDate());
					modelAndView.addObject("picture", user.getPicture());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "/register")
	public ModelAndView register(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("signup");
		if (request.getSession().getAttribute("error") != null) {
			if (request.getSession().getAttribute("error").equals("Email or Id Number already exist!")) {
				modelAndView.addObject("error", request.getSession().getAttribute("error"));
			}
		}
		request.getSession().setAttribute("error", "");
		return modelAndView;
	}

	@RequestMapping(value = "/registerprocess", method = RequestMethod.POST)
	public String registerprocess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = format.parse(request.getParameter("birthday"));
			java.sql.Date birthdaydate = new java.sql.Date(date.getTime());
			Memberdb database = new Memberdb();
			Member user;
			user = new Member(-1, request.getParameter("email"), request.getParameter("password"),
					request.getParameter("name"), request.getParameter("surname"), request.getParameter("id_number"),
					request.getParameter("sec_answer"), -1, birthdaydate, request.getParameter("mobile_number"),
					request.getParameter("address"), "default.png");
			boolean control = database.registerprocess(user);
			if (!control) {
				request.getSession().setAttribute("error", "Email or Id Number already exist!");
				return "redirect:register";
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		request.getSession().setAttribute("login", "true");
		request.getSession().setAttribute("login_user", request.getParameter("email"));
		return "redirect:profile";
	}

	@RequestMapping(value = "/editprofileprocess", method = RequestMethod.POST)
	public String editprofileprocess(@RequestParam("image") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		try {
			Memberdb database = new Memberdb();
			String foldername = UPLOAD_DIRECTORY + request.getSession().getAttribute("login_user");
			System.out.println(foldername);
			new File(foldername).mkdirs();

			String filename = file.getOriginalFilename().toString();
			String extension = "";

			int control = 0;
			for (int i = 0; i < filename.length(); i++) {
				if (filename.charAt(i) == '.') {
					control++;
				} else if (control != 0) {
					extension += filename.charAt(i);
				}
			}

			control = 0;
			InputStream in = new ByteArrayInputStream(file.getBytes());
			BufferedImage image = ImageIO.read(in);

			if (image != null) {
				ImageIO.write(image, extension, new File(foldername + "\\" + filename));
				database.editprofileprocess(request.getSession().getAttribute("login_user").toString(),
						request.getParameter("name"), request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"),
						request.getSession().getAttribute("login_user") + "/" + filename);
			} else {
				database.editprofileprocess(request.getSession().getAttribute("login_user").toString(),
						request.getParameter("name"), request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"), null);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:profile";
	}

	@RequestMapping(value = "/deleteprofileprocess", method = RequestMethod.GET)
	public String deleteprofileprocess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			Memberdb database = new Memberdb();
			Boolean deletestate = database.deleteprofileprocess(
					request.getSession().getAttribute("login_user").toString(), request.getParameter("password"));

			if (!deletestate) {
				request.getSession().setAttribute("invalidpassword", "Invalid Password");
				return "redirect:profile";
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:logout";
	}

	@RequestMapping(value = "/joinclass", method = RequestMethod.GET)
	public String joinClass(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			Memberdb database = new Memberdb();
			boolean control = database.joinClass(request.getParameter("id").toString(),
					request.getSession().getAttribute("login_user").toString());
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:calendar";
	}

	@RequestMapping(value = "/mylessons")
	public ModelAndView mylessons(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		ModelAndView modelAndView;
		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("member/mylessons");
			ArrayList<JSONObject> lessons = new ArrayList<JSONObject>();

			Memberdb database = new Memberdb();
			ArrayList<Lesson> lessonobjects = database
					.getLessons(request.getSession().getAttribute("login_user").toString());

			for (int j = 0; j < lessonobjects.size(); j++) {
				Lesson l = lessonobjects.get(j);
				JSONObject lesson = new JSONObject();
				lesson.put("id", l.getId());
				lesson.put("coursename", l.getCoursename());
				lesson.put("branchname", l.getBranchname());
				lesson.put("branchoffice", l.getBranchoffice());
				lesson.put("trainername", l.getTrainername());
				lesson.put("lessondate", l.getStartdate());
				lesson.put("enddate", l.getEnddate());
				lesson.put("lessondates", l.getDates());
				lessons.add(lesson);
			}
			modelAndView.addObject("lessons", lessons);
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/searchcourse")
	public ModelAndView searchuser(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("member/searchcourse");
			try {
				Memberdb database = new Memberdb();
				if (request.getParameter("name") != null) {
					ArrayList<Lesson> lessons = database.searchCourse(request.getParameter("name").toString());
					if (lessons != null) {
						JSONArray lessonarray = new JSONArray();
						for (int i = 0; i < lessons.size(); i++) {
							JSONObject lesson = new JSONObject();
							lesson.put("id", lessons.get(i).getId());
							lesson.put("name", lessons.get(i).getCoursename());
							lesson.put("trainername", lessons.get(i).getTrainername());
							lesson.put("branch", lessons.get(i).getBranchname());
							lesson.put("branchoffice", lessons.get(i).getBranchoffice());
							lessonarray.put(lesson);
						}
						modelAndView.addObject("users", lessonarray);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/payforlesson", method = RequestMethod.GET)
	public String payForLesson(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		return "redirect:logout";
	}

	@RequestMapping(value = "/payforlessonprocess", method = RequestMethod.POST)
	public String payForLessonProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		return "redirect:logout";
	}

	@RequestMapping(value = "/searchlesson", method = RequestMethod.GET)
	public String searchLesson(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		return "redirect:logout";
	}
}