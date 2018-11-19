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
import model.Staff;
import database.*;

@Controller
public class StaffController {
	private final String UPLOAD_DIRECTORY = "D:\\javaworks\\SportCenter\\src\\main\\webapp\\resources\\images\\employee\\";

	@RequestMapping(value = "/loginstaff")
	public ModelAndView loginstaff(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView;
		modelAndView = new ModelAndView("loginstaff");
		return modelAndView;
	}

	@RequestMapping(value = "/loginstaffprocess", method = RequestMethod.POST)
	public String loginstaffprocess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		request.getSession().invalidate();

		String role = request.getParameter("optradio");
		boolean activelogin = false;

		StaffDatabase database = new StaffDatabase();
		if (role.equals("T")) {
			activelogin = database.logincontroltrainer(request.getParameter("email"), request.getParameter("password"));
		} else if (role.equals("B")) {
			activelogin = database.logincontrolbranchmanager(request.getParameter("email"),
					request.getParameter("password"));
		} else if (role.equals("S")) {
			activelogin = database.logincontrolsystemmanager(request.getParameter("email"),
					request.getParameter("password"));
		}
		if (activelogin) {
			if (role.equals("T")) {
				request.getSession().setAttribute("trainer", "true");
				request.getSession().setAttribute("login_user", request.getParameter("email"));
				return "redirect:trainerprofile";
			} else if (role.equals("B")) {
				request.getSession().setAttribute("branchmanager", "true");
				request.getSession().setAttribute("login_user", request.getParameter("email"));
				return "redirect:branchmanagerprofile";
			} else if (role.equals("S")) {
				request.getSession().setAttribute("systemmanager", "true");
				request.getSession().setAttribute("login_user", request.getParameter("email"));
				return "redirect:systemmanagerprofile";
			}
		}
		return "redirect:loginstaff";
	}

	public static boolean checkIfExists(String[] myStringArray, String stringToLocate) {
		for (String element : myStringArray) {
			if (element.equals(stringToLocate)) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/editclass")
	public ModelAndView showClass(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, IOException, ParseException {
		ModelAndView modelAndView = null;
		LessonDatabase database = new LessonDatabase();
		Lesson l = database.getLesson(request.getParameter("id"));

		int control = 0;

		if (request.getSession().getAttribute("trainer") != null) {
			if (request.getSession().getAttribute("trainer").equals("true")) {
				modelAndView = new ModelAndView("trainer/lessons");
				control++;
			}
		}

		if (request.getSession().getAttribute("branchmanager") != null) {
			if (request.getSession().getAttribute("branchmanager").equals("true")) {
				modelAndView = new ModelAndView("branchmanager/lessons");
				control++;
			}
		}

		if (request.getSession().getAttribute("systemmanager") != null) {
			if (request.getSession().getAttribute("systemmanager").equals("true")) {
				modelAndView = new ModelAndView("systemmanager/lessons");
				control++;
			}
		}

		if (control != 0) {
			modelAndView.addObject("id", l.getId());
			modelAndView.addObject("coursename", l.getCoursename());
			modelAndView.addObject("branchname", l.getBranchname());
			modelAndView.addObject("branchoffice", l.getBranchoffice());
			modelAndView.addObject("trainername", l.getTrainername());
			modelAndView.addObject("lessondate", l.getStartdate().toString());
			modelAndView.addObject("enddate", l.getEnddate().toString());
			modelAndView.addObject("lessondates", l.getDates());

			StaffDatabase database2 = new StaffDatabase();
			ArrayList<String> offices = database2.getbranchoffices();
			ArrayList<JSONObject> jsonoffices = new ArrayList<JSONObject>();
			JSONObject office2 = new JSONObject();
			office2.put("name", l.getBranchoffice());
			jsonoffices.add(office2);
			for (String o : offices) {
				JSONObject office = new JSONObject();
				office.put("name", o);
				jsonoffices.add(office);
			}
			modelAndView.addObject("branchs", jsonoffices);

			ArrayList<String> branchs = database2.getbranchs();
			ArrayList<JSONObject> jsonbranchs = new ArrayList<JSONObject>();
			JSONObject branch2 = new JSONObject();
			branch2.put("name", l.getBranchname());
			jsonbranchs.add(branch2);
			for (String o : branchs) {
				JSONObject branch = new JSONObject();
				branch.put("name", o);
				jsonbranchs.add(branch);
			}
			modelAndView.addObject("branch", jsonbranchs);

		} else {
			modelAndView = new ModelAndView(new RedirectView("/loginstaff", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/editclassprocess", method = RequestMethod.GET)
	public String editClassProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			StaffDatabase database = new StaffDatabase();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = format.parse(request.getParameter("lessondate"));
			java.sql.Date startdate = new java.sql.Date(date.getTime());
			java.util.Date endate = format.parse(request.getParameter("enddate"));
			java.sql.Date enddate = new java.sql.Date(endate.getTime());
			database.editclassprocess(request.getParameter("id"), request.getParameter("coursename"), startdate,
					enddate, request.getParameter("lessondates"), request.getParameter("branchoffice"),
					request.getParameter("branch"));

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:manipulatelessonbranchmanager";
	}

	@RequestMapping(value = "/home")
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("trainer/home");
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}
		return modelAndView;
	}

	@RequestMapping(value = "/search")
	public ModelAndView searchUser(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView = null;
		int control2 = 0;
		if (request.getSession().getAttribute("trainer") != null) {
			control2 = 1;
		}
		if (request.getSession().getAttribute("branchmanager") != null) {
			control2 = 2;
		}
		if (request.getSession().getAttribute("systemmanager") != null) {
			control2 = 3;
		}

		if (control2 != 0) {
			String which = request.getParameter("optradio");
			if (which.equals("M")) {
				int control = 0;

				if (request.getSession().getAttribute("trainer") != null) {
					if (request.getSession().getAttribute("trainer").equals("true")) {
						modelAndView = new ModelAndView("trainer/searchuser");
						control++;
					}
				}

				if (request.getSession().getAttribute("branchmanager") != null) {
					if (request.getSession().getAttribute("branchmanager").equals("true")) {
						modelAndView = new ModelAndView("branchmanager/searchuser");
						control++;
					}
				}
				if (request.getSession().getAttribute("systemmanager") != null) {
					if (request.getSession().getAttribute("systemmanager").equals("true")) {
						modelAndView = new ModelAndView("systemmanager/searchuser");
						control++;
					}
				}
				if (control != 0) {
					try {
						Memberdb database = new Memberdb();
						if (request.getParameter("name") != null) {
							ArrayList<Member> users = database.searchuser("true",
									request.getParameter("name").toString());
							if (users != null) {
								JSONArray usersarray = new JSONArray();
								for (int i = 0; i < users.size(); i++) {
									JSONObject user = new JSONObject();
									user.put("email", users.get(i).getEmail());
									user.put("name", users.get(i).getName());
									user.put("surname", users.get(i).getSurname());
									user.put("userimage", users.get(i).getPicture());
									usersarray.put(user);
								}
								modelAndView.addObject("users", usersarray);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
				int control = 0;

				if (request.getSession().getAttribute("trainer") != null) {
					if (request.getSession().getAttribute("trainer").equals("true")) {
						modelAndView = new ModelAndView("trainer/searchcourse");
						control++;
					}
				}

				if (request.getSession().getAttribute("branchmanager") != null) {
					if (request.getSession().getAttribute("branchmanager").equals("true")) {
						modelAndView = new ModelAndView("branchmanager/searchcourse");
						control++;
					}
				}
				if (request.getSession().getAttribute("systemmanager") != null) {
					if (request.getSession().getAttribute("systemmanager").equals("true")) {
						modelAndView = new ModelAndView("systemmanager/searchcourse");
						control++;
					}
				}

				if (control != 0) {
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
				}
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("/", true));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/viewprofile")
	public ModelAndView viewProfile(HttpServletRequest request, HttpServletResponse response)
			throws ClassNotFoundException, SQLException, ParseException {
		ModelAndView modelAndView;

		if (request.getSession().getAttribute("login") == "true") {
			modelAndView = new ModelAndView("member/viewprofile");

			try {
				Memberdb database = new Memberdb();
				Member user = database.profile(request.getSession().getAttribute("login").toString(),
						request.getParameter("email").toString());

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

	@RequestMapping(value = "/deleteclassprocess", method = RequestMethod.GET)
	public String deleteClassProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			StaffDatabase database = new StaffDatabase();
			Boolean deletestate = database.deleteClassProcess(request.getParameter("id"));

		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		if (request.getSession().getAttribute("trainer") != null) {
			if (request.getSession().getAttribute("trainer").equals("true")) {
				return "redirect:manipulatelessontrainer";
			}
		}

		if (request.getSession().getAttribute("branchmanager") != null) {
			if (request.getSession().getAttribute("branchmanager").equals("true")) {
				return "redirect:manipulatelessonbranchmanager";
			}
		}
		if (request.getSession().getAttribute("systemmanager") != null) {
			if (request.getSession().getAttribute("systemmanager").equals("true")) {
				return "redirect:manipulatelessonsystemmanager";
			}
		}

		return "redirect:manipulatelessontrainer";
	}
	
	@RequestMapping(value = "/createlessonprocess", method = RequestMethod.GET)
	public String createClassProcess(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ClassNotFoundException, SQLException {
		try {
			String office = request.getParameter("branch");
			String branch = request.getParameter("lesson_id");
			String trainer = request.getParameter("trainerr");
			
			StaffDatabase database=new StaffDatabase();
			
			database.createclassprocess(request.getParameter("name"), request.getParameter("date"), 
					request.getParameter("enddate"), request.getParameter("days"), office, branch, trainer);
			
			
			if (request.getSession().getAttribute("branchmanager") != null) {
				if (request.getSession().getAttribute("branchmanager").equals("true")) {
					return "redirect:manipulatelessonbranchmanager";
				}
			}
			if (request.getSession().getAttribute("systemmanager") != null) {
				if (request.getSession().getAttribute("systemmanager").equals("true")) {
					return "redirect:manipulatelessonsystemmanager";
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

		return "redirect:manipulatelessonbranchmanager";
	}
}