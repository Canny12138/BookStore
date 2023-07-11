package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Student extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private String no;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 是否可用 0- 不可用 1-可用
     */
    private Integer useful;


}
