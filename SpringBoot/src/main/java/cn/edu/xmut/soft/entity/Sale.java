package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Sale extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String bookId;

    private Double price;

    private Double oldPrice;

    private String createTime;

    /**
     * 持续时间 单位分钟
     */
    private Integer times;

    private String createTimeId;

    private Book book;
}
