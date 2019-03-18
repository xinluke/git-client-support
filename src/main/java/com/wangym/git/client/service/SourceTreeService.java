package com.wangym.git.client.service;

import com.wangym.git.client.model.RepositoryInfo;
import com.wangym.git.client.model.TreeView;
import com.wangym.git.client.model.TreeViewNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wangym
 * @version 创建时间：2019年3月18日 上午10:43:27
 */
@Service
@Slf4j
public class SourceTreeService {

    private static String TARGET_PATH = "C:\\Users\\Administrator\\AppData\\Local\\Atlassian\\SourceTree\\bookmarks.xml";

    public void handle() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 以当前jar为基点，搜索下面全部的文件
        Resource[] resources = resolver.getResources("file:./**/.git");
        List<RepositoryInfo> list = Stream.of(resources)
                .map(it -> {
                    RepositoryInfo info;
                    try {
                        String absolutePath = it.getFile().getAbsolutePath();
                        // 把最后的"\.git"去除，得到git仓库的入口地址
                        absolutePath = absolutePath.replaceAll("\\\\.git", "");
                        info = new RepositoryInfo(absolutePath);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return info;
                })
                .collect(Collectors.toList());
        for (Resource resource : resources) {
            log.info("name:{}", resource.getURL());
        }
        Map<String, List<RepositoryInfo>> group = list.stream()
                .collect(Collectors.groupingBy(RepositoryInfo::getGroup));
        Set<Entry<String, List<RepositoryInfo>>> set = group.entrySet();
        List<TreeViewNode> result = new ArrayList<>();
        for (Entry<String, List<RepositoryInfo>> entry : set) {
            String val = getCurrentName(entry.getKey());
            List<TreeViewNode> children = entry.getValue().stream()
                    .map(it -> {
                        String path = it.getPath();
                        // 最后一级目录名当做节点名称
                        String name = getCurrentName(path);
                        TreeViewNode po = new TreeViewNode(name, path);
                        return po;
                    })
                    .collect(Collectors.toList());
            result.add(new TreeViewNode(val, children));
        }
        String xml = convertToXml(new TreeView(result));
        xml = xml.replaceAll("<TreeViewNodeChildren>", "<TreeViewNode xsi:type=\"BookmarkNode\">");
        xml = xml.replaceAll("</TreeViewNodeChildren>", "</TreeViewNode>");
        xml = xml.replaceAll("<TreeViewNode>", "<TreeViewNode xsi:type=\"BookmarkFolderNode\">");
        FileCopyUtils.copy(xml.getBytes(), new File(TARGET_PATH));
    }

    private String getCurrentName(String path) {
        // 最后一级目录名当做节点名称
        String name = path.substring(path.lastIndexOf("\\") + 1);
        return name;
    }

    public static String convertToXml(Object obj) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
