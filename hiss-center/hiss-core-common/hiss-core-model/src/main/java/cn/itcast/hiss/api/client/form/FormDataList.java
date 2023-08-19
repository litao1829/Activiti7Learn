package cn.itcast.hiss.api.client.form;

import cn.itcast.hiss.common.dtos.PageRequestDto;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 *
 * @author: miukoo
 * @describe: 表单列表数据
 * @date: 2022/12/28 10:10
 */
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FormDataList extends PageRequestDto {

    /**
     * 数据id
     */
    private String tableId;

    /**
     * 表单ID:暂存时使用
     */
    private String formId;

    /**
     * 数据ID
     */
    private String dataId;

    /**
     * 搜索的字段
     */
    private List<FormSearchField> search;

}
