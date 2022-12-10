package pl.edu.pw.elka.paprykaisalami.geeruh.support;

import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.*;

public class ProjectDataset {

    public static final Project FIRST_PROJECT = Project.builder()
            .projectCode(new ProjectCode(FIRST_PROJECT_CODE))
            .name(FIRST_PROJECT_NAME)
            .description(FIRST_PROJECT_DESCRIPTION)
            .build();

    public static final Project SECOND_PROJECT = Project.builder()
            .projectCode(new ProjectCode(SECOND_PROJECT_CODE))
            .name(SECOND_PROJECT_NAME)
            .description(SECOND_PROJECT_DESCRIPTION)
            .build();

    public static final Project THIRD_PROJECT_NO_DESCRIPTION = Project.builder()
            .projectCode(new ProjectCode(THIRD_PROJECT_CODE))
            .name(THIRD_PROJECT_NAME)
            .build();
}
