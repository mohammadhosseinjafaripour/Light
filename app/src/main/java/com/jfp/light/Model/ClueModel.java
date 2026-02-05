package com.jfp.light.Model;

public class ClueModel {
    String id, title, answer, length, bounty, max_attempt, package_url;

    public ClueModel(String id, String title, String answer, String length, String bounty, String max_attempt, String package_url) {
        this.id = id;
        this.title = title;
        this.answer = answer;
        this.length = length;
        this.bounty = bounty;
        this.max_attempt = max_attempt;
        this.package_url = package_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBounty() {
        return bounty;
    }

    public void setBounty(String bounty) {
        this.bounty = bounty;
    }

    public String getMax_attempt() {
        return max_attempt;
    }

    public void setMax_attempt(String max_attempt) {
        this.max_attempt = max_attempt;
    }

    public String getPackage_url() {
        return package_url;
    }

    public void setPackage_url(String package_url) {
        this.package_url = package_url;
    }
}
