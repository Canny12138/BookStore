package cn.edu.xmut.soft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.UserRoleMapper;
import cn.edu.xmut.soft.entity.UserRole;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/user-role")
public class UserRoleController extends BaseController {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @RequestMapping(method= RequestMethod.POST,value="/getRoleByUserId")
    public Result getRoleByUserId(
            String id) {
        Result result = new Result();
        UserRole parms = new UserRole();
        QueryWrapper<UserRole> warpper = new QueryWrapper<>(parms);
        warpper.eq("user_id",id);

        result.setData(userRoleMapper.selectList( warpper));
        return result;
    }

    @RequestMapping(method= RequestMethod.POST,value="/updateRoleByUserId")
    public Result updateRoleByUserId(String id, String ids) {
        Result result = new Result();
        //先晴空数据库记录
        QueryWrapper<UserRole> warpper = new QueryWrapper<>(new UserRole());
        warpper.eq("user_id",id);

        userRoleMapper.delete(warpper);

        //插入新记录
        for (String roleId : ids.split(",")) {
            UserRole ur = new UserRole();
            ur.setRoleId(roleId);
            ur.setUserId(id);

            userRoleMapper.insert(ur);
        }
        return result;
    }

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize) {
        Result result = new Result();
        Page<UserRole> page = new Page<UserRole>(pageNum, pageSize);
        UserRole parms = new UserRole();
        QueryWrapper<UserRole> warpper = new QueryWrapper<UserRole>(parms);

        result.setData(userRoleMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody UserRole entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            userRoleMapper.insert(entity);
        } else {
            userRoleMapper.updateById(entity);
        }
        return result;
    }
    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete( String ids) {
        Result result = new Result();
        List<String> deleteIds = new ArrayList<String>();
        for (String id : ids.split(",")) {
            deleteIds.add(id);
        }
        userRoleMapper.deleteBatchIds(deleteIds);
        return result;
    }

}
