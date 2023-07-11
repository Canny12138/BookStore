package cn.edu.xmut.soft.service;

import cn.edu.xmut.soft.entity.Book;
import cn.edu.xmut.soft.entity.Sale;
import cn.edu.xmut.soft.mapper.BookMapper;
import cn.edu.xmut.soft.mapper.SaleMapper;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(topic = "saleTopic", consumerGroup = "saleConsumerGroup")
public class SaleConsumer implements RocketMQListener<String>
{
    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public void onMessage(String saleId) {
        // 根据saleId查询销售信息
        Sale sale = saleMapper.selectById(saleId);
        if (sale != null) {
            // 根据销售信息中的bookId查询图书信息
            Book book = bookMapper.selectById(sale.getBookId());
            if (book != null) {
                // 修改图书的价格为原价，修改促销状态为0
                book.setPrice(sale.getOldPrice());
                book.setOnSale(0);
                bookMapper.updateById(book);
            }

            // 从销售表中删除该销售信息
            saleMapper.deleteById(saleId);
        }
    }
}