package si.fri.rso.comments;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ServiceHealthIndicator implements HealthIndicator {
    private final String message_key = "comments-service";

    private boolean isSick = false;

    @Override
    public Health health() {
        if (isSick) {
            return Health.down().withDetail(message_key, "Not Available").build();
        }
        return Health.up().withDetail(message_key, "Available").build();
    }

    public void makeMeSick() {
        isSick = true;
    }
}
