package cn.itcast.hiss.form.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DynamicFieldDTO
 *
 * @author: wgl
 * @describe: TODO
 * @date: 2022/12/28 10:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DynamicFieldDTO {
    /**
     * 字段物理名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;
}
