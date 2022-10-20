package com.smartshare.user_management.event;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
public class BaseEvent implements Serializable {
    private UUID eventId = UUID.randomUUID();
    private Instant createdAt;
}
