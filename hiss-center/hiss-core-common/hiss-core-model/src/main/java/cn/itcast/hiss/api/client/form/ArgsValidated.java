package cn.itcast.hiss.api.client.form;

import lombok.Builder;
import lombok.Data;

/**
 * ArgsValidated
 *
 * @author: wgl
 * @describe: 参数校验
 * @date: 2022/12/28 10:10
 */
@Data
@Builder
public class ArgsValidated {

    /**
     * 模型参数
     */
    private String modelId;

    /**
     * 字段ID
     */
    private String filedId;

    /**
     * 数据ID
     */
    private String dataId;

    /**
     * 用户填写的参数
     */
    private String args;

    /**
     * 参数校验类型
     */
    private String type;
}
