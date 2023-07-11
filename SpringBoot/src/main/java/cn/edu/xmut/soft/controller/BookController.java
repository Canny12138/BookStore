package cn.edu.xmut.soft.controller;

import cn.edu.xmut.utils.DateTool;
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
import cn.edu.xmut.soft.mapper.BookMapper;
import cn.edu.xmut.soft.entity.Book;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/book")
public class BookController extends BaseController {
    @Autowired
    private BookMapper bookMapper;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String title,
        String bookid,
        Integer useful,
        Integer category,
        String rank) {
        Result result = new Result();
        Page<Book> page = new Page<Book>(pageNum, pageSize);
        Book parms = new Book();
        QueryWrapper<Book> warpper = new QueryWrapper<Book>(parms);

        if(title != null && !"".equals(title)){
            warpper.like("title",title);
        }
        if(bookid != null && !"".equals(bookid)){
            warpper.eq("id",bookid);
        }
        if(useful != null){
            warpper.eq("useful",useful);
        }
        if(category != null && category != 0){
            warpper.eq("category",category);
        }
        if(rank != null && rank.equals("clicks")){
            warpper.orderByDesc("clicks");
        }
        if(rank != null && rank.equals("sales")){
            warpper.orderByDesc("sales");
        }
        if(rank != null && rank.equals("collects")){
            warpper.orderByDesc("collects");
        }
        result.setData(bookMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody Book entity) {
        Result result = new Result();
        entity.setImgSrc("img/" + entity.getImgSrc());
        if (entity.getId() == null) {
            entity.setUseful(1);
            entity.setCreateTime(DateTool.getCurrTime());
            bookMapper.insert(entity);
        } else {
            bookMapper.updateById(entity);
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
        bookMapper.deleteBatchIds(deleteIds);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
    public Result updateUseful( String ids,Integer useful) {
        Result result = new Result();

        for (String id : ids.split(",")) {

            //修改值
            Book book = new Book();
            book.setUseful(useful);

            //修改条件
            UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<>();
            bookUpdateWrapper.eq("id", id);

            bookMapper.update(book, bookUpdateWrapper);
        }
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateClicks")
    public Result updateClicks(String bookId) {
        Result result = new Result();

        // 查询图书信息
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            result.setMessage("书本不存在");
            return result;
        }

        // 点击数加1
        book.setClicks(book.getClicks() + 1);

        // 更新数据库
        bookMapper.updateById(book);

        result.setMessage("更新点击数成功");
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateSales")
    public Result updateSales(String bookId) {
        Result result = new Result();

        // 查询图书信息
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            result.setMessage("书本不存在");
            return result;
        }

        // 点击数加1
        book.setSales(book.getSales() + 1);

        // 更新数据库
        bookMapper.updateById(book);

        result.setMessage("更新点击数成功");
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/detail")
//    public Result bookDetail(String bookid) {
//        Result result = new Result();
////        Page<Book> page = new Page<Book>(pageNum, pageSize);
//        Book parms = new Book();
//        QueryWrapper<Book> warpper = new QueryWrapper<Book>(parms);
//
//        if(bookid != null && !"".equals(bookid)){
//            warpper.eq("id",bookid);
//        }
//
//        result.setData(bookMapper.selectOne(warpper));
//        return result;
//    }
}
