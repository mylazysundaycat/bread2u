package com.daegeon.bread2u.module.member.entity;

public enum Role {
    MEMBER(Authority.MEMBER),

    PRIVATE_MEMBER(Authority.PRIVATE_MEMBER),

    VIP_MEMBER(Authority.VIP_MEMBER),

    ADMIN(Authority.ADMIN);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String PRIVATE_MEMBER = "ROLE_PRIVATE_MEMBER";
        public static final String VIP_MEMBER = "ROLE_VIP_MEMBER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
    public static Role fromString(String role) {
        switch (role) {
            case "ROLE_MEMBER":
                return MEMBER;
            case "ROLE_PRIVATE_MEMBER":
                return PRIVATE_MEMBER;
            case "ROLE_VIP_MEMBER":
                return VIP_MEMBER;
            case "ROLE_ADMIN":
                return ADMIN;
        }

        return null;
    }
}
