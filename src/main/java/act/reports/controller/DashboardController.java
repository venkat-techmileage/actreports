package act.reports.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

	//private Logger logger=Logger.getLogger(DashboardController.class);
	
	@RequestMapping("/dashboard.html")
	public String getReportsDashboard(){
		return "dashboard";
	}
	
}
