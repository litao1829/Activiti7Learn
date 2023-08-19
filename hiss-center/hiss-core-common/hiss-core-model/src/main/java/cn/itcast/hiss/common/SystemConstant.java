package cn.itcast.hiss.common;

/**
 * SystemConstant
 *
 * @author: wgl
 * @describe: 系统常量
 * @date: 2022/12/28 10:10
 */
public class SystemConstant {

    public final static String CLIENT_FLAG = "_W_G_L_Hiss";

    /**
     * 流程标题变量，用于抄送、知会等流程，获取标题的问题
     */
    public final static String PROCESS_VARIABLES_CREATE_TITLE = "hissPT";

    /**
     * 流程发起人ID
     */
    public final static String TASK_VARIABLES_CREATE_USERID = "hissCreateUserId";

    /**
     * 流程发起人用户名
     */
    public final static String TASK_VARIABLES_CREATE_USERNAME = "hissCreateUserName";

    /**
     * 拒绝任务原因
     */
    public final static String REJECT_TASK_REASON = "hissRejectTaskId";

    /**
     * 拒绝任务ID
     */
    public final static String REJECT_TASK_ID = "hissRejectTaskId";



    /**
     * 拒绝时间
     */
    public static final String REJECT_TASK_REJECT_TIME = "hissRejectTime";

    /**
     * 拒绝的流程实例ID
     */
    public static final String REJECT_TASK_REJECT_PROCESSINSTANCE_ID = "hissRejectProcessInstanceId";

    /**
     * 拒绝的流程定义ID
     */
    public static final String REJECT_TASK_REJECT_PROCESSDEFINITION_ID = "hissRejectProcessDefinitionId";


    /**
     * 会签任务总数
     */
    public static final String MULTILINSTANCE_NUMBER_OF_INSTANCES = "nrOfInstances";

    /**
     * 会签任务总数
     */
    public static final String MULTILINSTANCE_NUMBER_OF_ACTIVE = "nrOfActiveInstances";

    /**
     * 完成任务数量
     */
    public static final String COMPLATE_INSTANCES = "nrOfCompletedInstances";

    /**
     * 执行人列表
     */
    public static final String MULTILINSTANCE_ASSIGNEE_LIST = "assigneeList";

    /**
     * 循环次数
     */
    public static final String MULTILINSTANCE_LOOPCOUNTER =  "loopCounter";

    /**
     * 执行人
     */
    public static final String ASSIGNEE = "assignee";

    /**
     * 默认的表单状态为未发布  0未发布 1：已发布
     */
    public static final String FORM_DEFAUTL_DATA_STATE = "0";


    /**
     * 使用中 0 以停用或删除 1
     */
    public static final boolean FORM_USE = true;

    /**
     * 外部表单
     */
    public static final String FORM_OUTER_FORM = "1";

    /**
     * 内部表单
     */
    public static final String FORM_INNER_FORM = "0";

    /**
     * 单条消息的结尾标识，用于解决netty合包问题
     */
    public static final String NETTY_MESSAGE_SUFFIX = "_H_I_S_S_";
}
