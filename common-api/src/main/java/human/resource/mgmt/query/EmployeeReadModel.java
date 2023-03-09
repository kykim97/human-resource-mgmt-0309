package human.resource.mgmt.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

@Entity
@Table(name = "Employee_table")
@Data
@Relation(collectionRelation = "employees")
public class EmployeeReadModel {

    @Id
    private String userId;

    private String name;

    private String email;
}
