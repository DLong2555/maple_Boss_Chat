package project.maple.domain;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String role;

    MemberRole(String role) {
        this.role = role;
    }
}
