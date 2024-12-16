package com.gang.Entity;

public enum User_Role {
	ROLE_CUSTOMER,ROLE_RESTAURANT_OWNER,ROLE_ADMIN;

	public static User_Role fromString(String role) {
        for (User_Role userRole : User_Role.values()) {
            if (userRole.name().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + role);
    }

}
