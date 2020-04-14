package com.parkingmanagement.utils;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class TaskBase {
    private String identifier;

    public TaskBase(String identifier){
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
