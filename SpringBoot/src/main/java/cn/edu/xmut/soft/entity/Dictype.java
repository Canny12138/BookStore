package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Dictype extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典类型name
     */
    private String name;

    /**
     * 字典类型value
     */
    private String value;


}
