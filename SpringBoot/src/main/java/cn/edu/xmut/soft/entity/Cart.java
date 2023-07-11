package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Cart extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String bookId;

    private Integer quantity;

    private Integer checked;

    private Book book;

    public void setBook(Book book) {
        this.book = book;
    }
}
