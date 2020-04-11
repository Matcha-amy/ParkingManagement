package com.parkingmanagement.entity.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;

@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class ListQuery {

    private Integer page; //多少页

    private Integer limit; //每页多少条

    private HashMap<String, Object> query ;

}
