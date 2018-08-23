package ru.alxndrzuev.iso20022.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
