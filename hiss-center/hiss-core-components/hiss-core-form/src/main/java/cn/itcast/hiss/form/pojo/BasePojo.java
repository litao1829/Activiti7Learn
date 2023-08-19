package cn.itcast.hiss.form.pojo;

import lombok.Data;

import java.util.Map;

/**
 * BasePojo
 *
 * @author: wgl
 * @describe: 父类
 * @date: 2022/12/28 10:10
 */
@Data
public class BasePojo {

    /**
     * 主键 系统主键
     */
    private String id;

    /**
     * 需要用到的参数
     */
    private Map params;

}
