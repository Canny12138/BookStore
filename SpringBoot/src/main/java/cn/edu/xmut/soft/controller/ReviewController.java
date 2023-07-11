package cn.edu.xmut.soft.controller;

import cn.edu.xmut.soft.entity.*;
import cn.edu.xmut.soft.mapper.OrderDetailMapper;
import cn.edu.xmut.soft.mapper.ReviewBookMapper;
import cn.edu.xmut.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.ReviewMapper;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/soft/review")
public class ReviewController extends BaseController {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private ReviewBookMapper reviewBookMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String orderId) {
        String token = request.getHeader("token");
        System.out.println("headerToken: " + token);
        String userId = JwtUtil.getUserId(token);
        Result result = new Result();

        Page<Review> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Review> wrapper = new QueryWrapper<>();
        if (userId != null && !"".equals(userId)) {
            wrapper.eq("user_id", userId);
        }
        if (orderId != null)
        {
            wrapper.eq("order_id", orderId);
        }

        result.setData(reviewMapper.selectPage(page, wrapper));
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public Result save(String orderId, String content, Integer rating) {
        String token = request.getHeader("token");
        System.out.println("headerToken: " + token);
        String userId = JwtUtil.getUserId(token);

        // 创建新的Review对象
        Review review = new Review();
        review.setUserId(userId);
        review.setOrderId(orderId);
        review.setContent(content);
        review.setRating(rating);
        review.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // 保存Review对象
        reviewMapper.insert(review);

        // 获取订单中所有的bookId
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(wrapper);

        // 遍历所有的bookId，并为每个bookId创建并保存一个ReviewBook对象
        for (OrderDetail orderDetail : orderDetails) {
            ReviewBook reviewBook = new ReviewBook();
            reviewBook.setReviewId(review.getId());
            reviewBook.setBookId(orderDetail.getBookId());
            reviewBookMapper.insert(reviewBook);
        }

        Result result = new Result();
        result.setData(review);
        return result;
    }

    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete( String ids) {
        Result result = new Result();
        List<String> deleteIds = new ArrayList<String>();
        for (String id : ids.split(",")) {
            deleteIds.add(id);
        }
        reviewMapper.deleteBatchIds(deleteIds);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            Review review = new Review();
//            review.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<Review> reviewUpdateWrapper = new UpdateWrapper<>();
//            reviewUpdateWrapper.eq("id", id);
//
//            reviewMapper.update(review, reviewUpdateWrapper);
//        }
//        return result;
//    }
}
