package com.parkingmanagement.dao;

import com.parkingmanagement.entity.Plate;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface PlateDao {

    List<Plate> getList(Integer userId);

    Plate getById(Integer plateId);

    void updatePlate(Plate plate);

    Plate query(HashMap<String, Object> queryMap);

    void save(Plate plate);
}
