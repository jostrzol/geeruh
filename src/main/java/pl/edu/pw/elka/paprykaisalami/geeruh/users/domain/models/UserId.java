package pl.edu.pw.elka.paprykaisalami.geeruh.users.domain.models;


import java.util.UUID;

import static java.util.UUID.randomUUID;

public record UserId(UUID value) {

    @Override
    public String toString() {
        return value.toString();
    }

    public static UserId randomUserId() {
        return new UserId(randomUUID());
    }
}
