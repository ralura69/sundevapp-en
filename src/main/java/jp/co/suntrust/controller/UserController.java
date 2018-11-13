package jp.co.suntrust.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jp.co.suntrust.bean.User;
import jp.co.suntrust.repository.UserRepository;
import jp.co.suntrust.service.UserService;

@Controller
public class UserController {

	/** ロガー */
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	ModelAndView model;

	public UserController() {
		model = new ModelAndView();
	}

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = { "/", "/login" })
	public String index() {
		log.info("login");
		return "user/login";
	}

	@GetMapping(value = { "/list" })
	public ModelAndView list() {
		log.info("user list");
		setUserListMode();
		return model;
	}

	@PostMapping(value = { "/doLogin" })
	public String userLogin(@Valid String userID, @Valid String password) {
		log.info("doLogin");
		User existsUser = userService.findUser(userID, password);
		String result = "";
		if (existsUser != null) {
			result = "user/userList";
		} else {
			result = "/";
		}
		return result;
	}

	@GetMapping(value = { "/new" })
	public ModelAndView newUser() {
		setSexListMode();
		model.setViewName("user/new");
		return model;
	}

	@GetMapping(value = { "/edit" })
	public ModelAndView userEdit(@Valid String userID) {
		User user = userService.findUserByID(userID);
		model.addObject("user", user);
		setSexListMode();
		model.setViewName("user/detail");
		return model;
	}

	@PostMapping(value = { "/doEdit" })
	public ModelAndView saveUser(@Valid User user) {
		userService.saveUser(user);
		setUserListMode();
		return model;
	}

	private void setSexListMode() {
		Map<Integer, String> sexList = new LinkedHashMap<>();
		sexList.put(1, "男");
		sexList.put(2, "女");
		model.addObject("sexList", sexList);
	}

	private void setUserListMode() {
		List<User> list = userRepository.findAll();
		model.addObject("userList", list);
		model.setViewName("user/userList");
	}

	//	@GetMapping(value = { "/regist" })
	//	public ModelAndView addUser() {
	//		log.info("addUser");
	//		model.addObject("user", new User());
	//		model.setViewName("user/add");
	//		return model;
	//	}
	//
	//	@PostMapping(value = { "/regist" })
	//	public ModelAndView createUser(@Valid User user, BindingResult result) {
	//		log.info("createUser");
	//		User existsUser = userService.findUserByID(user.getUserID());
	//		if (existsUser != null) {
	//			result.rejectValue("userID", "exists user", "該当ユーザは既に存在しています。");
	//		}
	//		if (result.hasErrors()) {
	//		} else {
	//			userService.saveUser(user);
	//			model.addObject("msg", "ユーザ追加完了しました。");
	//			model.addObject("user", new User());
	//		}
	//		model.setViewName("user/regist");
	//		return model;
	//	}
	//
	//	@GetMapping(value = { "home/home" })
	//	public ModelAndView goHome() {
	//		log.info("goHome");
	//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	//		User user = userService.findUserByID(auth.getName());
	//		model.addObject("userName", user.getName());
	//		model.setViewName("home/home");
	//		return model;
	//	}
	//
	//	public ModelAndView accessDenied() {
	//		log.info("accessDenied");
	//		model.setViewName("errors/access_denied");
	//		return model;
	//	}

}
