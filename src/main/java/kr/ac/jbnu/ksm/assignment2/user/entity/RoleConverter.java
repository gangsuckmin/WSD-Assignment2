package kr.ac.jbnu.ksm.assignment2.user.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        if (attribute == null) return "user";
        return attribute.toDb(); // 'user' or 'admin'
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        return Role.fromDb(dbData); // 'user'/'admin' -> ROLE_USER/ROLE_ADMIN
    }
}
