package cn.itcast.hiss.api.client.form;

import cn.itcast.hiss.common.dtos.PageRequestDto;
import cn.itcast.hiss.common.enums.SearchFieldTypeEnum;
import lombok.*;

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
public class FormSearchField extends PageRequestDto {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 搜索值1
     */
    private String fieldValue;

    /**
     * 搜索值2
     */
    private String fieldValue2;

    /**
     * 搜索方式
     */
    private String type;

}
