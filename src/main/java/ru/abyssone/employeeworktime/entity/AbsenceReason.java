package ru.abyssone.employeeworktime.entity;

/**
 * Перечень причин отсутствия работника на рабочем месте в рабочее время
 */
public enum AbsenceReason {

    // Причина не указана
    NO_REASON,

    // Больничный
    SICK_LEAVE,

    // Рабочая командировка
    BUSINESS_TRIP
}
