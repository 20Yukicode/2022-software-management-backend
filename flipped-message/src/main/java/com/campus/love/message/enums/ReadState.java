package com.campus.love.message.enums;

public enum ReadState {

    NOT_READ(0,"未读"),

    IS_READ(1,"已读");

    final Integer number;

    final String description;

    ReadState(Integer number, String description) {
        this.number = number;
        this.description = description;
    }

    public Integer getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}
