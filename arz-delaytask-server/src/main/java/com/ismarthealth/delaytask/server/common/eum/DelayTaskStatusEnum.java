package com.ismarthealth.delaytask.server.common.eum;

import com.ismarthealth.osp.core.common.enums.FlowStatusEnum;

public enum DelayTaskStatusEnum {

    INIT("初始化", 1), DELAY_SUCCESS("任务延时成功", 2);

    private String name;
    private int index;

    // 构造方法
    private DelayTaskStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (FlowStatusEnum c : FlowStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name();
            }
        }
        return null;
    }


    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
