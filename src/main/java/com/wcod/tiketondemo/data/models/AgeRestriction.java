package com.wcod.tiketondemo.data.models;

public enum AgeRestriction {
    AGE_0_PLUS("0+ Без возрастных ограничений"),
    AGE_6_PLUS("6+ (Рекомендуется родительский контроль)"),
    AGE_12_PLUS("12+ (Лёгкий контент, требуется родительское усмотрение)"),
    AGE_16_PLUS("16+ (Для подростков и старше, возможны взрослые темы)"),
    AGE_18_PLUS("18+ (Только для взрослых, может содержать откровенный контент)"),
    AGE_21_PLUS("21+ (Ограничение по возрасту, например, алкогольные мероприятия)");

    private final String description;

    AgeRestriction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
