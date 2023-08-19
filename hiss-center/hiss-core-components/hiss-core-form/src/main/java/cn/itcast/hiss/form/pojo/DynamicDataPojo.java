package cn.itcast.hiss.form.pojo;

import cn.itcast.hiss.form.dto.DynamicFieldDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DynamicDataPojo
 *
 * @author: wgl
 * @describe: 动态数据
 * @date: 2022/12/28 10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DynamicDataPojo extends BasePojo {

    private String tableName;

    private Long tableId;

    private Long modelId;

    private List<DynamicFieldDTO> fieldDTOList;

}