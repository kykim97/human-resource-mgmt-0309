package human.resource.mgmt.query;

import human.resource.mgmt.aggregate.*;
import human.resource.mgmt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("employee")
public class EmployeeCQRSHandlerReusingAggregate {

    @Autowired
    private EmployeeReadModelRepository repository;

    //<<< Etc / RSocket
    @Autowired
    private QueryUpdateEmitter queryUpdateEmitter;

    //>>> Etc / RSocket

    @QueryHandler
    public List<EmployeeReadModel> handle(EmployeeQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    public Optional<EmployeeReadModel> handle(EmployeeSingleQuery query) {
        return repository.findById(query.getUserId());
    }

    @EventHandler
    public void whenEmployeeJoined_then_CREATE(EmployeeJoinedEvent event)
        throws Exception {
        EmployeeReadModel entity = new EmployeeReadModel();
        EmployeeAggregate aggregate = new EmployeeAggregate();
        aggregate.on(event);

        BeanUtils.copyProperties(aggregate, entity);

        repository.save(entity);

        //<<< Etc / RSocket
        queryUpdateEmitter.emit(EmployeeQuery.class, query -> true, entity);
        //>>> Etc / RSocket

    }

    @EventHandler
    public void whenEmployeeResigned_then_UPDATE(EmployeeResignedEvent event)
        throws Exception {
        repository
            .findById(event.getUserId())
            .ifPresent(entity -> {
                EmployeeAggregate aggregate = new EmployeeAggregate();

                BeanUtils.copyProperties(entity, aggregate);
                aggregate.on(event);
                BeanUtils.copyProperties(aggregate, entity);

                repository.save(entity);

                //<<< Etc / RSocket
                queryUpdateEmitter.emit(
                    EmployeeSingleQuery.class,
                    query -> query.getUserId().equals(event.getUserId()),
                    entity
                );
                //>>> Etc / RSocket

            });
    }
}
