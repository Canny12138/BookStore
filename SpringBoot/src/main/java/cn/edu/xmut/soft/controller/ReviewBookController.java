package cn.edu.xmut.soft.controller;

import cn.edu.xmut.soft.entity.Review;
import cn.edu.xmut.soft.mapper.ReviewMapper;
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
import cn.edu.xmut.soft.mapper.ReviewBookMapper;
import cn.edu.xmut.soft.entity.ReviewBook;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/soft/review-book")
public class ReviewBookController extends BaseController {
    @Autowired
    private ReviewBookMapper reviewBookMapper;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String bookId) {
        Result result = new Result();

        Page<ReviewBook> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ReviewBook> wrapper = new QueryWrapper<>();

        if (bookId != null)
        {
            wrapper.eq("book_id", bookId);
        }

        Page<ReviewBook> reviewBookPage = reviewBookMapper.selectPage(page, wrapper);
        List<ReviewBook> reviewBookList = reviewBookPage.getRecords();

        for (ReviewBook reviewBook : reviewBookList) {
            String reviewId = reviewBook.getReviewId();
            Review review = reviewMapper.selectById(reviewId);
            reviewBook.setReview(review);
        }

        result.setData(reviewBookPage);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody ReviewBook entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            reviewBookMapper.insert(entity);
        } else {
            reviewBookMapper.updateById(entity);
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
        reviewBookMapper.deleteBatchIds(deleteIds);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            ReviewBook reviewBook = new ReviewBook();
//            reviewBook.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<ReviewBook> reviewBookUpdateWrapper = new UpdateWrapper<>();
//            reviewBookUpdateWrapper.eq("id", id);
//
//            reviewBookMapper.update(reviewBook, reviewBookUpdateWrapper);
//        }
//        return result;
//    }
}
