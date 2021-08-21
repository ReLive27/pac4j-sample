package com.relive.pac4j.authorizer;

import com.relive.pac4j.config.SecurityConstraint;
import org.apache.commons.collections4.CollectionUtils;
import org.pac4j.core.authorization.generator.AuthorizationGenerator;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.UserProfile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 角色权限映射，全局考虑SpringSecurityHelper
 *
 * @Author ReLive
 * @Date 2021/4/29-20:21
 */
public class RolesPermissionsMappingAuthorizationGenerator implements AuthorizationGenerator {
    private List<SecurityConstraint> securityConstraints;

    public RolesPermissionsMappingAuthorizationGenerator(List<SecurityConstraint> securityConstraints) {
        this.securityConstraints = securityConstraints;
    }

    @Override
    public Optional<UserProfile> generate(WebContext webContext, UserProfile userProfile) {
        if (CollectionUtils.isNotEmpty(securityConstraints)) {
            for (SecurityConstraint securityConstraint : securityConstraints) {
                if (userProfile.getClientName().equalsIgnoreCase(securityConstraint.getClientName())) {
                    Map<String, String> roleMapping = securityConstraint.getRoleAndPermissions().getRoleMapping();
                    Set<String> roles = getRoles(userProfile);
                    if (CollectionUtils.isNotEmpty(roles)) {
                        roles.stream().forEach(r -> userProfile.addRole(roleMapping.get(r)));
                    }
                    Map<String, String> permissionMapping = securityConstraint.getRoleAndPermissions().getPermissionMapping();
                    Set<String> permissions = getPermissions(userProfile);
                    if (CollectionUtils.isNotEmpty(permissions)) {
                        permissions.stream().forEach(p -> userProfile.addPermission(permissionMapping.get(p)));
                    }
                    break;
                }
            }
        }
        return Optional.of(userProfile);
    }

    private Set<String> getRoles(UserProfile userProfile) {
        Set<String> roles = userProfile.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            roles = (Set<String>) userProfile.getAttribute("roles");
        }
        return roles;
    }

    private Set<String> getPermissions(UserProfile userProfile) {
        Set<String> permissions = userProfile.getPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            permissions = (Set<String>) userProfile.getAttribute("permissions");
        }
        return permissions;
    }
}
