package kr.ac.jbnu.ksm.assignment2.user.entity;

public enum Role
{
    ROLE_USER, ROLE_ADMIN;

    public static Role fromDb(String v)
    {
        if (v == null) return ROLE_USER;
        return switch (v.toLowerCase())
        {
            case "admin" -> ROLE_ADMIN;
            default -> ROLE_USER;
        };
    }

    public String toDb()
    {
        return this == ROLE_ADMIN ? "admin" : "user";
    }
}