package com.backstage.system.entity.customized;

import com.backstage.core.constant.Constant;
import com.backstage.system.entity.gen.User;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;
import java.util.List;

/**
 * 应用对象 - User.
 * <p>
 * 该类于 2018-06-14 13:25:21 首次生成，后由开发手工维护。
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Jun 14, 2018
 */
@JsonSerialize(include = Inclusion.ALWAYS)
@Data
public final class UserAO extends User implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户拥有的角色
     */
    private List<RoleAO> roles;

    /**
     * 用户拥有的权限
     */
    private List<VUserPrivilegeAO> privileges;

    /**
     * 机构名称
     */
    private String agencyName;


    /**
     * 判断是否系统超级管理员
     *
     * @return
     */
    public Boolean isSuperAdmin() {
        if (Constant.SYSTEM_SUPER_ADMIN.equals(super.getUserName())) {
            return true;
        }
        return false;
    }
}
