package net.ecommerce.events.repository;

import net.ecommerce.events.model.types.Abandonment;

import java.time.OffsetDateTime;
import java.util.List;

public interface AbandonmentRepository {

    List<Abandonment> getAbandonmentsCreatedAfter(OffsetDateTime dateTime);
}
