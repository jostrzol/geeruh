package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
public class Project {

    @NonNull
    ProjectCode projectCode;

    @NonNull
    @Setter
    String name;

    @NonNull
    @Setter
    String description;
}
