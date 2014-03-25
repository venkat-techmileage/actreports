package act.reports.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DashboardController {

	private Logger logger=Logger.getLogger(DashboardController.class);

	final String actSessonCookieName = "actsession";
	final String actUserKeyCookieName = "actuserkey";

	final Boolean useSecureCookie = new Boolean(false);
	final int expiryTime = -1 ;  
	final String cookiePath = "/";

	@RequestMapping({"/","/dashboard.html"})
	public String getReportsDashboard(@RequestParam("session") String sessionId, @RequestParam("userIdKey") String userIdKey, HttpServletRequest request){
		logger.info("In DashboardController - getReportsDashboard()...");
		try{
			//System.out.println("sessionId in DashboardController - getReportsDashboard() : "+sessionId);
			//System.out.println("userIdKey in DashboardController - getReportsDashboard() : "+userIdKey);
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession(true);
			session.setAttribute("sessionId", sessionId);
			session.setAttribute("usrIdKey", userIdKey);
		}
		catch(Exception e){
			logger.error(e);
		}
		return "dashboard";
	}

	@RequestMapping("/goHome")
	public String goHome(ModelAndView model, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();

		if(session != null) {
			session.setAttribute("userDetails",null);
			session.invalidate();
		}

		return "redirect:../acthome/goHome";

	}   

	@RequestMapping("/logout")
	public String signOut(ModelAndView model, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession();

		if(session != null) {
			session.removeAttribute("userDetails");
		}

		Cookie myCookie = new Cookie(actSessonCookieName, "");
		Cookie myuserCookie = new Cookie(actUserKeyCookieName,"");
		myCookie.setSecure(useSecureCookie.booleanValue());
		myCookie.setMaxAge(expiryTime);
		myCookie.setPath(cookiePath); 

		myuserCookie.setSecure(useSecureCookie.booleanValue());
		myuserCookie.setMaxAge(expiryTime);
		myuserCookie.setPath(cookiePath); 

		response.addCookie(myCookie);
		response.addCookie(myuserCookie); 
		return "redirect:../acthome/";

	}
}
