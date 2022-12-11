package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.api;

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
import java.util.List;

import static pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode.STATUS_CODE_REGEX;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("statuses")
@Tag(name = "Status")
class StatusEndpoint {

    private final StatusFacade statusFacade;

    @GetMapping
    public List<StatusResponse> list() {
        return statusFacade.list();
    }

    @GetMapping("{statusCode}")
    public StatusResponse get(@PathVariable final String statusCode) {
        return statusFacade.get(statusCode);
    }

    @PutMapping("{statusCode}")
    public StatusResponse update(
            @PathVariable final String statusCode,
            @Valid @RequestBody final StatusRequest statusRequest
    ) {
        return statusFacade.update(statusCode, statusRequest);
    }

    @PostMapping("{statusCode}")
    public StatusResponse create(
            @PathVariable @Pattern(regexp = STATUS_CODE_REGEX) final String statusCode,
            @Valid @RequestBody final StatusRequest statusRequest
    ) {
        return statusFacade.create(statusCode, statusRequest);
    }
}
