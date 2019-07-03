package processes;

public class CampsiteFormat {

	public String formatAccessibility(boolean isAccessible) {
		if (isAccessible) {
			return "Yes";
		}
		else {
			return "No";
		}
	}
	
	public String formatMaxRVLength(int maxRVLength) {
		if (maxRVLength < 1) {
			return "N/A";
		}
		else {
			return Integer.toString(maxRVLength);
		}
	}
	
	public String formatUtilities(boolean isUtilities) {
		if (isUtilities) {
			return "Yes";
		}
		else {
			return "N/A";
		}
	}
}
