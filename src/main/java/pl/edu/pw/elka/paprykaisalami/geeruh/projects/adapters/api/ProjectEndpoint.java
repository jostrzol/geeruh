package pl.edu.pw.elka.paprykaisalami.geeruh.projects.adapters.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("projects")
@Tag(name = "Projects")
class ProjectEndpoint {

    private final ProjectFacade projectFacade;

    @GetMapping
    public List<ProjectResponse> list() {
        return projectFacade.list();
    }

    @GetMapping("{projectCode}")
    public ProjectResponse get(@PathVariable final String projectCode) {
        return projectFacade.get(projectCode);
    }

    @PutMapping("{projectCode}")
    public ProjectResponse update(
            @PathVariable final String projectCode,
            @Valid @RequestBody final ProjectRequest projectRequest
    ) {
        return projectFacade.update(projectCode, projectRequest);
    }

    @PostMapping("{projectCode}")
    public ProjectResponse create(
            @PathVariable @Size(min = 2, max = 5) @Pattern(regexp = "^[A-Z]+$") final String projectCode,
            @Valid @RequestBody final ProjectRequest projectRequest
    ) {
        return projectFacade.create(projectCode, projectRequest);
    }
}
