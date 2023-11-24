package ru.abyssone.employeeworktime.entity.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Вспомогательный класс для хранения информации о промежутке времени в пределах одного дня
 */
@Embeddable
@Getter
@Setter
public class TimePeriod {

    /**
     * Начало временного периода
     */
    private LocalTime startTime;

    /**
     * Конец временного периода
     */
    private LocalTime endTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimePeriod that = (TimePeriod) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @PrePersist
    private void correcting() {
        if (this.getStartTime().isAfter(this.getEndTime())) {
            LocalTime temp = this.startTime;
            this.startTime = endTime;
            this.endTime = temp;
        }
    }
}
