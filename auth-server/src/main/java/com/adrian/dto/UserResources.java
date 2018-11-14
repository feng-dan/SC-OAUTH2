package com.adrian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色资源
 *
 * @author fengdan
 * @date 2018/10/9
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UserResources implements Serializable {
    private static final long serialVersionUID = 6308251885690419888L;
    private Long id;
    /**
     * 资源名称，如xx菜单，xx按钮
     */
    private String name;

    private String value;

    /**
     * 资源链接
     */
    private String path;
    /**
     * 图标
     */
    private String iconCls;

    private boolean enabled;
    /**
     * 资源类型
     */
    private String resourceType;

    private String component;


}
