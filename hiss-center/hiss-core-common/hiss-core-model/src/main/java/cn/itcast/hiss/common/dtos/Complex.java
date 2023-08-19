package cn.itcast.hiss.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * @author miukoo
 * @description 复合对象
 * @date 2023/5/28 11:29
 * @version 1.0
 **/
@Data
@AllArgsConstructor
public class Complex<T,K> {
    T first;
    K second;
}
