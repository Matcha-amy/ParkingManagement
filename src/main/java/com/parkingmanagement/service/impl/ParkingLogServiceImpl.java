package com.parkingmanagement.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.parkingmanagement.dao.*;
import com.parkingmanagement.entity.Carport;
import com.parkingmanagement.entity.Parking;
import com.parkingmanagement.entity.ParkingLog;
import com.parkingmanagement.entity.Plate;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.entity.vo.ListQuery;
import com.parkingmanagement.entity.vo.ParkingLogVO;
import com.parkingmanagement.service.ParkingLogService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.Constant;
import com.parkingmanagement.utils.MathUtils;
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
    @Autowired
    private CarportDao carportDao;

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
        //同时更改车位
        Carport updateCarport = new Carport();
        updateCarport.setCarportId(parkingLog.getParkingLogCarportId());
        updateCarport.setCarportStatus(Constant.CARPORT_STATUS_USEING);
        carportDao.updateCarport(updateCarport);
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
            Double stopCarTime = TimeUtils.getStopCarTime(parkingLog.getParkingLogStartTime(), parkingLog.getParkingLogEndTime());
            user.setBalance(user.getBalance()-MathUtils.doubleMul(payment,stopCarTime));
            userDao.update(user);
        }
        parkingLogDao.updateParking(parkingLog);
        //同时更改车位
        Carport updateCarport = new Carport();
        updateCarport.setCarportId(parkingLog.getParkingLogCarportId());
        updateCarport.setCarportStatus(Constant.CARPORT_STATUS_FREE);
        carportDao.updateCarport(updateCarport);
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
        List<Plate> plateList = plateDao.query(plateQuery);
        if (parking == null){
            throw new RuntimeException("车牌信息错误");
        }
        //车位信息
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("parkingId",parking.getParkingId());
        queryMap.put("carportNum",parkingLogVO.getCarportNum());
        List<Carport> query = carportDao.query(queryMap);
        parkingLog.setParkingLogCarportId(query.get(0).getCarportId());
        parkingLog.setParkingLogPlateId(plateList.get(0).getPlateId());
        parkingLog.setParkingLogUserId(plateList.get(0).getUserId());
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
        Carport carport = carportDao.getById(parkingLog.getParkingLogCarportId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = simpleDateFormat.format(parkingLog.getParkingLogStartTime());
        if(parkingLog.getParkingLogEndTime() !=0){
            String endTime = simpleDateFormat.format(parkingLog.getParkingLogEndTime());
            parkingLogVO.setParkingLogEndTime(endTime);
        }else {
            parkingLogVO.setParkingLogEndTime("-");

        }
        parkingLogVO.setUsername(user.getUsername());
        parkingLogVO.setPlateCode(plate.getPlateCode());
        parkingLogVO.setParkingName(parking.getParkingName());
        parkingLogVO.setParkingLogStartTime(startTime);
        parkingLogVO.setParkingLogPayment(parkingLog.getParkingLogPayment());
        parkingLogVO.setCarportNum(carport.getCarportNum());
        return parkingLogVO;

    }
    @Override
    public List<ParkingLogVO> getList(String userName) {
        User user = userDao.getUserByUsername(userName);
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("parkingLogUserId",user.getUserId());
        List<ParkingLog>  parkingLogList= parkingLogDao.getList(queryMap);
        List<ParkingLogVO> parkingLogVOList = new ArrayList<>();
        for (ParkingLog parkingLog : parkingLogList) {
            ParkingLogVO parkingLogVO = parkingToVO(parkingLog);
            parkingLogVOList.add(parkingLogVO);
        }
        return parkingLogVOList;
    }
}
