package dev.revature.clearcall.dto;

public class CategoryStats {

    private String categoryName;

    private Long callCount;

    private double averageDuration;

    public CategoryStats() {}

    public CategoryStats(String categoryName, Long callCount, double averageDuration) {
        this.categoryName = categoryName;
        this.callCount = callCount;
        this.averageDuration = averageDuration;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCallCount() {
        return callCount;
    }

    public void setCallCount(Long callCount) {
        this.callCount = callCount;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }
}
