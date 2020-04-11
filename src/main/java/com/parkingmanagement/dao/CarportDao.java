package com.parkingmanagement.dao;

import com.parkingmanagement.entity.Carport;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface CarportDao {

    List<Carport> query(HashMap<String, Object> query);

    void save(Carport carport);

    Carport getById(Integer carportId);

    void updateCarport(Carport carport);

}
