package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Orders extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String orderNumber;

    private Integer status;

    private Double price;

    private String addressId;

    private String createTime;

    private List<OrderDetail> orderDetail;

    private String orderStatus;

    private Address address;
}
