package com.jvportfolio.service;

import com.jvportfolio.model.VisitorCount;
import com.jvportfolio.repository.VisitorCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitorService {

    private final VisitorCountRepository visitorCountRepository;

    @Transactional
    public long incrementAndGetCount() {
        List<VisitorCount> all = visitorCountRepository.findAll();

        VisitorCount vc;
        if (all.isEmpty()) {
            vc = new VisitorCount();
            vc.setTotalCount(1L);
        } else {
            vc = all.get(0);
            vc.setTotalCount(vc.getTotalCount() + 1);
        }

        visitorCountRepository.save(vc);
        log.info("Visitor count updated to: {}", vc.getTotalCount());
        return vc.getTotalCount();
    }

    public long getCount() {
        List<VisitorCount> all = visitorCountRepository.findAll();
        return all.isEmpty() ? 0L : all.get(0).getTotalCount();
    }
}
