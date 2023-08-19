package cn.itcast.hiss.api.client.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PageInfo
 *
 * @author: wgl
 * @describe: 分页参数
 * @date: 2022/12/28 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {

    private Integer pageNum;

    private Integer pageSize;
}
