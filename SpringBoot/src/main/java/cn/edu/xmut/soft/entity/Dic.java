package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Dic extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典name
     */
    private String name;

    /**
     * 字典value
     */
    private String value;

    /**
     * 所属字典类型
     */
    private String dictypeId;


}
