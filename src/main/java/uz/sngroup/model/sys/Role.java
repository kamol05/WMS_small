package uz.sngroup.model.sys;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    BARKOD("ROLE_BARKOD");


    private String name;

    Role(String name) {
        this.name = name;
    }
}
