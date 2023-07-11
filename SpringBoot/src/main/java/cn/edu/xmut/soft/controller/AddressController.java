package cn.edu.xmut.soft.controller;

import cn.edu.xmut.soft.entity.Cart;
import cn.edu.xmut.utils.JwtUtil;
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
import cn.edu.xmut.soft.mapper.AddressMapper;
import cn.edu.xmut.soft.entity.Address;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/soft/address")
public class AddressController extends BaseController {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);
        Result result = new Result();

        Page<Address> page = new Page<Address>(pageNum, pageSize);
        Address parms = new Address();
        QueryWrapper<Address> wrapper = new QueryWrapper<Address>(parms);
        wrapper.eq("user_id", userId);

        result.setData(addressMapper.selectPage(page, wrapper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(
            String id,
            String receiverName,
            String receiverPhone,
            String address,
            String detailAddress) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);
        Result result = new Result();

        Address entity = new Address();
        entity.setUserId(userId);
        entity.setReceiverName(receiverName);
        entity.setReceiverPhone(receiverPhone);
        entity.setAddress(address);
        entity.setDetailAddress(detailAddress);

        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Address existingAddress = addressMapper.selectOne(queryWrapper);

        if (existingAddress == null) {
            addressMapper.insert(entity);
        } else {
            entity.setId(id);
            addressMapper.updateById(entity);
        }
        return result;
    }

    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete(String id) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);
        Result result = new Result();

        Address parms = new Address();
        QueryWrapper<Address> warpper = new QueryWrapper<Address>(parms);
        warpper.eq("user_id",userId);
        warpper.eq("id",id);
        addressMapper.delete(warpper);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            Address address = new Address();
//            address.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<Address> addressUpdateWrapper = new UpdateWrapper<>();
//            addressUpdateWrapper.eq("id", id);
//
//            addressMapper.update(address, addressUpdateWrapper);
//        }
//        return result;
//    }
}
