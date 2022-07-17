package com.relay42.sensor.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.relay42.consumer.domain.EvenMessage} entity.
 */
public class EvenMessageDTO implements Serializable {

    private String uid;

    private Long id;

    private BigDecimal value;

    private Instant timestampDate;

    private String type;

    private String name;

    private Long clusterId;

    private Long offset;

    private Long partitionId;

    private String topic;

    private String groupId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Instant getTimestampDate() {
        return timestampDate;
    }

    public void setTimestampDate(Instant timestampDate) {
        this.timestampDate = timestampDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getPartitionId() {
        return partitionId;
    }

    public void setPartitionId(Long partitionId) {
        this.partitionId = partitionId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvenMessageDTO)) {
            return false;
        }

        EvenMessageDTO evenMessageDTO = (EvenMessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evenMessageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvenMessageDTO{" +
            "uid='" + getUid() + "'" +
            ", id=" + getId() +
            ", value=" + getValue() +
            ", timestampDate='" + getTimestampDate() + "'" +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", clusterId=" + getClusterId() +
            ", offset=" + getOffset() +
            ", partitionId=" + getPartitionId() +
            ", topic='" + getTopic() + "'" +
            ", groupId='" + getGroupId() + "'" +
            "}";
    }
}
