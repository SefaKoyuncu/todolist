package com.example.todolist;

public class Todo
{
    private String titleText;
    private String dateFrom;
    private String dateTo;
    private boolean expandable;

    public Todo() {
    }

    public Todo(String titleText, String dateFrom, String dateTo) {
        this.titleText = titleText;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
