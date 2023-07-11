//package cn.edu.xmut.soft.controller;
//
//import cn.edu.xmut.common.BaseController;
//import cn.edu.xmut.soft.entity.Order;
//import cn.edu.xmut.soft.entity.OrderDetail;
//import cn.edu.xmut.soft.mapper.OrderDetailMapper;
//import cn.edu.xmut.soft.mapper.OrderMapper;
//import cn.edu.xmut.springboot.utils.Result;
//import cn.edu.xmut.utils.JwtUtil;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * <p>
// *  前端控制器 chrimer
// * </p>
// *
// * @author chrimer
// * @since 2023-05-18
// */
//@RestController
//@RequestMapping("/soft/order")
//public class OrderControllerback extends BaseController {
//    @Autowired
//    private OrderMapper orderMapper;
//    @Autowired
//    private OrderDetailMapper orderDetailMapper;
//    @Autowired
//    private HttpServletRequest request;
//
//    @RequestMapping(method= RequestMethod.POST,value="/page")
//    public Result page(
//        Integer pageNum,
//        Integer pageSize) {
//        String token = request.getHeader("token");
//        System.out.println("headerToken: "+token);
//        String userId = JwtUtil.getUserId(token);
//        Result result = new Result();
//        Page<Order> page = new Page<>(pageNum, pageSize);
//        QueryWrapper<Order> wrapper = new QueryWrapper<>();
//        if (userId != null && !"".equals(userId)) {
//            wrapper.eq("user_id", userId);
//        }
//        Page<Order> orderPage = orderMapper.selectPage(page, wrapper);
//        List<Order> orderList = orderPage.getRecords();
//
//        for (Order order : orderList) {
//            String orderId = order.getId();
//            QueryWrapper<OrderDetail> wrapper2 = new QueryWrapper<>();
//            wrapper2.eq("order_id", orderId);
//            List<OrderDetail> orderDetail = orderDetailMapper.selectList(wrapper2);
//            order.setOrderDetail(orderDetail);
//        }
//
//        result.setData(orderPage);
//        return result;
//    }
//    @RequestMapping(method= RequestMethod.POST,value="/save")
//    public Result save(@RequestBody Order entity) {
//        Result result = new Result();
//        if (entity.getId() == null) {
//            orderMapper.insert(entity);
//        } else {
//            orderMapper.updateById(entity);
//        }
//        return result;
//    }
//    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
//    public Result delete( String ids) {
//        Result result = new Result();
//        List<String> deleteIds = new ArrayList<String>();
//        for (String id : ids.split(",")) {
//            deleteIds.add(id);
//        }
//        orderMapper.deleteBatchIds(deleteIds);
//        return result;
//    }
////    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
////    public Result updateUseful( String ids,Integer useful) {
////        Result result = new Result();
////
////        for (String id : ids.split(",")) {
////
////            //修改值
////            Order order = new Order();
////            order.setUseful(useful);
////
////            //修改条件
////            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
////            orderUpdateWrapper.eq("id", id);
////
////            orderMapper.update(order, orderUpdateWrapper);
////        }
////        return result;
////    }
//}
