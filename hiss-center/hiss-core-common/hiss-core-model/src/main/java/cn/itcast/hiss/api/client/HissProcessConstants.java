package cn.itcast.hiss.api.client;

/*
 * @author miukoo
 * @description 流程内使用到的常量
 * @date 2023/6/3 11:23
 * @version 1.0
 **/
public class HissProcessConstants {

    /**
     * 流程引擎超管用户名
     */
    public final static String ADMIN_ID = "hiss";

    /**
     * 和客户端协定的用户信息获取变量
     */
    public final static String CLIENT_USER_NAME = "client.directUser";

    /**
     * 流程运行的资源文件名称
     */
    public final static String PROCESS_RUN_XML = "hiss.bpmn20.xml";

    /**
     * 流程运行的资源文件名称
     */
    public final static String PROCESS_SHOW_CONFIG_JSON = "hiss.show.json";

    /**
     * 流程展示的资源文件名称
     */
    public final static String PROCESS_SHOW_XML = "hiss.show.xml";

    /**
     * 用于标记当前待办任务是什么类型
     */
    public final static String TASK_TYPE = "HISS_TASK_TYPE";

    /**
     * 标记流程状态信息
     */
    public final static String PROCESS_STATUS = "PROCESS_STATUS";

    /**
     * 标记内部执行的任务类前缀
     */
    public final static String INNER_CLASS_PREFIX = "#inner#";

    /**
     * 流程发起限定人员的白名单列表
     */
    public final static String PROCESSDEFINITION_WHITE_LIST_USERS = "PROCESSDEFINITION_WHITE_LIST_USERS";

    /**
     * 流程中空节点的配置前缀
     */
    public final static String PROCESS_NULL_NODE = "PROCESS_NULL_NODE_";

    /**
     * 父流程实例,用于流程标记
     */
    public final static String PARENT_PROCESS_INSTANCE = "PARENT_PROCESS_INSTANCE";

    /**
     * 任务自动完成任务 0:不自动完成 1：自动完成
     */
    public static final String TASK_NULL_NODE_AUTO_COMPLATE_FLAG = "HISS_TASK_NULL_NODE_AUTO_COMPLATE_FLAG_";

    /**
     * 业务模式转成运行模式时，标记的流程节点类型
     */
    public static final String NODE_TYPE = "NODE_TYPE";

    public static String getNullNodeAutoFlag(String nodeId){
        return TASK_NULL_NODE_AUTO_COMPLATE_FLAG + "_" + nodeId;
    }

    /**
     * 任务自动完成标识：用于业务模式下stater的自动跳过
     */
    public static final String TASK_AUTO_COMPLATE_FLAG = "HISS_TASK_AUTO_COMPLATE_FLAG_";
    public static final String TASK_VAR_AUTO_COMPLATE = "1";//自动完成

    public static String getAutoComplateFlag(String nodeId){
        return TASK_AUTO_COMPLATE_FLAG + "_" + nodeId;
    }


    public static final String TASK_VAR_NULL_NODE_AUTO_COMPLATE = "1";//自动完成

    /**
     * 内部错误状态码标识
     */
    public static final String BPMN_ERROR = "BPMN_ERROR";


}
