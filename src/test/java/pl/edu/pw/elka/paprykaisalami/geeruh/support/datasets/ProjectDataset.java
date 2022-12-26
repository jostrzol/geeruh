package pl.edu.pw.elka.paprykaisalami.geeruh.support.datasets;

import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.Project;
import pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models.ProjectCode;

import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_DESCRIPTION;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.FIRST_PROJECT_NAME;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_CODE;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_DESCRIPTION;
import static pl.edu.pw.elka.paprykaisalami.geeruh.support.ProjectAttributeDataset.SECOND_PROJECT_NAME;

public class ProjectDataset {

    public static Project firstProject() {
        return Project.builder()
                .projectCode(new ProjectCode(FIRST_PROJECT_CODE))
                .name(FIRST_PROJECT_NAME)
                .description(FIRST_PROJECT_DESCRIPTION)
                .build();
    }

    public static Project secondProject() {
        return Project.builder()
                .projectCode(new ProjectCode(SECOND_PROJECT_CODE))
                .name(SECOND_PROJECT_NAME)
                .description(SECOND_PROJECT_DESCRIPTION)
                .build();
    }
}
