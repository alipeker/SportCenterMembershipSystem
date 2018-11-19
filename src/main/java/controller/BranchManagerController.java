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

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
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
import model.Staff;
import database.*;

@Controller
public class BranchManagerController {
	private final String UPLOAD_DIRECTORY = "D:\\javaworks\\SportCenter\\src\\main\\webapp\\resources\\images\\employee\\";

	@RequestMapping(value = "/editprofilememberbranchmanager")
	public ModelAndView editprofile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login_user") != null) {
			modelAndView = new ModelAndView("branchmanager/membereditprofile");

			try {
				Memberdb database = new Memberdb();
				Member user = database.profile("true", request.getParameter("email").toString());

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

	@RequestMapping(value = "/branchmanagercreateuser")
	public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("branchmanager") == "true") {
			modelAndView = new ModelAndView("branchmanager/createmember");
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/branchmanagerregisterprocess", method = RequestMethod.POST)
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
				return "redirect:branchmanagercreateuser";
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:branchmanagermembers";
	}

	@RequestMapping(value = "/branchmanagereditprofileprocess", method = RequestMethod.POST)
	public String editprofileprocess(@RequestParam("image") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		try {
			Memberdb database = new Memberdb();
			String foldername = "D:\\javaworks\\SportCenter\\src\\main\\webapp\\resources\\images\\"
					+ request.getParameter("email");
			System.out.println(request.getParameter("email"));
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
				database.editprofileprocess(request.getParameter("email"), request.getParameter("name"),
						request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"), request.getParameter("email") + "/" + filename);
			} else {
				database.editprofileprocess(request.getParameter("email"), request.getParameter("name"),
						request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"), null);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:branchmanagermembers";
	}

	@RequestMapping(value = "/deleteprofilememberbranchmanager", method = RequestMethod.GET)
	public String deleteprofileprocess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			StaffDatabase database = new StaffDatabase();
			Boolean deletestate = database.deletememberprofileprocess(request.getParameter("email"));

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:branchmanagermembers";
	}

	@RequestMapping(value = "/branchmanagerprofile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("branchmanager").equals("true")) {
			modelAndView = new ModelAndView("branchmanager/profile");
			try {
				StaffDatabase database = new StaffDatabase();
				Staff trainer = database.branchmanagerprofile("T",
						request.getSession().getAttribute("login_user").toString());
				modelAndView.addObject("error", request.getSession().getAttribute("invalidpassword"));
				request.getSession().setAttribute("invalidpassword", "");
				if (trainer != null) {
					modelAndView.addObject("email", trainer.getEmail());
					modelAndView.addObject("name", trainer.getName());
					modelAndView.addObject("surname", trainer.getSurname());
					modelAndView.addObject("phone_number", trainer.getMobile_number());
					modelAndView.addObject("address", trainer.getAddress());
					modelAndView.addObject("birthday", trainer.getDate());
					modelAndView.addObject("picture", trainer.getPicture());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/branchmanagermembers")
	public ModelAndView lessons(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView=null;
		int control = 0;
		if (request.getSession().getAttribute("branchmanager") != null) {
			modelAndView = new ModelAndView("branchmanager/home");
			control++;
		} else if (request.getSession().getAttribute("systemmanager") != null) {
			modelAndView = new ModelAndView("systemmanager/home");
			control++;
		}
		if (control != 0) {
			ArrayList<JSONObject> memberlist = new ArrayList<JSONObject>();

			StaffDatabase database = new StaffDatabase();
			if (database.getAllMembers(request.getSession().getAttribute("login_user").toString())) {
				ArrayList<Member> members = database.getMembers();

				for (int j = 0; j < members.size(); j++) {
					JSONObject membersj = new JSONObject();
					membersj.put("id", members.get(j).getId());
					membersj.put("email", members.get(j).getEmail());
					membersj.put("phonenumber", members.get(j).getMobile_number());
					membersj.put("name", members.get(j).getName());
					membersj.put("surname", members.get(j).getSurname());
					membersj.put("birthday", members.get(j).getDate());
					membersj.put("address", members.get(j).getAddress());
					membersj.put("picture", members.get(j).getPicture());
					memberlist.add(membersj);
				}
				modelAndView.addObject("members", memberlist);
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/showclassbranchmanager")
	public ModelAndView showClass(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException {
		ModelAndView modelAndView;
		LessonDatabase database = new LessonDatabase();
		Lesson l = database.getLesson(request.getParameter("id"));

		if (request.getSession().getAttribute("login_user") != null) {
			modelAndView = new ModelAndView("branchmanager/showlesson");
			modelAndView.addObject("id", l.getId());
			modelAndView.addObject("coursename", l.getCoursename());
			modelAndView.addObject("branchname", l.getBranchname());
			modelAndView.addObject("branchoffice", l.getBranchoffice());
			modelAndView.addObject("trainername", l.getTrainername());
			modelAndView.addObject("lessondate", l.getStartdate().toString());
			modelAndView.addObject("enddate", l.getEnddate().toString());
			modelAndView.addObject("lessondates", l.getDates());

		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/viewprofilebranchmanager")
	public ModelAndView viewprofile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("branchmanager").equals("true")) {
			modelAndView = new ModelAndView("branchmanager/viewprofile");

			try {
				Memberdb database = new Memberdb();
				Member user = database.profile("true", request.getParameter("email").toString());

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

	@RequestMapping(value = "/manipulatelessonbranchmanager")
	public ModelAndView manipulatelesson(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;
		modelAndView = null;
		if ((request.getSession().getAttribute("branchmanager") != null)) {

			if (request.getSession().getAttribute("branchmanager").equals("true")) {
				modelAndView = new ModelAndView("branchmanager/manipulatelesson");
			}
		} else if ((request.getSession().getAttribute("systemmanager") != null)) {

			if (request.getSession().getAttribute("systemmanager").equals("true")) {
				modelAndView = new ModelAndView("systemmanager/manipulatelesson");
			}
		}
		StaffDatabase database = new StaffDatabase();
		ArrayList<JSONObject> lessons = new ArrayList<JSONObject>();
		if (database.getLessonsManipulateBranchManager(request.getSession().getAttribute("login_user").toString())) {
			ArrayList<Lesson> lessonobjects = database.getLessons();

			for (Lesson l : lessonobjects) {
				JSONObject lesson = new JSONObject();
				lesson.put("id", l.getId());
				lesson.put("coursename", l.getCoursename().toString());
				lesson.put("branchname", l.getBranchname().toString());
				lesson.put("startdate", l.getStartdate().toString());
				lesson.put("enddate", l.getEnddate().toString());
				lesson.put("dates", l.getDates().toString());
				lessons.add(lesson);
			}

			modelAndView.addObject("lessons", lessons);

		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
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

	@RequestMapping(value = "/branchmanagercalendar")
	public ModelAndView calendar(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("branchmanager").equals("true")) {
			modelAndView = new ModelAndView("branchmanager/calendar");

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
						lesson.put("url", "./editclass?id=" + l.getId());
						lessons.add(lesson);
					}
				}
			}
			modelAndView.addObject("dersler", lessons);
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/editbranchmanagerprofile")
	public ModelAndView edittrainerprofile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("branchmanager").equals("true")) {
			modelAndView = new ModelAndView("branchmanager/editprofile");

			try {
				StaffDatabase database = new StaffDatabase();
				Staff trainer = database.branchmanagerprofile("T",
						request.getSession().getAttribute("login_user").toString());

				if (trainer != null) {
					modelAndView.addObject("email", trainer.getEmail());
					modelAndView.addObject("name", trainer.getName());
					modelAndView.addObject("surname", trainer.getSurname());
					modelAndView.addObject("phone_number", trainer.getMobile_number());
					modelAndView.addObject("address", trainer.getAddress());
					modelAndView.addObject("birthday", trainer.getDate());
					modelAndView.addObject("picture", trainer.getPicture());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/editbranchmanagerprofileprocess", method = RequestMethod.POST)
	public String editTrainerProfileProcess(@RequestParam("image") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException {
		try {
			StaffDatabase database = new StaffDatabase();
			String foldername = UPLOAD_DIRECTORY + request.getSession().getAttribute("login_user");
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
						"employee/" + request.getSession().getAttribute("login_user") + "/" + filename);
			} else {
				database.editprofileprocess(request.getSession().getAttribute("login_user").toString(),
						request.getParameter("name"), request.getParameter("surname"), request.getParameter("birthday"),
						request.getParameter("address"), null);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:branchmanagerprofile";
	}
	
	@RequestMapping(value = "/branchmanagercreatelesson")
	public ModelAndView createLesson(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("branchmanager") == "true") {
			modelAndView = new ModelAndView("branchmanager/createlesson");
			StaffDatabase database2 = new StaffDatabase();
			ArrayList<String> offices = database2.getbranchoffices();
			ArrayList<JSONObject> jsonoffices = new ArrayList<JSONObject>();
			for (String o : offices) {
				JSONObject office = new JSONObject();
				office.put("name", o);
				jsonoffices.add(office);
			}
			modelAndView.addObject("branchs", jsonoffices);

			ArrayList<String> branchs = database2.getbranchs();
			ArrayList<JSONObject> jsonbranchs = new ArrayList<JSONObject>();
			for (String o : branchs) {
				JSONObject branch = new JSONObject();
				branch.put("name", o);
				jsonbranchs.add(branch);
			}
			modelAndView.addObject("branch", jsonbranchs);
			
			ArrayList<String> trainers = database2.getTrainers();
			ArrayList<JSONObject> jsontrainers = new ArrayList<JSONObject>();
			for (String o : trainers) {
				JSONObject trainer = new JSONObject();
				trainer.put("name", o);
				jsontrainers.add(trainer);
			}
			modelAndView.addObject("trainers", jsontrainers);
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}
}