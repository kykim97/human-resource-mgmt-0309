package human.resource.mgmt.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.*;

import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;
import human.resource.mgmt.query.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Data
@ToString
public class VacationAggregate {

    @AggregateIdentifier
    private String id;

    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;
    private Integer days;
    private String status;

    public VacationAggregate() {}

    @CommandHandler
    public VacationAggregate(RegisterVacationCommand command) {
        VacationRegisteredEvent event = new VacationRegisteredEvent();
        BeanUtils.copyProperties(command, event);

        //TODO: check key generation is properly done
        if (event.getId() == null) event.setId(createUUID());

        apply(event);

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        human.resource.mgmt.external.Calendar calendar = new human.resource.mgmt.external.Calendar();
        // mappings goes here
        Application.applicationContext
            .getBean(human.resource.mgmt.external.CalendarService.class)
            .addCalendar(calendar);
    }

    @CommandHandler
    public void handle(CancelCommand command) {
        VacationCancelledEvent event = new VacationCancelledEvent();
        BeanUtils.copyProperties(command, event);

        apply(event);
    }

    @CommandHandler
    public void handle(ApproveCommand command) {
        VacationApprovedEvent event = new VacationApprovedEvent();
        BeanUtils.copyProperties(command, event);

        apply(event);
    }

    @CommandHandler
    public void handle(ConfirmUsedCommand command) {
        VacationUsedEvent event = new VacationUsedEvent();
        BeanUtils.copyProperties(command, event);

        apply(event);
    }

    @CommandHandler
    public void handle(UpdateCommand command) {
        VacationRejectedEvent event = new VacationRejectedEvent();
        BeanUtils.copyProperties(command, event);

        apply(event);
    }

    //<<< Etc / ID Generation
    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    //>>> Etc / ID Generation

    //<<< EDA / Event Sourcing

    @EventSourcingHandler
    public void on(VacationRegisteredEvent event) {
        BeanUtils.copyProperties(event, this);
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(VacationCancelledEvent event) {
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(VacationApprovedEvent event) {
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(VacationRejectedEvent event) {
        //TODO: business logic here

    }

    @EventSourcingHandler
    public void on(VacationUsedEvent event) {
        //TODO: business logic here

    }
    //>>> EDA / Event Sourcing

}
