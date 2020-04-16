package com.parkingmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.parkingmanagement.dao.ParkingDao;
import com.parkingmanagement.dao.ParkingLogDao;
import com.parkingmanagement.dao.PlateDao;
import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.Plate;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.entity.vo.ParkingLogVO;
import com.parkingmanagement.service.ParkingLogService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ParkingLogServiceImpl implements ParkingLogService {

    @Autowired
    private ParkingLogDao parkingLogDao;
    @Autowired
    private ParkingDao parkingDao;
    @Autowired
    private PlateDao plateDao;
    @Autowired
    private UserDao userDao;
    @Override
    public List<ParkingLogVO> getList() {
        List<ParkingLog>  parkingLogList= parkingLogDao.getList(new HashMap<>());
        List<ParkingLogVO> parkingLogVOList = new ArrayList<>();
        for (ParkingLog parkingLog : parkingLogList) {
            ParkingLogVO parkingLogVO = parkingToVO(parkingLog);
            parkingLogVOList.add(parkingLogVO);
        }
        return parkingLogVOList;
    }

    @Override
    public BaseResult addParkingLog(ParkingLogVO parkingLogVO) {
        BaseResult baseResult  = new BaseResult();
        if (parkingLogVO == null || parkingLogVO.getPlateCode()==null || parkingLogVO.getParkingName()==null|| parkingLogVO.getParkingLogStartTime()==null){
            return baseResult.setMsg("信息过少，无法添加");
        }
        ParkingLog parkingLog = voToParking(parkingLogVO);
        parkingLogDao.save(parkingLog);
        return baseResult.setStatus(true).setMsg("添加成功");
    }

    @Override
    @Transactional
    public BaseResult updateParkingLog(ParkingLogVO parkingLogVO) {
        BaseResult result  = new BaseResult();
        ParkingLog parkingLog = voToParking(parkingLogVO);
        // 结束了 要计算钱了
        if (parkingLog.getParkingLogEndTime() !=null && parkingLog.getParkingLogPayment() != null){
            Double payment = parkingLog.getParkingLogPayment();
            User user = userDao.getUserById(parkingLog.getParkingLogUserId());
            user.setBalance(user.getBalance()-payment);
            userDao.update(user);
        }
        parkingLogDao.updateParking(parkingLog);
        return result.setStatus(true).setMsg("修改成功");
    }


    private ParkingLog voToParking(ParkingLogVO parkingLogVO){
        ParkingLog parkingLog = new ParkingLog();
        parkingLog.setParkingLogId(parkingLogVO.getParkingLogId());
        Parking parking = parkingDao.getByName(parkingLogVO.getParkingName());
        if (parking == null){
            throw new RuntimeException("停车场信息错误");
        }
        parkingLog.setParkingLogParkingId(parking.getParkingId());
        HashMap<String,Object> plateQuery = new HashMap<>();
        plateQuery.put("plateCode",parkingLogVO.getPlateCode());
        Plate plate = plateDao.query(plateQuery);
        if (parking == null){
            throw new RuntimeException("车牌信息错误");
        }
        parkingLog.setParkingLogPlateId(plate.getPlateId());
        parkingLog.setParkingLogUserId(plate.getUserId());
        Date startDate = new Date(parkingLogVO.getParkingLogStartTime());
        parkingLog.setParkingLogStartTime(startDate.getTime());
        if (parkingLogVO.getParkingLogEndTime() != null){
            // 结束了 要计算钱了
            Date endDate = new Date(parkingLogVO.getParkingLogStartTime());
            parkingLog.setParkingLogStartTime(endDate.getTime());
            Double time = TimeUtils.getDistanceTime(startDate.getTime(), endDate.getTime());
            parkingLog.setParkingLogPayment(parking.getParkingPrice()*time);
        }
        return parkingLog;
    }

    private ParkingLogVO parkingToVO(ParkingLog parkingLog){
        ParkingLogVO parkingLogVO = new ParkingLogVO();
        parkingLogVO.setParkingLogId(parkingLog.getParkingLogId());
        Parking parking = parkingDao.getById(parkingLog.getParkingLogParkingId());
        Plate plate = plateDao.getById(parkingLog.getParkingLogPlateId());
        User user = userDao.getUserById(plate.getUserId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime = simpleDateFormat.format(parkingLog.getParkingLogStartTime());
        String endTime = simpleDateFormat.format(parkingLog.getParkingLogEndTime());
        parkingLogVO.setUsername(user.getUsername());
        parkingLogVO.setPlateCode(plate.getPlateCode());
        parkingLogVO.setParkingName(parking.getParkingName());
        parkingLogVO.setParkingLogStartTime(startTime);
        parkingLogVO.setParkingLogEndTime(endTime);
        parkingLogVO.setParkingLogPayment(parkingLog.getParkingLogPayment());
        return parkingLogVO;

    }
}
