
public interface QueryService {
	public int checkEavesDropConnection (String projectName);

	public String[] getYearsFromEavesDrop(String projectName);

	public int countLogs (String year);
}
