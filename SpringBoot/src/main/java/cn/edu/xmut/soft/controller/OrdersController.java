package cn.edu.xmut.soft.controller;

import cn.edu.xmut.soft.entity.*;
import cn.edu.xmut.soft.mapper.*;
import cn.edu.xmut.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.edu.xmut.springboot.utils.Result;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/soft/order")
public class OrdersController extends BaseController
{
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private CartMapper cartMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/page")
    public Result page(
            Integer pageNum,
            Integer pageSize,
            Integer status)
    {
        String token = request.getHeader("token");
        System.out.println("headerToken: " + token);
        String userId = JwtUtil.getUserId(token);
        Result result = new Result();
        Page<Orders> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (userId != null && !"".equals(userId))
        {
            wrapper.eq("user_id", userId);
        }
        if (status != null && status != 0)
        {
            wrapper.eq("status", status);
        }

        // 按照 create_time 降序排序
        wrapper.orderByDesc("create_time");

        Page<Orders> orderPage = ordersMapper.selectPage(page, wrapper);
        List<Orders> ordersList = orderPage.getRecords();

        for (Orders orders : ordersList)
        {
            String orderId = orders.getId();
            QueryWrapper<OrderDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("order_id", orderId);
            List<OrderDetail> orderDetailList = orderDetailMapper.selectList(wrapper2);

            for (OrderDetail orderDetail : orderDetailList)
            {
                String bookId = orderDetail.getBookId();
                Book book = bookMapper.selectById(bookId);
                orderDetail.setBook(book);
            }

            orders.setOrderDetail(orderDetailList);

            String addressId = orders.getAddressId();
            Address address = addressMapper.selectById(addressId);
            orders.setAddress(address);
        }
        result.setData(orderPage);
        return result;
    }
    @RequestMapping(method = RequestMethod.POST, value = "/pageAll")
    public Result pageAll(
            Integer pageNum,
            Integer pageSize,
            Integer status)
    {
        Result result = new Result();
        Page<Orders> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();

        if (status != null && status != 0)
        {
            wrapper.eq("status", status);
        }

        // 按照 create_time 降序排序
        wrapper.orderByDesc("create_time");

        Page<Orders> orderPage = ordersMapper.selectPage(page, wrapper);
        List<Orders> ordersList = orderPage.getRecords();

        for (Orders orders : ordersList)
        {
            String orderId = orders.getId();
            QueryWrapper<OrderDetail> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("order_id", orderId);
            List<OrderDetail> orderDetailList = orderDetailMapper.selectList(wrapper2);

            for (OrderDetail orderDetail : orderDetailList)
            {
                String bookId = orderDetail.getBookId();
                Book book = bookMapper.selectById(bookId);
                orderDetail.setBook(book);
            }

            orders.setOrderDetail(orderDetailList);

            String addressId = orders.getAddressId();
            Address address = addressMapper.selectById(addressId);
            orders.setAddress(address);
        }
        result.setData(orderPage);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public Result save(String addressId) {
        String token = request.getHeader("token");
        System.out.println("headerToken: " + token);
        String userId = JwtUtil.getUserId(token);
        Result result = new Result();

        // 根据用户ID和checked状态查询购物车
        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        cartWrapper.eq("user_id", userId).eq("checked", 1);
        List<Cart> cartList = cartMapper.selectList(cartWrapper);

        // 创建并发送OrderRequest
        OrderRequest orderRequest = new OrderRequest(userId, addressId, cartList);
        rocketMQTemplate.syncSend("orderTopic", orderRequest);

        return result;
    }

    private void sendOrderMessage(Orders orders, List<Cart> cartList)
    {
        // 设置订单状态和订单号
        orders.setOrderNumber(UUID.randomUUID().toString().replace("-", ""));

        // 将订单发送到名为"orderTopic"的消息主题
        rocketMQTemplate.convertAndSend("orderTopic", orders);

        // 将订单详情发送到名为"orderDetailTopic"的消息主题
        for (Cart cart : cartList)
        {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBookId(cart.getBookId());
            orderDetail.setQuantity(cart.getQuantity());
            rocketMQTemplate.convertAndSend("orderDetailTopic", orderDetail);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/updateStatus")
    public Result updateStatus(String orderId, Integer status)
    {
        Result result = new Result();

        Orders orders = ordersMapper.selectById(orderId);

        // 更新订单状态
        orders.setStatus(status);
        ordersMapper.updateById(orders);
        return result;
    }
}
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            Order order = new Order();
//            order.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<>();
//            orderUpdateWrapper.eq("id", id);
//
//            orderMapper.update(order, orderUpdateWrapper);
//        }
//        return result;
//    }
//}
//    @RequestMapping(method = RequestMethod.POST, value = "/save")
//    public Result save(String addressId) {
//        Result result = new Result();
//        String token = request.getHeader("token");
//        System.out.println("headerToken: " + token);
//        String userId = JwtUtil.getUserId(token);
//
//        // 根据用户ID和checked状态查询购物车
//        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
//        cartWrapper.eq("user_id", userId).eq("checked", 1);
//        List<Cart> cartList = cartMapper.selectList(cartWrapper);
//
//        // 创建订单对象
//        Orders orders = new Orders();
//        orders.setUserId(userId);
//        orders.setAddressId(addressId);
//        orders.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        orders.setOrderNumber(UUID.randomUUID().toString().replace("-", ""));
//        orders.setStatus(1);
////        orders.setOrderStatus("待支付");
//
//        // 遍历购物车中已选中的项目，计算订单总价
//        double totalPrice = 0.0;
//        for (Cart cart : cartList) {
//            // 获取图书价格
//            Book book = bookMapper.selectById(cart.getBookId());
//            totalPrice += book.getPrice() * cart.getQuantity();
//        }
//
//        orders.setPrice(totalPrice);
//        ordersMapper.insert(orders);
//
//        // 获取自动生成的订单ID
//        String orderId = orders.getId();
//
//        for (Cart cart : cartList) {
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setBookId(cart.getBookId());
//            orderDetail.setQuantity(cart.getQuantity());
//            orderDetail.setOrderId(orderId); // 使用获取到的orderId
//            orderDetailMapper.insert(orderDetail);
//        }
//
//        // 从购物车中删除已下单的商品
//        cartMapper.delete(cartWrapper);
//
//        result.setData(orders);
//        return result;
//    }