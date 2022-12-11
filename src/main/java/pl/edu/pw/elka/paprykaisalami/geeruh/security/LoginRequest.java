package pl.edu.pw.elka.paprykaisalami.geeruh.security;


import lombok.NonNull;

public record LoginRequest(@NonNull String username, @NonNull String password) {
}
