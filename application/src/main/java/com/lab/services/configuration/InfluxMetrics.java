package com.lab.services.configuration;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class InfluxMetrics {

    private static final Duration HISTOGRAM_EXPIRY = Duration.ofMinutes(10);
    private static final Duration STEP = Duration.ofSeconds(5);

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config()
                .meterFilter(new MeterFilter() {
                    @Override
                    public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                        return config.merge(DistributionStatisticConfig.builder()
                                .percentilesHistogram(true)
                                .percentiles(0.5, 0.75, 0.85, 0.95, 0.99)
                                .expiry(HISTOGRAM_EXPIRY)
                                .bufferLength((int) (HISTOGRAM_EXPIRY.toMillis() / STEP.toMillis()))
                                .build());
                    }
                });
    }
}
