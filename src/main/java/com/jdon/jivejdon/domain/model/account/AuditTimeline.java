package com.jdon.jivejdon.domain.model.account;

import java.util.Date;
import java.util.Objects;

import com.jdon.jivejdon.util.Constants;

public class AuditTimeline {
    private final String creationDate;
    private final String modifiedDate;

    public AuditTimeline(String creationDate, String modifiedDate) {
        this.creationDate = creationDate != null ? creationDate : "";
        this.modifiedDate = modifiedDate != null ? modifiedDate : "";
    }

    public String getCreationDate() { return creationDate; }
    public String getModifiedDate() { return modifiedDate; }

    // 把原本写在 Account 里的解析逻辑内聚到值对象中
    public String getShortCreationDate() {
        if (creationDate.length() != 0) {
            try {
                return creationDate.substring(0, 11);
            } catch (Exception ignored) {}
        }
        return "";
    }

    public Date getCreationDateTime() {
        return Constants.parseDateTime(creationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditTimeline timeline = (AuditTimeline) o;
        return Objects.equals(creationDate, timeline.creationDate) && Objects.equals(modifiedDate, timeline.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, modifiedDate);
    }
}
