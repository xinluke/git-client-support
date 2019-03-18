package com.wangym.git.client.model;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

import java.util.List;

/**
 * @author wangym
 * @version 创建时间：2019年3月18日 下午3:10:58
 */
@Getter
public class TreeChildrenView {

    @XmlElement(name = "TreeViewNodeChildren")
    private List<TreeViewNode> children;

    public TreeChildrenView(List<TreeViewNode> children) {
        super();
        this.children = children;
    }

}
