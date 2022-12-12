package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import org.json.JSONObject;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.IssueAttributeDataset.*;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.StatusAttributeDataset.FIRST_STATUS_CODE;

public class IssueDataset {

    public static final JSONObject FIRST_ISSUE = new JSONObject()
            .put("summary", FIRST_ISSUE_SUMMARY)
            .put("type", FIRST_ISSUE_TYPE)
            .put("statusCode", FIRST_STATUS_CODE)
            .put("description", FIRST_ISSUE_DESCRIPTION);
    public static final String FIRST_ISSUE_STRING = FIRST_ISSUE.toString();

    public static final JSONObject SECOND_ISSUE = new JSONObject()
            .put("summary", SECOND_ISSUE_SUMMARY)
            .put("type", SECOND_ISSUE_TYPE)
            .put("statusCode", FIRST_STATUS_CODE)
            .put("description", SECOND_ISSUE_DESCRIPTION);
    public static final String SECOND_ISSUE_STRING = SECOND_ISSUE.toString();

    public static final JSONObject THIRD_ISSUE_NO_DESCRIPTION = new JSONObject()
            .put("summary", THIRD_ISSUE_SUMMARY)
            .put("statusCode", FIRST_STATUS_CODE)
            .put("type", THIRD_ISSUE_TYPE);
    public static final String THIRD_ISSUE_NO_DESCRIPTION_STRING = THIRD_ISSUE_NO_DESCRIPTION.toString();
}
