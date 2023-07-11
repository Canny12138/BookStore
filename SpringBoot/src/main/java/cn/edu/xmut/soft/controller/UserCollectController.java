package cn.edu.xmut.soft.controller;

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
import cn.edu.xmut.soft.mapper.UserCollectMapper;
import cn.edu.xmut.soft.entity.UserCollect;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/soft/user-collect")
public class UserCollectController extends BaseController {
    @Autowired
    private UserCollectMapper userCollectMapper;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String bookId) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);

        Result result = new Result();
        Page<UserCollect> page = new Page<UserCollect>(pageNum, pageSize);
        UserCollect parms = new UserCollect();
        QueryWrapper<UserCollect> warpper = new QueryWrapper<UserCollect>(parms);
        warpper.eq("user_id", userId);
        if(bookId != null && !"".equals(bookId)){
            warpper.eq("book_id", bookId);
        }

        result.setData(userCollectMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(String bookId) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);

        Result result = new Result();
        UserCollect uc = new UserCollect();
        uc.setUserId(userId);
        uc.setBookId(bookId);
        userCollectMapper.insert(uc);
        return result;
    }
    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete(String bookId) {
        String token = request.getHeader("token");
        System.out.println("headerToken: "+token);
        String userId = JwtUtil.getUserId(token);

        Result result = new Result();
        UserCollect parms = new UserCollect();
        QueryWrapper<UserCollect> warpper = new QueryWrapper<UserCollect>(parms);
        warpper.eq("user_id",userId);
        warpper.eq("book_id",bookId);
        userCollectMapper.delete(warpper);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            UserCollect userCollect = new UserCollect();
//            userCollect.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<UserCollect> userCollectUpdateWrapper = new UpdateWrapper<>();
//            userCollectUpdateWrapper.eq("id", id);
//
//            userCollectMapper.update(userCollect, userCollectUpdateWrapper);
//        }
//        return result;
//    }
}
