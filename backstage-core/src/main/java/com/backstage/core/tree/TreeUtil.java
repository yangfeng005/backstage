package com.backstage.core.tree;

import com.backstage.core.result.ServiceResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 基础树形结构数据工具类
 *
 * @author yangfeng
 */
public class TreeUtil {

    /**
     * 获取树结构数据
     *
     * @return
     */
    public static <T extends TreeBase> ServiceResult<List<T>> listTreeNodes(List<T> allList) {
        ServiceResult<List<T>> ret = new ServiceResult<>();
        List<T> parentList = new ArrayList<>();//根节点
        List<T> list = null;
        if (!CollectionUtils.isEmpty(allList)) {
            list = allList;
            for (T resource : allList) {
                if (StringUtils.isEmpty(resource.getParentCode())) {
                    parentList.add(resource);
                }
            }
        }
        //返回的树形节点数据
        List<T> treeNodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(parentList)) {
            for (T parent : parentList) {
                //递归查询所有子节点
                treeNodeList.add(recursiveTree(parent, list));
            }
        }
        ret.setData(treeNodeList);
        ret.setSucceed(true);
        return ret;
    }


    /**
     * 递归算法解析成树形结构
     */
    public static <T extends TreeBase> T recursiveTree(T parentNode, List<T> list) {
        List<T> childTreeNodes = getChildTree(parentNode.getCode(), list);
        if (!CollectionUtils.isEmpty(childTreeNodes)) {
            for (T child : childTreeNodes) {
                T n = recursiveTree(child, list);
                parentNode.getChildren().add(n);
            }
        }
        return parentNode;
    }

    /**
     * 根据父节点ID获取子节点
     */
    public static <T extends TreeBase> List<T> getChildTree(String parentId, List<T> list) {
        if (StringUtils.isEmpty(parentId)) {
            return null;
        }
        List<T> childNodes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (T resource : list) {
                if (parentId.equals(resource.getParentCode())) {
                    childNodes.add(resource);
                }
            }
        }
        return childNodes;
    }


    /**
     * 获取某个父节点下面的所有子节点
     *
     * @return
     */
    public static <T extends TreeBase> List<T> getAllChildNodes(T parentNode, List<T> list, List<T> childNodes) {
        for (T node : list) {
            //遍历出父id等于参数的id，add进子节点集合
            if (StringUtils.isNotEmpty(node.getParentCode()) && node.getParentCode().equals(parentNode.getCode())) {
                //递归遍历下一级
                getAllChildNodes(node, list, childNodes);
                childNodes.add(node);
            }
        }
        return childNodes;
    }


    /**
     * 根据子级查询所有父级资源
     *
     * @param allList    所有资源
     * @param code       子级code
     * @param parentList 最终返回的子级+所有父级的集合
     * @return
     */
    public static <T extends TreeBase> List<T> getAllParentListWithChild(List<T> allList, String code, List<T> parentList) {
        if (!CollectionUtils.isEmpty(allList)) {
            for (T resource : allList) {
                // 判断是否存在父节点
                if (resource.getCode().equals(code)) {
                    // 递归遍历上一级
                    getAllParentListWithChild(allList, resource.getParentCode(), parentList);
                    if (!parentList.contains(resource)) {
                        parentList.add(resource);
                    }
                }
            }
            return parentList;
        }
        return null;
    }


    /**
     * @param list
     * @param result
     * @param father
     * @return LinkedList<T>
     * @Description: 排序
     */
    public static <T extends TreeBase> LinkedList<T> toSort(List<T> list, LinkedList<T> result, String father) {
        List<T> temp = new ArrayList<>();
        // 最高层,临时存放
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParentCode().equals(father)) {
                temp.add(list.get(i));
            }
        }

        if (temp.size() < 1) {
            return result;
        } else {
            // 删除最高层
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getParentCode().equals(father)) {
                    list.remove(j);
                }
            }
            // 对最高层排序
            for (int i = 0; i < temp.size() - 1; i++) {
                for (int j = i + 1; j < temp.size(); j++) {
                    if (temp.get(i).getRank() > temp.get(j).getRank()) {
                        T object = temp.get(i);
                        temp.set(i, temp.get(j));
                        temp.set(j, object);
                    }
                }
            }
            // 递归
            for (int i = 0; i < temp.size(); i++) {
                result.add(temp.get(i));
                toSort(list, result, temp.get(i).getCode());
            }
            return result;
        }
    }


    /**
     * 数据排序并构造树结构
     *
     * @param resourceAOList
     * @return
     */
    public static <T extends TreeBase> ServiceResult<List<T>> sortTreeNodes(List<T> resourceAOList, String father) {
        LinkedList<T> result = new LinkedList<>();
        LinkedList<T> linkedList = TreeUtil.toSort(resourceAOList, result, father);
        return listTreeNodes(new ArrayList<>(linkedList));
    }

}
