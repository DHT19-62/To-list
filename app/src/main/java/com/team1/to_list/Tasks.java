package com.team1.to_list;

public class Tasks {
    String id;
    String title;
    String content;
    String deadline;
    Boolean isComplete;

    public Tasks(String title, String content, String deadline) {
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.isComplete = false;
    }

    public Tasks(String id, String title, String content, String deadline, Boolean isComplete) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.isComplete = isComplete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
