package cn.edu.xmut.soft.controller;

import cn.edu.xmut.soft.entity.Book;
import cn.edu.xmut.soft.mapper.BookMapper;
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
import cn.edu.xmut.soft.mapper.CartMapper;
import cn.edu.xmut.soft.entity.Cart;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/soft/cart")
public class CartController extends BaseController {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private BookMapper bookMapper; // 图书Mapper接口
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.POST, value = "/page")
    public Result page(
            Integer pageNum,
            Integer pageSize,
            Integer checked) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);

        Result result = new Result();
        Page<Cart> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();

        if (userId != null && !"".equals(userId)) {
            wrapper.eq("user_id", userId);
        }
        if (checked != null && !"".equals(checked)) {
            wrapper.eq("checked", checked);
        }

        Page<Cart> cartPage = cartMapper.selectPage(page, wrapper);
        List<Cart> cartList = cartPage.getRecords();

        for (Cart cart : cartList) {
            String bookId = cart.getBookId();
            Book book = bookMapper.selectById(bookId);
            cart.setBook(book);
        }

        result.setData(cartPage);
        return result;
    }

    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(String bookId, Integer quantity, Integer checked) {
        String token = request.getHeader("token");
//        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);
//        System.out.println("checked: " + checked);
        Result result = new Result();

        // 根据图书 ID 和用户 ID 查询购物车记录
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId)
                .eq("user_id", userId);
        Cart existingCart = cartMapper.selectOne(queryWrapper);

        if (existingCart != null) {
            // 购物车记录已存在，执行更新操作
            existingCart.setQuantity(quantity); // 设置其他字段的值
            existingCart.setChecked(checked);
            cartMapper.updateById(existingCart);
        } else {
            // 购物车记录不存在，执行插入操作
            Cart newCart = new Cart();
            newCart.setBookId(bookId);
            newCart.setUserId(userId);
            newCart.setQuantity(quantity);
            newCart.setChecked(0);
            // 设置其他字段的值
            cartMapper.insert(newCart);
        }
        return result;
    }

    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete(String bookId) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);

        Result result = new Result();
        Cart parms = new Cart();
        QueryWrapper<Cart> warpper = new QueryWrapper<Cart>(parms);
        warpper.eq("user_id",userId);
        if(bookId != null){
            warpper.eq("book_id",bookId);
        }
        cartMapper.delete(warpper);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            Cart cart = new Cart();
//            cart.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<Cart> cartUpdateWrapper = new UpdateWrapper<>();
//            cartUpdateWrapper.eq("id", id);
//
//            cartMapper.update(cart, cartUpdateWrapper);
//        }
//        return result;
//    }
}
