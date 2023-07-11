package cn.edu.xmut.soft.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.OrderDetailMapper;
import cn.edu.xmut.soft.entity.OrderDetail;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/order-detail")
public class OrderDetailController extends BaseController {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize) {
        Result result = new Result();
        Page<OrderDetail> page = new Page<OrderDetail>(pageNum, pageSize);
        OrderDetail parms = new OrderDetail();
        QueryWrapper<OrderDetail> warpper = new QueryWrapper<OrderDetail>(parms);

        result.setData(orderDetailMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody OrderDetail entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            orderDetailMapper.insert(entity);
        } else {
            orderDetailMapper.updateById(entity);
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
        orderDetailMapper.deleteBatchIds(deleteIds);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<OrderDetail> orderDetailUpdateWrapper = new UpdateWrapper<>();
//            orderDetailUpdateWrapper.eq("id", id);
//
//            orderDetailMapper.update(orderDetail, orderDetailUpdateWrapper);
//        }
//        return result;
//    }
}
