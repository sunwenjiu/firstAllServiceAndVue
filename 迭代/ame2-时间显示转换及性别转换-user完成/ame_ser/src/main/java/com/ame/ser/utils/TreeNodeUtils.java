package com.ame.ser.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:树节点工具类
 * Date: 2018-05-19
 * Time: 11:11
 *
 * @author: ycbx
 */
public class TreeNodeUtils {
    private static final String PARENT_KEY_NAME = "parentId";

    private TreeNodeUtils() {
    }

    public static List<Map<String, Object>> getTreeResult(List<Map<String, Object>> list) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        for (Map<String, Object> map : list) {
            // 递归获取父节点下的子节点
            map.put("children", getChildrenNode(map.get("id").toString(), list));
            nodes.add(map);
        }
        return nodes;
    }

    private static List<Map<String, Object>> getChildrenNode(String parentId, List<Map<String, Object>> list) {
        List<Map<String, Object>> nodes = new ArrayList<>();
        for (Map<String, Object> map : list) {
            // 如果parentId为空，则该节点为父节点
            if (map.get(PARENT_KEY_NAME) == null || StringUtils.isBlank(map.get(PARENT_KEY_NAME).toString())) {
                continue;
            }
            if (StringUtils.equals(parentId, map.get(PARENT_KEY_NAME).toString())) {
                // 递归获取子节点下的子节点，即设置树控件中的children
                map.put("children", getChildrenNode(map.get("id").toString(), list));
                nodes.add(map);
            }
        }
        return nodes;
    }
}
