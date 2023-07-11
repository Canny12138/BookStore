package cn.edu.xmut.soft.controller;

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
import cn.edu.xmut.soft.mapper.BookSaleMapper;
import cn.edu.xmut.soft.entity.BookSale;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/book-sale")
public class    BookSaleController extends BaseController {
    @Autowired
    private BookSaleMapper bookSaleMapper;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize) {
        Result result = new Result();
        Page<BookSale> page = new Page<BookSale>(pageNum, pageSize);
        BookSale parms = new BookSale();
        QueryWrapper<BookSale> warpper = new QueryWrapper<BookSale>(parms);

        result.setData(bookSaleMapper.selectPage(page, warpper));
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(@RequestBody BookSale entity) {
        Result result = new Result();
        if (entity.getId() == null) {
            bookSaleMapper.insert(entity);
        } else {
            bookSaleMapper.updateById(entity);
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
        bookSaleMapper.deleteBatchIds(deleteIds);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
    public Result updateUseful( String ids,Integer useful) {
        Result result = new Result();

        for (String id : ids.split(",")) {

            //修改值
            BookSale bookSale = new BookSale();
            bookSale.setUseful(useful);

            //修改条件
            UpdateWrapper<BookSale> bookSaleUpdateWrapper = new UpdateWrapper<>();
            bookSaleUpdateWrapper.eq("id", id);

            bookSaleMapper.update(bookSale, bookSaleUpdateWrapper);
        }
        return result;
    }
}
