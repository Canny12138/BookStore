package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BookSale extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 书名
     */
    private String title;

    /**
     * 价格
     */
    private Double price;

    /**
     * 是否可用 0- 不可用 1-可用
     */
    private Integer useful;

    /**
     * 创建时间 YYYY-mm-DD HH:MM:SS
     */
    private String createTime;

    /**
     * 书籍封面
     */
    private String imgSrc;


}
