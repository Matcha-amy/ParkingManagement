package com.parkingmanagement.service.impl;

import com.parkingmanagement.dao.PaymentDao;
import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.PaymentLog;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.entity.vo.OrderVO;
import com.parkingmanagement.entity.vo.PaymentVO;
import com.parkingmanagement.service.PaymentService;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PaymentDao paymentDao;

    @Override
    public List<PaymentVO> getList(String userName) {
        User user = userDao.getUserByUsername(userName);
        List<PaymentLog> paymentLogList = paymentDao.getList(user.getUserId());
        List<PaymentVO> paymentVOS = toVO(paymentLogList);
        return paymentVOS;
    }

    private  List<PaymentVO>  toVO(List<PaymentLog> paymentLogs){
        List<PaymentVO> paymentVOS = new ArrayList<>();
        for (PaymentLog paymentLog : paymentLogs) {
            PaymentVO  paymentVO= new PaymentVO();
            paymentVO.setPaymentMoney(paymentLog.getPaymentMoney());
            paymentVO.setPaymentTime(TimeUtils.timeToStr(paymentLog.getPaymentTime()));
            paymentVOS.add(paymentVO);
        }
        return paymentVOS;
    }
}
