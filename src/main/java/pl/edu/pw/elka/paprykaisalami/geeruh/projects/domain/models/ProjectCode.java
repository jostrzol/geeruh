package pl.edu.pw.elka.paprykaisalami.geeruh.projects.domain.models;


import lombok.NonNull;

public record ProjectCode(@NonNull String value) {

    @Override
    public String toString() {
        return value;
    }
}
