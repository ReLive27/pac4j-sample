package com.relive.pac4j.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ReLive
 * @Date 2021/7/31-17:40
 */
public class SecurityConstraint {

    private String clientName;
    private SecurityConstraint.RoleAndPermission roleAndPermissions = new SecurityConstraint.RoleAndPermission();

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public RoleAndPermission getRoleAndPermissions() {
        return roleAndPermissions;
    }

    public void setRoleAndPermissions(RoleAndPermission roleAndPermissions) {
        this.roleAndPermissions = roleAndPermissions;
    }

    public static class RoleAndPermission {
        private Map<String, String> roleMapping = new HashMap<>();
        private Map<String, String> permissionMapping = new HashMap<>();

        public Map<String, String> getRoleMapping() {
            return roleMapping;
        }

        public void setRoleMapping(Map<String, String> roleMapping) {
            this.roleMapping = roleMapping;
        }

        public Map<String, String> getPermissionMapping() {
            return permissionMapping;
        }

        public void setPermissionMapping(Map<String, String> permissionMapping) {
            this.permissionMapping = permissionMapping;
        }
    }
}
