import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QueryController {

	private QueryService queryService;

	@ResponseBody
    @RequestMapping(value = "/")
    public String helloWorld()
    {
        return "Hello world!";
    }

	@ResponseBody
	@RequestMapping(value = "/meetings", params = {"project"}, method=RequestMethod.GET)
	public String getMeetingCount(@RequestParam("project") String project)
	{
		String ret = "Year&nbsp&nbsp&nbsp&nbsp&nbsp&nbspcount<br /><br />";

		String years[] = new String[0];

		if (project != null) {
			years = queryService.getYearsFromEavesDrop(project);

			for (int i = 0; i < years.length; i++) {
				int count =  queryService.countLogs(years[i]);
				ret += years[i] + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" + count + "<br />";
				ret += "<br />";
			}
		}

		return ret;
	}

	public void setQueryService(QueryService queryService) {
		this.queryService = queryService;
	}
}
