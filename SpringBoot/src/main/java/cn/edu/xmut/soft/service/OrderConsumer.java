package cn.edu.xmut.soft.service;

import cn.edu.xmut.soft.entity.*;
import cn.edu.xmut.soft.mapper.BookMapper;
import cn.edu.xmut.soft.mapper.CartMapper;
import cn.edu.xmut.soft.mapper.OrderDetailMapper;
import cn.edu.xmut.soft.mapper.OrdersMapper;
import cn.edu.xmut.springboot.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RocketMQMessageListener(topic = "orderTopic", consumerGroup = "orderConsumerGroup")
public class OrderConsumer implements RocketMQListener<OrderRequest> {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void onMessage(OrderRequest orderRequest) {
        String userId = orderRequest.getUserId();
        String addressId = orderRequest.getAddressId();
        List<Cart> cartList = orderRequest.getCartList();

        // 创建订单对象
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setAddressId(addressId);
        orders.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        orders.setOrderNumber(UUID.randomUUID().toString().replace("-", ""));
        orders.setStatus(1);

        // 遍历购物车中已选中的项目，计算订单总价
        double totalPrice = 0.0;
        for (Cart cart : cartList) {
            // 获取图书价格
            Book book = bookMapper.selectById(cart.getBookId());
            totalPrice += book.getPrice() * cart.getQuantity();

            // 减少库存量
            book.setStock(book.getStock() - cart.getQuantity());
            // 增加销量
            book.setSales(book.getSales() + cart.getQuantity());
            bookMapper.updateById(book);
        }

        orders.setPrice(totalPrice);
        ordersMapper.insert(orders);

        // 获取自动生成的订单ID
        String orderId = orders.getId();

        for (Cart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBookId(cart.getBookId());
            orderDetail.setQuantity(cart.getQuantity());
            orderDetail.setOrderId(orderId);
            orderDetailMapper.insert(orderDetail);
        }

        // 从购物车中删除已下单的商品
        QueryWrapper<Cart> cartWrapper = new QueryWrapper<>();
        cartWrapper.eq("user_id", userId).eq("checked", 1);
        cartMapper.delete(cartWrapper);
    }
}