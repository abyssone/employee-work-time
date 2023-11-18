package ru.abyssone.employeeworktime.repository;

import java.util.UUID;

public interface WorkTimeModelObject {
    UUID getId();
    String getTitle();
    String getClazz();
}
