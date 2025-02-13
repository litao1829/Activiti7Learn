package cn.itcast.hiss.api.client.task;

import lombok.Data;

@Data
public class PracticeTask {
    private String name;

    private String category;

    private String modelId;

    private String key;

    private String content;

    private String tenant;

    private String description;

    private String icon;

    private String configJson;

    private String _tenant;

}
