package com.tomato.reconciliation.support;

import com.tomato.reconciliation.task.internal.Task;

import java.util.*;

/**
 * 对账核心类
 * TODO 分片对账
 *
 * @author lizhifu
 * @since 2023/5/27
 */
public class ReconciliationSupport {
    private final List<Map<String, Object>> upList;

    private final List<Map<String, Object>> downList;
    private final Map<String, Map<String, Object>> downMap;

    private final Map<String, Map<String, Object>> upMap;
    private final Task task;

    public ReconciliationSupport(List<Map<String, Object>> upList, List<Map<String, Object>> downList, Task task) {
        this.upList = upList;
        this.downList = downList;
        this.task = task;
        this.downMap = downMap();
        this.upMap = new HashMap<>();
    }
    public ReconciliationSupport(List<Map<String, Object>> upList, Map<String, Map<String, Object>> downMap, Task task) {
        this.upList = upList;
        this.downList = new ArrayList<>();
        this.task = task;
        this.downMap = downMap;
        this.upMap = new HashMap<>();
    }

    public Map<String, Map<String, Object>> getDownMap() {
        return downMap;
    }

    public Map<String, Map<String, Object>> getUpMap() {
        return upMap;
    }

    /**
     * 对账
     */
    public void reconciliation() {
        for (int i = upList.size() - 1; i >= 0; i--){
            Map<String, Object> up = upList.remove(i);
            String sign = up.get(task.getTaskSign()).toString();
            Map<String, Object> down = downMap.get(sign);
            // 上游有下游没有，对账失败
            if (down == null) {
                upMap.put(sign, up);
                continue;
            }
            // 开始进行明细对账
            if (!reconciliationDetail(up, down)) {
                upMap.put(sign, up);
                continue;
            }
            // 对账成功
            downMap.remove(sign);
        }
    }
    private boolean reconciliationDetail(Map<String, Object> up,Map<String, Object> down) {
        Set<Map.Entry<String, Object>> upSet = up.entrySet();
        Set<Map.Entry<String, Object>> downSet = down.entrySet();
        if (upSet.size() != downSet.size()) {
            return false;
        }
        for (Map.Entry<String, Object> entry : upSet) {
            String key = entry.getKey();
            Object value1 = entry.getValue();
            Object value2 = down.get(key);
            if (!value1.equals(value2)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 对账数据清洗
     */
    private Map<String, Map<String, Object>> downMap() {
        Map<String, Map<String, Object>> dataMap = new HashMap<>();
        for (int i = downList.size() - 1; i >= 0; i--) {
            Map<String, Object> resultMap = downList.remove(i);
            dataMap.put(resultMap.get(task.getTaskSign()).toString(), resultMap);
        }
        return dataMap;
    }
}