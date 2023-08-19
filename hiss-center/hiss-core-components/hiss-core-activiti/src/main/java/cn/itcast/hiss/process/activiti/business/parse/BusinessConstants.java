package cn.itcast.hiss.process.activiti.business.parse;

/*
 * @author miukoo
 * @description 业务模式xml中用到的固定变量值
 * @date 2023/6/6 13:11
 * @version 1.0
 **/
public class BusinessConstants {

    // 发起人设置
    public static final String NODE_CONFING_STARTER_USER_TYPE = "hissBusinessStarterUserType";
    // 可发起的人配置
    public static final String NODE_CONFING_STARTER_USE_USER = "useUser";
    // 固定人员名称
    public static final String NODE_CONFING_STARTER_FIXED_USER = "fixedUser";

    // 表单配置属性名
    public static final String FORM_CONFIG_NAME = "formConfig";
    // 单人、多人节点的用户类型
    public static final String APPROVAL_USER_TYPE = "hissBusinessSingleUserType";
    // 单人审批节点的模式，人工审批、自动通过、自动拒绝
    public static final String SINGLE_APPROVAL_MODE ="hissBusinessSingleMode";
    // 多人审批节点的模式，并签、串签、一签、2签、3签、半签
    public static final String MULTIPLE_APPROVAL_MODE ="hissBusinessMultipleMode";

    // 空节点处理方式 0 挂起，1自动通过
    public static final String NULL_NODE_MODE ="hissBusinessNullNodeMode";
}
