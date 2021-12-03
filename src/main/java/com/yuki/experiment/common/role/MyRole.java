package com.yuki.experiment.common.role;

public enum MyRole implements IMyRole {
    ADMINISTRATOR(0,"管理员"),
    TEACHER(1,"教师"),
    STUDENT(2,"学生");

    private final Integer roleId;
    private final String roleName;

    MyRole(Integer roleId, String roleName){
        this.roleId=roleId;
        this.roleName=roleName;
    }

    @Override
    public Integer getRoleId() {
        return this.roleId;
    }

    @Override
    public String getRoleName() {
        return this.roleName;
    }
}
