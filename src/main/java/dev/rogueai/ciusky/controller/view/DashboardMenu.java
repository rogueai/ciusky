package dev.rogueai.ciusky.controller.view;

public class DashboardMenu {

    private String text;

    private String icon;

    private String link;

    public DashboardMenu() {

    }

    public DashboardMenu(String text, String icon, String link) {
        this.text = text;
        this.icon = icon;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
