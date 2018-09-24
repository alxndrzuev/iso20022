package ru.alxndrzuev.iso20022.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {
    private String id;
    private String name;
    private String owner;

    private String snils;
    private String inn;
    private String ogrn;
    private String organizationName;
    private String subjectName;

    private boolean isNeedPassword;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
