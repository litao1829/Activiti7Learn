package cn.itcast.hiss.common.dtos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PageRequestDto {

    protected Long pageSize;
    protected Long current;

    public void checkParam() {
        if (this.current == null || this.current < 0) {
            setCurrent(1L);
        }
        if (this.pageSize == null || this.pageSize < 0 || this.pageSize > 100) {
            setPageSize(20L);
        }
    }
}
