package cn.itcast.hiss.handler;

import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZoneId;

/**
 * CommonHandlerIdEnum
 *
 * @author: wgl
 * @describe: 公共的处理器ID整合枚举类
 * @date: 2022/12/28 10:10
 */
@AllArgsConstructor
@Getter
public enum HandlerIdClientEnum {
    //==============流程引擎系统功能===================
    SYS_USER_APP_LIST("USER_APP_LIST", "户应用列表查询"),
    SYS_USER_APP_ADD_OR_UPDATE("USER_APP_ADD_OR_UPDATE", "户应用新增或更新"),
    SYS_USER_APP_DELETE("USER_APP_DELETE", "用户应用删除"),
    SYS_SYSTEM_USER_LOGIN("SYSTEM_USER_LOGIN", "用户登录"),
    SYS_SYSTEM_USER_LIST("SYSTEM_USER_LIST", "用户列表查询"),
    SYS_SYSTEM_USER_ADD_OR_UPDATE("SYSTEM_USER_ADD_OR_UPDATE", "用户新增或更新"),
    SYS_SYSTEM_USER_DELETE("SYSTEM_USER_DELETE", "用户删除"),
    SYS_SYSTEM_USER_RESET_PASSWORD("SYSTEM_USER_RESET_PASSWORD", "用户密码重置"),

    SYS_PROCESSS_CATEGORY_ADD_OR_UPDATE("PROCESSS_CATEGORY_ADD_OR_UPDATE", "流程分类新增或更新"),
    SYS_PROCESSS_CATEGORY_DELETE("PROCESSS_CATEGORY_DELETE", "流程分类删除"),
    SYS_PROCESSS_CATEGORY_LIST("PROCESSS_CATEGORY_LIST", "流程分类查询"),
    SYS_PROCESS_MODEL_LIST("PROCESS_MODEL_LIST", "流程设计模型查询"),
    SYS_PROCESS_INSTANCE_LIST("PROCESS_INSTANCE_LIST", "流程实例查询"),
    SYS_PROCESS_INSTANCE_DELETE("PROCESS_INSTANCE_DELETE", "流程实例删除"),
    SYS_PROCESS_MODEL_DELETE("PROCESS_MODEL_DELETE", "流程设计模型查询"),
    SYS_FORM_CATEGORY_ADD_OR_UPDATE("FORM_CATEGORY_ADD_OR_UPDATE", "表单分类新增或更新"),
    SYS_FORM_CATEGORY_DELETE("FORM_CATEGORY_DELETE", "表单分类删除"),
    SYS_FORM_CATEGORY_LIST("FORM_CATEGORY_LIST", "表单分类查询"),

    SYS_FORM_MODEL_LIST("FORM_MODEL_LIST", "表单模型查询"),
    SYS_FORM_MODEL_DELETE("FORM_MODEL_DELETE", "表单模型删除"),

    SYS_SCAFFOLD_EXPORT("SCAFFOLD_EXPORT", "脚手架导出集成项目"),
    //==============客户端使用的===================
    CLI_PROCESS_INSTANCE_DELETE("CLI_PROCESS_INSTANCE_DELETE", "流程实例删除"),
    CLI_PROCESS_INSTANCE_LIST("CLI_PROCESS_INSTANCE_LIST", "流程实例查询"),
    CLI_FORM_MODEL_DELETE("CLI_FORM_MODEL_DELETE", "表单模型删除"),
    CLI_FORM_MODEL_LIST("CLI_FORM_MODEL_LIST", "表单模型查询"),
    CLI_FORM_CATEGORY_LIST("CLI_FORM_CATEGORY_LIST", "表单分类查询"),
    CLI_PROCESSS_CATEGORY_LIST("CLI_PROCESSS_CATEGORY_LIST", "流程分类查询"),
    CLI_PROCESS_MODEL_DELETE("CLI_PROCESS_MODEL_DELETE", "流程设计模型删除"),
    CLI_PROCESS_MODEL_LIST("CLI_PROCESS_MODEL_LIST", "流程设计模型查询"),
    CLI_PROCESS_APPLY_LIST("CLI_PROCESS_APPLY_LIST", "申请人发起的流程查询"),
    CLI_PROCESS_APPLY_DELETE("CLI_PROCESS_APPLY_DELETE", "删除申请人发起的流程"),
    CLI_PROCESS_HANDLE_LIST("CLI_PROCESS_HANDLE_LIST", "操作人的办理流程查询"),

    CLI_FLOW_M_PRE_START_MODEL_FOR_BIS("CLI_FLOW_M_PRE_START_MODEL_FOR_BIS", "通过Model启动业务预模式的流程"),

    //==============系统模块===================
    CUSTOM_VARIABLES("CustomVariables", "自定义变量"),

    SERVER_VARIABLES_METHOD("SERVER_VARIABLES_METHOD", "服务端用来调用客户端获取自定义变量方法"),
    CUSTOM_VARIABLES_METHOD("CUSTOM_VARIABLES_METHOD", "调用自定义变量方法"),
    SYS_VARIABLE("SYS_UEL_VARIABLE", "系统的UEL表达式变量处理器"),
    SYS_GET_VARIABLE("SYS_GET_VARIABLE", "获取系统的UEL表达式变量"),
    //    SYS_HEARTBEAT("HISS_HEADBEAT", "心跳检测"),
    //==============历史记录===================
    HIS_USER_HISTORY("USER_HISTORY", "用户历史数据"),
    HIS_PID_INSTANCE("PID_INSTANCE", "历史实例"),
    HIS_GET_HIGNT_LINE("GET_HIGNT_LINE", "获取高亮"),
    //==============流程定义===============
    PD_MODEL_TO_DEPLOYMENT("MODEL_TO_DEPLOYMENT", "设计部署发布"),
    PD_UPLOAD_ZIP_DEPLOYMENT("UPLOAD_ZIP_DEPLOYMENT", "根据ZIP上传流程"),
    PD_FLOW_M_UPLOAD_DEPLOYMENT("FLOW_M_UPLOAD_DEPLOYMENT", "上传流程文件"),
    PD_DELETE_DEFINITION("DELETE_DEFINITION", "删除流程定义"),
    PD_FLOW_M_SAVE_MODEL_FOR_DEV("FLOW_M_SAVE_MODEL_FOR_DEV", "保存开发者模式的流程信息"),
    PD_FLOW_M_GET_MODEL_FOR_DEV("FLOW_M_GET_MODEL_FOR_DEV", "查询开发者模式的流程信息"),
    PD_FLOW_M_START_MODEL_FOR_DEV("FLOW_M_START_MODEL_FOR_DEV", "通过Model启动开发者模式的流程"),
    PD_FLOW_M_START_MODEL_FOR_BIS("FLOW_M_START_MODEL_FOR_BIS", "通过Model启动业务模式的流程"),
    PD_FLOW_M_PRE_START_MODEL_FOR_BIS("FLOW_M_PRE_START_MODEL_FOR_BIS", "通过Model启动业务预模式的流程"),
    PD_FLOW_M_SAVE_MODEL_FOR_BIS("FLOW_M_SAVE_MODEL_FOR_BIS", "保存业务模式的流程信息"),
    PD_FLOW_M_TODEV_MODEL_FOR_BIS("FLOW_M_TODEV_MODEL_FOR_BIS", "业务模式转为开发者模式"),
    PD_FLOW_M_VERIF_MODEL_FOR_BIS("FLOW_M_VERIF_MODEL_FOR_BIS", "验证业务模式的流程信息"),
    PD_FLOW_M_GET_MODEL_FOR_BIS("FLOW_M_GET_MODEL_FOR_BIS", "查询业务模式的流程信息"),
    PD_GET_DEPLOYMENT("GET_DEPLOYMENT", "获取流程部署"),

    //=============流程实例================
    PI_CREATE_INSTANCE("CREATE_INSTANCE", "创建流程实例"),
    PI_GET_INSTANCE("GET_INSTANCE", "获取流程实例"),
    PI_GET_INSTANCE_DEV_VIEWER("GET_INSTANCE_DEV_VIEWER", "获取开发者模式流程高亮实例"),
    PI_START_PROCESS("START_PROCESS", "启动流程"),
    PI_DETE_INSTANCE("DELETE_INSTANCE", "删除流程实例"),
    PI_SUSPEND_INSTANCE("SUSPEND_INSTANCE", "挂起流程"),
    PI_RESUME_INSTANCE("RESUME_INSTANCE", "激活流程"),
    PI_VARIABLES("VARIABLES", "获取流程参数"),
    //=============任务==================
    TASK_GET_MY_TASK("GET_MY_TASK", "获取我的代办任务"),
    TASK_CLAIM_TASK("CLAIM_TASK", "拾取任务"),
    TASK_EXPEDITE_TASK("EXPEDITE_TASK", "催办任务"),

    TASK_RESTART_TASK("RESTART_TASK", "重新发起任务"),
    TASK_SEND_TASK("SEND_TASK", "发起任务"),
    TASK_UNCLAIM_TASK("UNCLAIM_TASK", "归还任务"),
    TASK_ASSIGNED_TASK("ASSIGED_TASK", "归还,交办任务"),

    TASK_APPROVE_TASK("APPROVE_TASK", "同意待办任务"),
    TASK_SUBMIT_TASK("SUBMIT_TASK", "提交待办任务"),// 与同意功能相同
    TASK_CANCEl_TASK("CANCEl_TASK", "取消任务"),
    TASK_NOTIFICATION_TASK("NOTIFICATION_TASK", "知会任务"),
    TASK_CC_TASK("CC_TASK", "抄送任务"),
    TASK_READ_TASK("READ_TASK", "知晓任务"),

    TASK_START_PROCESS_TASK("START_PROCESS_TASK", "启动任务"),
    TASK_FROM_DATA_SHOW("FROM_DATA_SHOW", "表单渲染"),
    TASK_FROM_DATA_SAVE("SAVE_DATA_SAVE", "保存表单"),
    TASK_REJECT_TASK("REJECT_TASK", "不同意任务"),
    TASK_ROLLBACK_TASK("ROLLBACK_TASK", "驳回任务"),
    TASK_JUMP_TASK("JUMP_TASK", "跳转任务"),
    TASK_WITHDRAW_TASK("WITHDRAW_TASK", "撤回任务"),
    TASK_DELEGATE_TASK("DELEGATE_TASK", "指派任务"),
    TASK_ADDEXECUTION_TASK("AFTER_SIGN_TASK", "后加签任务"),

    TASK_BEFORE_SIGN_TASK("BEFORE_SIGN_TASK", "前加签任务"),

    TASK_PARALLEL_SIGN_TASK("PARALLEL_SIGN_TASK", "并行加签任务"),

    TASK_TIGGER_TASK("TIGGER_TASK", "信号任务"),
    TASK_RECEIVED_TASK("RECEIVED_TASK", "触发任务"),

    //=======================================表单form=======================================
    FORM_SERVER_GET_FORM_DIFINITION("SERVER_GET_FORM_DIFINITION", "服务端接口获取表单定义"),


    FROM_CLIENT_GET_FROM_DIFINITION("CLIENT_GET_FORM_DIFINITION", "客户端接口获取表单定义"),

    FORM_CREATE_FORM_DIFINITION("CREATE_FORM_DIFINITION", "创建表单定义"),


    FORM_ARG_VALIDATED("ARG_VALIDATED", "表单参数校验"),

    FORM_TASK_VALIDATE_TYPE("TASK_VALIDATE_TYPE", "任务校验类型"),

    FORM_ONLY_VALIDATE("ONLY_VALIDATE", "唯一性校验"),

    FORM_GET_MY_FORM("GET_MY_FORM","获取我的表单列表"),
    FORM_GET_MY_FORM_FIELD("GET_MY_FORM_FIELD","获取我的表单字段"),

    FORM_GET_MY_FORM_DATA_LIST("GET_MY_FORM_DATA_LIST","获取我的表单字段"),
    FORM_GET_MY_FORM_DATA_DELETE("GET_MY_FORM_DATA_DELETE","表单数据删除"),

    FORM_GET_MY_FORM_DATA("GET_MY_FORM_DATA","获取我的表单回显数据"),
    FORM_GET_MY_FORM_AUTO_FILL_DATA("GET_MY_FORM_AUTO_FILL_DATA","获取表单自动填充的字段数据"),

    FORM_SUBMIT_DATA("SUBMIT_FORM_DATA", "提交表单数据"),

    FORM_ACTIVITI_DEFINITION_GET_FORM_DIFINITION("ACTIVITI_DEFINITION_GET_FORM_DIFINITION", "获取流程定义表单定义"),
    ;


    private String id;

    private String name;
}
