import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestQueryController {

	QueryController queryController = new QueryController();
	QueryService mockEditor = null;

	@Before
	public void setUp() {
		mockEditor = mock(QueryService.class);
		queryController.setQueryService(mockEditor);
	}

	@Test
	public void thisAlwaysPasses1() {
    }

	@Test
	public void testIndexPage1() {
		String reply = queryController.indexPage();
		assertEquals("Welcome to Assignment 3", reply);
	}

	@Test
	public void testGetMeetingCount1() {
		String projName = "3rd_party_ci";
		String years[] = new String[]{"2014"};
		String chk = "Year&nbsp&nbsp&nbsp&nbsp&nbsp&nbspcount<br /><br />2014&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp0<br /><br />";

		when(mockEditor.checkEavesDropConnection(projName)).thenReturn(1);
		when(mockEditor.getYearsFromEavesDrop(projName)).thenReturn(years);
		String ret = queryController.getMeetingCount(projName);
		assertEquals(chk, ret);
	}

	@Test
	public void testGetMeetingCount2() {
		String projName = "3rd_party_c";
		String chk = "Unknown project 3rd_party_c";

		when(mockEditor.checkEavesDropConnection(projName)).thenReturn(-1);
		String ret = queryController.getMeetingCount(projName);
		assertEquals(chk, ret);
	}
}
