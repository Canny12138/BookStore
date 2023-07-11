package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ReviewBook extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String reviewId;

    private String bookId;

    private Review review;

}
