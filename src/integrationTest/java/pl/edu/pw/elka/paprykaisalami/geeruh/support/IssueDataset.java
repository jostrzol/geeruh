package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import org.json.JSONObject;

import java.util.UUID;

public class IssueDataset {

    public static final String FIRST_SUMMARY = "First issue";
    public static final String FIRST_TYPE = "TASK";
    public static final String FIRST_DESCRIPTION = "Important issue";
    public static final JSONObject FIRST_ISSUE = new JSONObject()
            .put("summary", FIRST_SUMMARY)
            .put("type", FIRST_TYPE)
            .put("description", FIRST_DESCRIPTION);
    public static final String FIRST_ISSUE_STRING = FIRST_ISSUE.toString();

    public static final String SECOND_SUMMARY = "Second issue";
    public static final String SECOND_TYPE = "BUG";
    public static final String SECOND_DESCRIPTION = "Even more important issue";
    public static final JSONObject SECOND_ISSUE = new JSONObject()
            .put("summary", SECOND_SUMMARY)
            .put("type", SECOND_TYPE)
            .put("description", SECOND_DESCRIPTION);
    public static final String SECOND_ISSUE_STRING = SECOND_ISSUE.toString();
}
