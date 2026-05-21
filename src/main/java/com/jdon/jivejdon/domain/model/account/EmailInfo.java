package com.jdon.jivejdon.domain.model.account;

import java.util.Objects;

public class EmailInfo {
    private final String address;
    private final boolean visible;
    private final boolean validated;

    public EmailInfo(String address, boolean visible, boolean validated) {
        // 可以在这里加邮箱格式校验
        this.address = address != null ? address : "";
        this.visible = visible;
        this.validated = validated;
    }

    public String getAddress() {
        return address;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isValidated() {
        return validated;
    }

    // 变更有对应的新值生成（因为不可变）
    public EmailInfo withAddress(String newAddress) {
        return new EmailInfo(newAddress, this.visible, this.validated);
    }

    public EmailInfo withVisible(boolean visible) {
        return new EmailInfo(this.address, visible, this.validated);
    }

    public EmailInfo withValidated(boolean validated) {
        return new EmailInfo(this.address, this.visible, validated);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmailInfo emailInfo = (EmailInfo) o;
        return visible == emailInfo.visible && validated == emailInfo.validated
                && Objects.equals(address, emailInfo.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, visible, validated);
    }
}
