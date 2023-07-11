package cn.edu.xmut.soft.entity;

import cn.edu.xmut.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 所属user
     */
    private String userId;

    /**
     * 所属role
     */
    private String roleId;


}
