package com.favorites.web;

import com.favorites.comm.Const;
import com.favorites.comm.aop.LoggerManage;
import com.favorites.domain.Config;
import com.favorites.domain.Favorites;
import com.favorites.domain.User;
import com.favorites.repository.ConfigRepository;
import com.favorites.repository.FavoritesRepository;
import com.favorites.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private ConfigRepository configRepository;
    @Autowired
    private UserRepository userRepository;

	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	@LoggerManage(description="首页")
	public String index(Model model){
		
		User user = super.getUser();
		if(null != user){
			model.addAttribute("user",user);
		}
		return "index";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	@LoggerManage(description="登陆后首页")
	public String home(Model model) {

		Config config = configRepository.findByUserId(getUserId());
		Favorites favorites = favoritesRepository.findOne(Long.parseLong(config.getDefaultFavorties()));
		model.addAttribute("config",config);
		model.addAttribute("favorites",favorites);
		model.addAttribute("user",getUser());
		logger.info("userID="+getUserId());
		return "home";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	@LoggerManage(description="登陆页面")
	public String login() {

		return "login";
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	@LoggerManage(description="注册页面")
	public String regist() {
		return "register";
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@LoggerManage(description="登出")
	public String logout(HttpServletResponse response,Model model) {
		getSession().removeAttribute(Const.LOGIN_SESSION_KEY);
		getSession().removeAttribute(Const.LAST_REFERER);
		Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);

		return "index";
	}

	@RequestMapping(value="/forgotPassword",method=RequestMethod.GET)
	@LoggerManage(description="忘记密码页面")
	public String forgotPassword() {
		return "user/forgotpassword";
	}
	
	@RequestMapping(value="/newPassword",method=RequestMethod.GET)
	public String newPassword(String email) {
		return "user/newpassword";
	}

}