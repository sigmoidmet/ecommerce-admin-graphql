package net.ecommerce.events.repository;

import net.ecommerce.events.model.types.Abandonment;
import net.ecommerce.events.model.types.AbandonmentType;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Repository
public class InMemoryAbandonmentRepository implements AbandonmentRepository {

    private static final List<Abandonment> abandonments = List.of(
            Abandonment.newBuilder()
                       .id("1")
                       .type(AbandonmentType.CART)
                       .createdAt(OffsetDateTime.of(2012, Month.APRIL.getValue(), 26, 2, 15, 36, 234, ZoneOffset.UTC))
                       .customerId("1")
                       .visitStartedAt(OffsetDateTime.of(2012, Month.APRIL.getValue(), 26, 1, 15, 36, 234, ZoneOffset.UTC))
                       .build(),
            Abandonment.newBuilder()
                       .id("2")
                       .type(AbandonmentType.CART)
                       .createdAt(OffsetDateTime.of(2015, Month.APRIL.getValue(), 26, 2, 15, 36, 234, ZoneOffset.UTC))
                       .customerId("1")
                       .visitStartedAt(OffsetDateTime.of(2014, Month.APRIL.getValue(), 26, 1, 15, 36, 234, ZoneOffset.UTC))
                       .build(),
            Abandonment.newBuilder()
                       .id("3")
                       .type(AbandonmentType.CART)
                       .createdAt(OffsetDateTime.of(2024, Month.APRIL.getValue(), 26, 2, 15, 36, 234, ZoneOffset.UTC))
                       .customerId("24")
                       .visitStartedAt(OffsetDateTime.of(2024, Month.APRIL.getValue(), 26, 1, 15, 36, 234, ZoneOffset.UTC))
                       .build()
    );

    @Override
    public List<Abandonment> getAbandonmentsCreatedAfter(OffsetDateTime dateTime) {
        return abandonments.stream()
                           .filter(abandonment -> abandonment.getCreatedAt().isAfter(dateTime))
                           .toList();
    }
}
