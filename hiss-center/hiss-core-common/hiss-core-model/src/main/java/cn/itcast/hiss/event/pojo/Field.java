package cn.itcast.hiss.event.pojo;

import lombok.Data;

/*
 * @author miukoo
 * @description 定义的字段信息
 * @date 2023/5/27 16:01
 * @version 1.0
 **/
@Data
public class Field {
    protected String name;
    protected String type;
    protected Object value;
}
