package com.parkingmanagement.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class PageList<T> {

    private List<T> rows = new ArrayList<T>();

    private Long total;//总数量

    private Integer size; //当前页数

    private Integer pageNum;//当前页码

    private Integer pageSize; //每页显示数

    private Integer pages;//总页数

}
