package com.parkingmanagement.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *  List的 返回结果封装类
 */
@Data
@ToString
@Accessors(chain = true)
public class ResultList extends BaseResult {
    private static final long serialVersionUID = 7318584753305769507L;


    private List list;
}
