package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import org.json.JSONObject;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_DESCRIPTION;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_DESCRIPTION;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.THIRD_PROJECT_NAME;

public class ProjectDataset {

    public static final JSONObject FIRST_PROJECT = new JSONObject()
            .put("name", FIRST_PROJECT_NAME)
            .put("description", FIRST_PROJECT_DESCRIPTION);
    public static final String FIRST_PROJECT_STRING = FIRST_PROJECT.toString();

    public static final JSONObject SECOND_PROJECT = new JSONObject()
            .put("name", SECOND_PROJECT_NAME)
            .put("description", SECOND_PROJECT_DESCRIPTION);
    public static final String SECOND_PROJECT_STRING = SECOND_PROJECT.toString();

    public static final JSONObject THIRD_PROJECT_NO_DESCRIPTION = new JSONObject()
            .put("name", THIRD_PROJECT_NAME);
    public static final String THIRD_PROJECT_NO_DESCRIPTION_STRING = THIRD_PROJECT_NO_DESCRIPTION.toString();
}
