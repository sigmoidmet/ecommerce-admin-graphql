package net.ecommerce.events.service;

import lombok.RequiredArgsConstructor;
import net.ecommerce.events.model.types.Abandonment;
import net.ecommerce.events.repository.AbandonmentRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbandonmentService {

    private final AbandonmentRepository abandonmentRepository;

    public List<Abandonment> getAbandonmentsAfter(OffsetDateTime afterDateTime) {
        return abandonmentRepository.getAbandonmentsCreatedAfter(afterDateTime);
    }
}
