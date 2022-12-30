package pl.edu.pw.elka.paprykaisalami.geeruh.issues.adapters.api;

import java.util.UUID;

import lombok.Data;

@Data
class IssueAssignUserRequest {

    UUID assigneeUserId;
}
