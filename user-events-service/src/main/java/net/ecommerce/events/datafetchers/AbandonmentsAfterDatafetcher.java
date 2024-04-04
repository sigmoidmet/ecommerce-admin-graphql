package net.ecommerce.events.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import net.ecommerce.events.model.DgsConstants;
import net.ecommerce.events.model.types.Abandonment;
import net.ecommerce.events.service.AbandonmentService;

import java.time.OffsetDateTime;
import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class AbandonmentsAfterDatafetcher {

    private final AbandonmentService abandonmentService;

    @DgsData(
            parentType = DgsConstants.QUERY_TYPE,
            field = DgsConstants.QUERY.AbandonmentsAfter
    )
    public List<Abandonment> getAbandonmentsAfter(DataFetchingEnvironment dataFetchingEnvironment,
                                                  @InputArgument OffsetDateTime createdAfterDate) {
        return abandonmentService.getAbandonmentsAfter(createdAfterDate);
    }
}
