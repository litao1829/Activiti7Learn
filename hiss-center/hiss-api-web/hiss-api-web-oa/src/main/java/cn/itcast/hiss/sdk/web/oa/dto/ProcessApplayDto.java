package cn.itcast.hiss.sdk.web.oa.dto;

import lombok.Data;

/*
 * @author miukoo
 * @description 个人申请流程的搜索项目
 * @date 2023/7/1 11:25
 * @version 1.0
 **/
@Data
public class ProcessApplayDto {
    String name;
    String category;
    String businessKey;

    String status;
    Long pageSize;
    Long pageNum;
}
