package act.reports.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.SessionAttributes;

import act.reports.dao.UserAuthDAO;
import act.reports.model.UserDetail;
import act.reports.util.EncodingUtil;




/**
 * Servlet Filter implementation class PreAuthSessionFilter
 */
@SessionAttributes("userDetails")
public class PreAuthSessionFilter implements Filter {

	private Logger logger=Logger.getLogger(PreAuthSessionFilter.class);
	final String actSessonCookieName = "actsession";
	final String actUserKeyCookieName = "actuserkey";
	public static boolean userValidated = false;
	UserDetail userDetails = null;



	/**
	 * Default constructor. 
	 */
	public PreAuthSessionFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Autowired
	private UserAuthDAO userDao;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		String sessionIdFromCookie = null;
		String userIdFromCookie = null;

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding("UTF-8");
		HttpSession session =  req.getSession();

		if (session.getAttribute("userDetails") == null && !req.getRequestURI().equalsIgnoreCase("/actreports/payment/response.html"))
		{
			String sessionID = (String) req.getParameter("session");
			String userId = req.getParameter("userIdKey");
			
			Cookie[] cookies = req.getCookies();
			if (cookies != null){
				for (Cookie ck : cookies) {
					if (actSessonCookieName.equals(ck.getName())) {

						sessionIdFromCookie = ck.getValue();


						//   req.setAttribute("myCoolObject", myObject)
					}
					else if(actUserKeyCookieName.equals(ck.getName()))
					{

						userIdFromCookie = ck.getValue();


					}


				}
			}
			logger.info("sessionID in cookie is  >> " + sessionIdFromCookie);
			logger.info("UserID in cookie is  >> " + userIdFromCookie);

			if (!(sessionID == null) && !(sessionID.isEmpty()) && (!(sessionIdFromCookie == null) && !(sessionIdFromCookie.isEmpty())))
			{
				//  if (sessionID.equalsIgnoreCase(sessionIdFromCookie)){

				if (!(userId == null) && !(userId.isEmpty()) && (!(userIdFromCookie == null) && !(userIdFromCookie.isEmpty())))

				{

					if(userId.equalsIgnoreCase(userIdFromCookie))
					{
						userDetails=userDao.getUserDetails(EncodingUtil.decode(userId));


						session.setAttribute("userDetails", userDetails);
						chain.doFilter(request, response);
						//Put user details in session and use further
					}
					else
					{
						logger.info("User Id not matching  please login >> " );
						res.sendRedirect("/acthome");
					}
				}

				//	  }
				else
				{
					logger.info("SEssion is invalid please login >> " );
					res.sendRedirect("/acthome");
				}
			}
			else
			{
				res.sendRedirect("/acthome");
			}

		} 
		else
		{
			chain.doFilter(request, response);
		}
		// pass the request along the filter chain



		//	

	}
	/*	public UserAuthDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserAuthDAO userDao) {
		this.userDao = userDao;
	} */

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub

		logger.info("PreAuthSessionFilter is called");
	}

}
