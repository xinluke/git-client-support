package com.wangym.git.client.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * @author wangym
 * @version 创建时间：2019年3月18日 下午2:44:17
 */
@XmlRootElement(name = "ArrayOfTreeViewNode")
@NoArgsConstructor
public class TreeView {

    @XmlElement(name = "TreeViewNode")
    private List<TreeViewNode> list;

    public TreeView(List<TreeViewNode> list) {
        super();
        this.list = list;
    }

}
