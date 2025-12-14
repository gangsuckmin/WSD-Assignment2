package kr.ac.jbnu.ksm.assignment2.user.entity;

public enum UserRole
{
    user, admin;

    public Role toSecurityRole()
    {
        return this == admin ? Role.ROLE_ADMIN : Role.ROLE_USER;
    }
}