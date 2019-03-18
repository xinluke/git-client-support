package com.wangym.git.client.model;

import lombok.Getter;

/**
 * @author wangym
 * @version 创建时间：2019年3月18日 下午1:28:38
 */
@Getter
public class RepositoryInfo {

    // 完整的全路径
    private String path;
    // 推断出所属的文件夹
    private String group;

    public RepositoryInfo(String path) {
        super();
        this.path = path;
        // 切割出上级目录
        this.group = path.substring(0, path.lastIndexOf("\\"));
    }

}
