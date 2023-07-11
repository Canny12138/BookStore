package cn.edu.xmut.soft.controller;

import cn.edu.xmut.soft.entity.Book;
import cn.edu.xmut.soft.mapper.BookMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.soft.mapper.SaleMapper;
import cn.edu.xmut.soft.entity.Sale;

import org.springframework.web.bind.annotation.RestController;
import cn.edu.xmut.common.BaseController;

@RestController
@RequestMapping("/soft/sale")
public class SaleController extends BaseController {
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping(method= RequestMethod.POST,value="/page")
    public Result page(
        Integer pageNum,
        Integer pageSize,
        String bookId) {
        Result result = new Result();

        // 创建Page对象
        Page<Sale> page = new Page<>(pageNum, pageSize);

        // 创建查询条件
        QueryWrapper<Sale> queryWrapper = new QueryWrapper<>();
        if(bookId != null) {
            queryWrapper.eq("book_id", bookId);
        }
        // 分页查询
        Page<Sale> salePage = saleMapper.selectPage(page, queryWrapper);

        // 通过Sale中的bookId查询Book详情
        List<Sale> saleList = salePage.getRecords();
        for (Sale sale : saleList) {
            Book book = bookMapper.selectById(sale.getBookId());
            sale.setBook(book);  // 假设Sale类中有setBook方法
        }
        result.setData(salePage);
        return result;
    }
    @RequestMapping(method= RequestMethod.POST,value="/save")
    public Result save(String bookId, double price, Integer times) {
        Result result = new Result();
        System.out.println(bookId);
        // 查询图书的原价并保存到sale中
        Book book = bookMapper.selectById(bookId);
        if (book == null) {
            result.setMessage("书本不存在");
            return result;
        }

        Sale sale = new Sale();
        sale.setBookId(bookId);
//        double d = Double.parseDouble(price);
        sale.setPrice(price);
        sale.setOldPrice(book.getPrice());
        sale.setTimes(times);

        // 设置图书的促销价格和促销状态
        book.setPrice(sale.getPrice());
        book.setOnSale(1);
        bookMapper.updateById(book);

        // 保存销售信息到数据库
        sale.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sale.setCreateTimeId(String.valueOf(System.currentTimeMillis()));
        saleMapper.insert(sale);

        // 创建一个RocketMQ的延时消息，将在指定时间后发送
        Message<String> message = MessageBuilder.withPayload(sale.getId()).build();
        rocketMQTemplate.syncSend("saleTopic", message, 30000, sale.getTimes());

        result.setMessage("秒杀信息保存成功");
        return result;
    }
    private int getDelayTimeLevel(Integer times) {
        // 根据实际情况确定RocketMQ的延时等级
        // 这里只是一个例子，实际项目中需要根据需求和RocketMQ的延时等级设定来确定
        if (times <= 1) {
            return 1;  // 1分钟
        } else if (times <= 5) {
            return 2;  // 5分钟
        } else if (times <= 10) {
            return 3;  // 10分钟
        } else {
            return 4;  // 30分钟
        }
    }

    @RequestMapping(method= RequestMethod.DELETE,value="/delete")
    public Result delete( String ids) {
        Result result = new Result();
        List<String> deleteIds = new ArrayList<String>();
        for (String id : ids.split(",")) {
            deleteIds.add(id);
        }
        saleMapper.deleteBatchIds(deleteIds);
        return result;
    }
//    @RequestMapping(method= RequestMethod.POST,value="/updateUseful")
//    public Result updateUseful( String ids,Integer useful) {
//        Result result = new Result();
//
//        for (String id : ids.split(",")) {
//
//            //修改值
//            Sale sale = new Sale();
//            sale.setUseful(useful);
//
//            //修改条件
//            UpdateWrapper<Sale> saleUpdateWrapper = new UpdateWrapper<>();
//            saleUpdateWrapper.eq("id", id);
//
//            saleMapper.update(sale, saleUpdateWrapper);
//        }
//        return result;
//    }
}
