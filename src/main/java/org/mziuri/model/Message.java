package org.mziuri.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(String content, String timestamp) {
    public Message(
            @JsonProperty("content") String content,
            @JsonProperty("timestamp") String timestamp
    ) {
        this.content = content;
        this.timestamp = timestamp;
    }
}