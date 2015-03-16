import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.ListIterator;

public class MeetingsQueryServiceImpl implements QueryService {
	URL eavesdropURL = null;
	String baseURL = "";
	String project = "";

	protected void resetUrl() {
		eavesdropURL = null;
		baseURL = "http://eavesdrop.openstack.org/";
		try {
			eavesdropURL = new URL(baseURL);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public MeetingsQueryServiceImpl() {
		resetUrl();
	}

	public String[] getYearsFromEavesDrop(String projectName) {
		String[] years = new String[0];

		resetUrl();
		project = projectName;
		baseURL += "meetings/" + project;
		try {
			eavesdropURL = new URL(baseURL);
			URLConnection connection = eavesdropURL.openConnection();
			String readData = readDataFromEavesdrop(connection);
			years = checkForYears(readData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return years;
	}

	protected String readDataFromEavesdrop(URLConnection connection) {
		String retVal = "";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				retVal += inputLine;
			}
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;
	}

	protected String[] checkForYears (String inputString) {
		ArrayList<String> years = new ArrayList<String>();
		try {
			Document doc = Jsoup.parse(inputString);

			Elements links = doc.select("body a");
			ListIterator<Element> iter = links.listIterator();
			while(iter.hasNext()) {
				Element e = iter.next();
				String s = e.html();

				if(s.matches("^[0-9]+/")) {
					years.add(s.replace("/", ""));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		String[] retVal = new String[years.size()];
		retVal = years.toArray(retVal);
		return retVal;
	}

	public int countLogs(String year) {
		resetUrl();

		int count = 0;

		baseURL += ("meetings/" + project + "/" + year);
		try {
			eavesdropURL = new URL(baseURL);
			URLConnection connection = eavesdropURL.openConnection();
			String readData = readDataFromEavesdrop(connection);

			Document doc = Jsoup.parse(readData);
			Elements links = doc.select("a[href]");
			ListIterator<Element> iter = links.listIterator();
			while(iter.hasNext()) {
				Element e = iter.next();
				String s = e.html();

				if(s.contains(".log.html")) {
					count++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}
}
