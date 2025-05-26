package ru.nsu.martynov.model;

public class Settings {
    private double latePenaltyPerDay = 0.1; // Снижение на k% от исходного за каждый день просрочки
    private boolean autoCheckEnabled = true; // Использовать ли автоматическую проверку
    private int reviewTimeoutDays = 3; // Сколько дней на проверку
    private String checkstyleConfigFile; // Путь к xml с правилом Checkstyle, например "lib/google_checks.xml" или "lib/my_custom_style.xml"

    public Settings() {
    }

    public Settings(double latePenaltyPerDay, boolean autoCheckEnabled, int reviewTimeoutDays, String checkstyleConfigFile) {
        this.latePenaltyPerDay = latePenaltyPerDay;
        this.autoCheckEnabled = autoCheckEnabled;
        this.reviewTimeoutDays = reviewTimeoutDays;
        this.checkstyleConfigFile = checkstyleConfigFile;
    }

    public double getLatePenaltyPerDay() {
        return latePenaltyPerDay;
    }

    public void setLatePenaltyPerDay(double latePenaltyPerDay) {
        this.latePenaltyPerDay = latePenaltyPerDay;
    }

    public boolean isAutoCheckEnabled() {
        return autoCheckEnabled;
    }

    public void setAutoCheckEnabled(boolean autoCheckEnabled) {
        this.autoCheckEnabled = autoCheckEnabled;
    }

    public int getReviewTimeoutDays() {
        return reviewTimeoutDays;
    }

    public void setReviewTimeoutDays(int reviewTimeoutDays) {
        this.reviewTimeoutDays = reviewTimeoutDays;
    }

    public String getCheckstyleConfigFile() {
        return checkstyleConfigFile;
    }

    public void setCheckstyleConfigFile(String checkstyleConfigFile) {
        this.checkstyleConfigFile = checkstyleConfigFile;
    }
}
