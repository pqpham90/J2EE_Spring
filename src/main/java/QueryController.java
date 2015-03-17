import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QueryController {

	private QueryService queryService;

	// message for the home page
	@ResponseBody
    @RequestMapping(value = "/")
    public String indexPage()
    {
        return "Welcome to Assignment 3";
    }

	// counts the number of log.html files given a project param
	@ResponseBody
	@RequestMapping(value = "/meetings", params = {"project"}, method=RequestMethod.GET)
	public String getMeetingCount(@RequestParam("project") String project)
	{
		String ret = "";

		String years[];


		if (project != null) {
			if (queryService.checkEavesDropConnection(project) == -1) {
				ret = "Unknown project " + project;
			}
			else {
				years = queryService.getYearsFromEavesDrop(project);
				ret = "Year&nbsp&nbsp&nbsp&nbsp&nbsp&nbspcount<br /><br />";

				for (int i = 0; i < years.length; i++) {
					int count =  queryService.countLogs(years[i]);
					ret += years[i] + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + count + "<br />";
					ret += "<br />";
				}
			}
		}


		return ret;
	}

	// for unit testing
	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
}
