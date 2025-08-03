package org.contoso.cotacoes.producer.adapter.input;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.contoso.cotacoes.producer.port.CotacoesProducerUseCase;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Slf4j
@Component
public class DynamicScheduler {

    private final ThreadPoolTaskScheduler taskScheduler;
    private final Random random = new Random();
    private final static int minIntervalMilliseconds = 300; // 0.3 seg
    private final static int maxIntervalMilliseconds = 1000; // 1.0 seg

    private final CotacoesProducerUseCase cotacoesProducerUseCase;

    public DynamicScheduler(CotacoesProducerUseCase cotacoesProducerUseCase) {
        this.cotacoesProducerUseCase = cotacoesProducerUseCase;

        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
    }

    @PostConstruct
    public void startScheduling() {
        scheduleNextExecution();
    }

    private void scheduleNextExecution() {
        int nextInterval = minIntervalMilliseconds + random.nextInt(maxIntervalMilliseconds - minIntervalMilliseconds + 1);
        Instant nextTime = Instant.now().plusMillis(nextInterval);
        log.info("Next execution in {} milliseconds", nextInterval);

        taskScheduler.schedule(
                this::executeAndReschedule,
                nextTime
        );
    }

    private void executeAndReschedule() {
        try {
            taskLogic();

        } finally {
            scheduleNextExecution();
        }
    }

    private void taskLogic() {
        cotacoesProducerUseCase.gerarCotacao();
    }
}