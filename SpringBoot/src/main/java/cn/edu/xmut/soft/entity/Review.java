package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Review extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String orderId;

    private ReviewBook reviewBook;

    private Integer rating;

    private String content;

    private String createTime;


}
