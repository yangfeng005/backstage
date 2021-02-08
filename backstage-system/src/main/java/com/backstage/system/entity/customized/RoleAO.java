package com.backstage.system.entity.customized;

import com.backstage.system.entity.gen.Role;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import java.io.Serializable;
import java.util.List;

/**
 * 应用对象 - Role.
 * <p>
 * 该类于 2018-06-14 13:25:21 首次生成，后由开发手工维护。
 * </p>
 *
 * @author yangfeng
 * @version 1.0.0, Jun 14, 2018
 */
@JsonSerialize(include = Inclusion.ALWAYS)
@Data
public final class RoleAO extends Role implements Serializable {

    /**
     * 默认的序列化 id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * 角色对应的的人数
     */
    private Integer userCount;

    /**
     * 角色人员列表
     */
    private List<UserAO> users;

    /**
     * 机构名称
     */
    private String agencyName;
}
