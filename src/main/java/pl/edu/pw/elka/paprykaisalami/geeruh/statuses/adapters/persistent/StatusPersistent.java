package pl.edu.pw.elka.paprykaisalami.geeruh.statuses.adapters.persistent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.Status;
import pl.edu.pw.elka.paprykaisalami.geeruh.statuses.domain.models.StatusCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "Statuses")
public class StatusPersistent {

    @Id
    @Column(name = "code", updatable = false, nullable = false)
    private String code;

    private String name;

    @Column(unique = true)
    private Integer orderNumber;

    public Status toStatus() {
        return Status.builder()
                .statusCode(new StatusCode(code))
                .name(name)
                .orderNumber(orderNumber)
                .build();
    }

    public static StatusPersistent of(Status status) {
        return new StatusPersistent(
                status.getStatusCode().value(),
                status.getName(),
                status.getOrderNumber()
        );
    }
}
