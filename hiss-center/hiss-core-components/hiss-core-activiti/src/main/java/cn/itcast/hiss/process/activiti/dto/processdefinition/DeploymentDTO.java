package cn.itcast.hiss.process.activiti.dto.processdefinition;

import lombok.Data;

import java.util.Date;

/**
 * DeploymentDTO
 *
 * @author: wgl
 * @describe: 流程部署的DTO
 * @date: 2022/12/28 10:10
 */
@Data
public class DeploymentDTO {

    private String id;

    private String name;

    private Date deploymentTime;

    private String category;

    private String key;

    private String tenantId;

    private Integer version;

    private String projectReleaseVersion;
}
