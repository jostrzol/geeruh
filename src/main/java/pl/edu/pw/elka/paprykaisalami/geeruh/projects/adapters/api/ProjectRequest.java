package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

@Builder(access = AccessLevel.PRIVATE)
@Value
public class ProjectRequest {

    String name;

    String description;
}
