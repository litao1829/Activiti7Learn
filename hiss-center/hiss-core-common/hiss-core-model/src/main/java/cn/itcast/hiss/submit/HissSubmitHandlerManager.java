package cn.itcast.hiss.client.manager;

import cn.itcast.hiss.submit.SubmitTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * HissSubmitHandlerManager
 *
 * @author: wgl
 * @describe: 客户端表达式处理器管理类
 * @date: 2022/12/28 10:10
 */
public class HissSubmitHandlerManager {

    private static List<SubmitTemplate> submitTemplateList = new ArrayList<SubmitTemplate>();

    /**
     * 添加上报处理类
     *
     * @param submitTemplate
     */
    public static void addSubmitHandler(SubmitTemplate submitTemplate) {
        submitTemplateList.add(submitTemplate);
    }

    /**
     * 移除上报任务
     *
     * @param submitTemplate
     */
    public static void removeSubmitTemplate(SubmitTemplate submitTemplate) {
        submitTemplateList.remove(submitTemplate);
    }

    public static void submitAll() {
        for (SubmitTemplate index: submitTemplateList) {
            index.submit();
        }
    }

    /**
     * 获取所有的上报处理类
     *
     * @return
     */
    public List<SubmitTemplate> getSubmitHandlerList() {
        return submitTemplateList;
    }
}