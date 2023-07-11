package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Address extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String receiverName;

    private String receiverPhone;

    /**
     * 地址
     */
    private String address;

    /**
     * 详细地址
     */
    private String detailAddress;


}
