package cn.itcast.hiss.process.activiti.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 多实例会签封装类
 */
@Data
public class MultiInstanceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会签类型（串行，并行）
     */
    private Object type;

    /**
     * 会签人员KEY
     */
    private String assignee;

    /**
     * 会签人员集合KEY
     */
    private String assigneeList;
}
