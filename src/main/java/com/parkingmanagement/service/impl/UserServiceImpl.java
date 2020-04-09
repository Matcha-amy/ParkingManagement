package com.parkingmanagement.service.impl;



import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.parkingmanagement.dao.UserDao;
import com.parkingmanagement.entity.system.User;
import com.parkingmanagement.service.UserService;
import com.parkingmanagement.utils.BaseResult;
import com.parkingmanagement.utils.MD5Utils;
import com.parkingmanagement.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User getUser(String username, String password) {
        return userDao.getUser(username,password);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao. getUserByUsername( username);
    }

    @Override
    public List<User> getList() {
        return userDao.getList();
    }

    @Override
    public List<User> query(HashMap<String, Object> queryMap) {
        return userDao.query(queryMap);
    }

    @Override
    public BaseResult register(User user) {
        //先去db中找到是否有相同的username
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",user.getUsername());
        List<User> users = userDao.query(queryMap);
        if ( users.isEmpty()) {
            String pas = MD5Utils.encodePassword(user.getPassword(), user.getCredentialsSalt());
            user.setPassword(pas);
            System.out.println(pas);
            userDao.save(user);
            return new BaseResult().setStatus(true).setMsg("注册成功");
        }
        return new BaseResult().setMsg("该用户已注册");
    }

    @Override
    public PageList getPageList(Integer pageNum, Integer pageSize,HashMap<String,Object> queryMap) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.query(queryMap);
        PageInfo<User> page = new PageInfo<User>(userList);
        PageList pageList = new PageList();
        pageList.setTotal(page.getTotal());
        pageList.setRows(page.getList());
        return pageList;
    }

    @Override
    public BaseResult updateUser(User user) {
        BaseResult result = new BaseResult();
        HashMap<String,Object> queryHash =new HashMap<>();
        queryHash.put("userId",user.getUserId());
        List<User> users = userDao.query(queryHash);
        if (users!=null && users.get(0).equals(user)){
            return result.setMsg("该用户无更改");
        }
        Integer updateStatus = userDao.update(user);
        if (updateStatus == 1){
            return result.setStatus(true).setMsg("修改成功");
        }

        return result.setMsg("修改失败");
    }
}
