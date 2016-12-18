package lukedev.hunter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse summary text for various job details
 */
public class JobSummaryTextParser {

    private String salary;
    private String location;
    private String employer;
    private Long jobNumber;
    private String summary;

    public JobSummaryTextParser(String summary) {
        this.summary = summary;
    }

    public String getSalary() {

        // Compile a regular expression for matching the location string in the summary text
        // Pattern assumes location is followed by employer in the text after stripping out html tags
        Pattern pattern = Pattern.compile("([$]\\d+([,]?\\d+)*([.]\\d{2})?)( to ([$]\\d+([,]?\\d+)*([.]\\d{2})?))?.*(annual|hourly)", Pattern.MULTILINE);

        // Point the pattern at the summary text
        Matcher matcher = pattern.matcher(summary);

        // If a match is found, add the first group to the list
        if (matcher.find()) {
            return matcher.group(0).trim();
        }
        // Return an empty string
        return "N/A";

    }

    public String getLocation() {

        // Compile a regular expression for matching the location string in the summary text
        // Pattern assumes location is followed by employer in the text after stripping out html tags
        Pattern pattern = Pattern.compile("Location: (\\d*)Employer", Pattern.MULTILINE);

        // Point the pattern at the summary text
        Matcher matcher = pattern.matcher(summary);

        // If a match is found, add the first group to the list
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        // Return an empty string
        return "";


    }

    public String getEmployer() {
        Pattern pattern = Pattern.compile("Employer:\\s(.*)\\s*Salary", Pattern.MULTILINE);
        // Point the pattern at the summary text
        Matcher matcher = pattern.matcher(summary);

        // If a match is found, add the first group to the list
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        // Return an empty string
        return "";
    }

    public Long getJobNumber() {
        Pattern pattern = Pattern.compile("Job Number: (\\d*)", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(summary);
        List<Long> jobNumberList = new ArrayList<>();

        while (matcher.find()) {
            jobNumberList.add(Long.parseLong(matcher.group(1)));
        }

        return jobNumberList.get(0);
    }
}
