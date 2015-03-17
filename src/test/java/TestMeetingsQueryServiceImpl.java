import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestMeetingsQueryServiceImpl {

	MeetingsQueryServiceImpl meetingsQuery = null;

	@Before
	public void setUp() {
		meetingsQuery = new MeetingsQueryServiceImpl();
	}

	@Test
	public void testResetUrl() {
		meetingsQuery.resetUrl();
		String baseURL = meetingsQuery.baseURL;

		assert (baseURL == "http://eavesdrop.openstack.org/");
	}

	@Test
	public void testCheckEavesDropConnection1() {
		int chk = 1;
		int ret = meetingsQuery.checkEavesDropConnection("nova");

		assertEquals(chk, ret);
	}

	@Test
	public void testCheckEavesDropConnection2() {
		int chk = -1;
		int ret = meetingsQuery.checkEavesDropConnection("novas");

		assertEquals(chk, ret);
	}

	@Test
	public void testGetYearsFromEavesDrop1() {
		String project = "barbican";

		String chk[] = new String[]{"2013", "2014", "2015"};
		String ret[] = meetingsQuery.getYearsFromEavesDrop(project);

		assertEquals(chk, ret);
	}

	@Test
	public void testGetYearsFromEavesDrop2() {
		String project = "octavia";

		String chk[] = new String[]{"2014", "2015"};
		String ret[] = meetingsQuery.getYearsFromEavesDrop(project);

		assertEquals(chk, ret);
	}

	@Test
	public void testReadDataFromEavesdrop1() {
		try {
			String exampleString = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
			exampleString += "<html>";
			exampleString += "</html>";

			URLConnection connection = mock(URLConnection.class); // Create mock dependency: mock()

			InputStream i = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

			when(connection.getInputStream()).thenReturn(i); // Setting up the expectations

			String result = meetingsQuery.readDataFromEavesdrop(connection);

			assertEquals(exampleString, result);

			verify(connection).getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadDataFromEavesdrop2() {
		try {
			String exampleString = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
			exampleString += "<html>";
			exampleString += "<head><title>Index of /meetings/3rd_party_ci</title></head><body><h1>Index of /meetings/3rd_party_ci</h1><table><tr><th><img src=\"/icons/blank.gif\" alt=\"[ICO]\"></th><th><a href=\"?C=N;O=D\">Name</a></th><th><a href=\"?C=M;O=A\">Last modified</a></th><th><a href=\"?C=S;O=A\">Size</a></th><th><a href=\"?C=D;O=A\">Description</a></th></tr><tr><th colspan=\"5\"><hr></th></tr><tr><td valign=\"top\"><img src=\"/icons/back.gif\" alt=\"[DIR]\"></td><td><a href=\"/meetings/\">Parent Directory</a></td><td>&nbsp;</td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"2014/\">2014/</a></td><td align=\"right\">10-Mar-2014 18:14  </td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><th colspan=\"5\"><hr></th></tr></table></body>";
			exampleString += "</html>";

			URLConnection connection = mock(URLConnection.class); // Create mock dependency: mock()

			InputStream i = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

			when(connection.getInputStream()).thenReturn(i); // Setting up the expectations

			String result = meetingsQuery.readDataFromEavesdrop(connection);

			assertEquals(exampleString, result);

			verify(connection).getInputStream();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCheckForYears1() {
		String project = "nova";

		String chk[] = new String[]{"2012", "2013", "2014", "2015"};

		String exampleString = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
		exampleString += "<html>";
		exampleString += "<head><title>Index of /meetings/nova</title></head><body><h1>Index of /meetings/nova</h1><table><tr><th><img src=\"/icons/blank.gif\" alt=\"[ICO]\"></th><th><a href=\"?C=N;O=D\">Name</a></th><th><a href=\"?C=M;O=A\">Last modified</a></th><th><a href=\"?C=S;O=A\">Size</a></th><th><a href=\"?C=D;O=A\">Description</a></th></tr><tr><th colspan=\"5\"><hr></th></tr><tr><td valign=\"top\"><img src=\"/icons/back.gif\" alt=\"[DIR]\"></td><td><a href=\"/meetings/\">Parent Directory</a></td><td>&nbsp;</td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"2012/\">2012/</a></td><td align=\"right\">20-Dec-2012 21:17  </td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"2013/\">2013/</a></td><td align=\"right\">19-Dec-2013 21:41  </td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"2014/\">2014/</a></td><td align=\"right\">18-Dec-2014 22:00  </td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"2015/\">2015/</a></td><td align=\"right\">12-Mar-2015 21:53  </td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><th colspan=\"5\"><hr></th></tr></table></body>";
		exampleString += "</html>";

		String ret[] = meetingsQuery.checkForYears(exampleString);

		assertEquals(chk, ret);
	}

	@Test
	public void testCheckForYears2() {
		String project = "python3";

		String chk[] = new String[]{"2013"};

		String exampleString = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\">";
		exampleString += "<html>";
		exampleString += "<head><title>Index of /meetings/python3</title></head><body><h1>Index of /meetings/python3</h1><table><tr><th><img src=\"/icons/blank.gif\" alt=\"[ICO]\"></th><th><a href=\"?C=N;O=D\">Name</a></th><th><a href=\"?C=M;O=A\">Last modified</a></th><th><a href=\"?C=S;O=A\">Size</a></th><th><a href=\"?C=D;O=A\">Description</a></th></tr><tr><th colspan=\"5\"><hr></th></tr><tr><td valign=\"top\"><img src=\"/icons/back.gif\" alt=\"[DIR]\"></td><td><a href=\"/meetings/\">Parent Directory</a></td><td>&nbsp;</td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><td valign=\"top\"><img src=\"/icons/folder.gif\" alt=\"[DIR]\"></td><td><a href=\"2013/\">2013/</a></td><td align=\"right\">16-May-2013 16:38  </td><td align=\"right\">  - </td><td>&nbsp;</td></tr><tr><th colspan=\"5\"><hr></th></tr></table></body>";
		exampleString += "</html>";

		String ret[] = meetingsQuery.checkForYears(exampleString);

		assertEquals(chk, ret);
	}

	@Test
	public void testCoungLogs1() {
		meetingsQuery.project = "nova";
		String year = "2013";

		int chk = 44;

		int ret = meetingsQuery.countLogs(year);

		assertEquals(chk, ret);
	}

	@Test
	public void testCoungLogs2() {
		meetingsQuery.project = "nova";
		String year = "2014";

		int chk = 43;

		int ret = meetingsQuery.countLogs(year);

		assertEquals(chk, ret);
	}
}
