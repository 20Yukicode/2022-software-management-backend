package com.campus.love.common.core.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseEntity implements Serializable {

    protected Date createTime;

    protected Date updateTime;
}
