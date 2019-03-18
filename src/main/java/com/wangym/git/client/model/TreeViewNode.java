package com.wangym.git.client.model;

import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangym
 * @version 创建时间：2019年3月18日 上午10:47:11
 */
@Getter
@ToString
public class TreeViewNode {

    @XmlElement(name = "Level")
    private int level;
    @XmlElement(name = "IsExpanded")
    private boolean isExpanded = false;
    @XmlElement(name = "IsLeaf")
    private boolean isLeaf;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "CanSelect")
    private boolean canSelect = true;
    @XmlElement(name = "HasError")
    private boolean hasError = false;
    @XmlElement(name = "Path")
    private String path;
    @XmlElement(name = "RepoType")
    private String repoType;
    @XmlElement(name = "Children")
    private TreeChildrenView children;

    public TreeViewNode(String name, List<TreeViewNode> children) {
        // 构造第一级的数据
        super();
        this.level = 0;
        this.isLeaf = false;
        this.name = name;
        this.path = null;
        this.repoType = null;
        this.children = new TreeChildrenView(children);
    }

    public TreeViewNode(String name, String path) {
        // 构造第二级的数据
        super();
        this.level = 1;
        this.isLeaf = true;
        this.name = name;
        this.path = path;
        this.repoType = "Git";
        // 叶子节点下面没有孩子节点了,设置一个空的列表
        this.children = new TreeChildrenView(new ArrayList<>());
    }

}
