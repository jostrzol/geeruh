package pl.edu.pw.elka.paprykaisalami.geeruh.users.adapters.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("users")
@Tag(name = "Users")
class UserEndpoint {

    private final UserFacade userFacade;

    @GetMapping
    public List<UserResponse> list() {
        return userFacade.list();
    }

    @GetMapping("{userId}")
    public UserResponse get(@PathVariable final UUID userId) {
        return userFacade.get(userId);
    }

    @PutMapping("{userId}")
    public UserResponse update(
            @PathVariable final UUID userId,
            @Valid @RequestBody final UserUpdateRequest request
    ) {
        return userFacade.update(userId, request);
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody final UserCreateRequest request) {
        return userFacade.create(request);
    }
}
