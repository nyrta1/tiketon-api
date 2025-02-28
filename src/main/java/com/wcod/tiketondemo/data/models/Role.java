package com.wcod.tiketondemo.data.models;


import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    USER("Regular User", Set.of(
            "VIEW_EVENTS",          // View the event listings
            "BUY_TICKETS",          // Purchase tickets
            "MANAGE_PROFILE"        // Manage personal account
    )),

    CASHIER("Ticketing Cashier", Set.of(
            "SHOW_QR_TICKETS"       // Sell tickets at physical locations
    )),

    MANAGER("Event Manager", Set.of(
            "CREATE_EVENTS",        // Create new events
            "EDIT_EVENTS",          // Edit their events
            "DELETE_EVENTS",        // Delete inappropriate events
            "VIEW_SALES",           // View sales statistics
            "MANAGE_TICKETS",       // Manage tickets for their events
            "VIEW_ALL_EVENTS",      // View all events on the platform
            "GENERATE_REPORTS"      // Generate financial and sales reports
    )),

    ADMIN("Platform Administrator", Set.of(
            "VIEW_USERS",           // View user accounts
            "MANAGE_USERS",         // Manage user accounts (ban, assign roles)
            "VIEW_FINANCES",        // View financial reports
            "MANAGE_EVENTS",        // Manage all events
            "SYSTEM_SETTINGS"       // Adjust system-wide settings
    ));

    private final String description;
    private final Set<String> permissions;

    Role(String description, Set<String> permissions) {
        this.description = description;
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission -> (GrantedAuthority) permission::toString)
                .collect(Collectors.toSet());
    }
}

