package com.shubham.covid19notification;

public class NotificationData {

    String title;
    String link;

    public NotificationData(String title, String link)
    {
        this.title = title;
        this.link = link;
    }
    public String getTitle()
    {
        return title;
    }
    public String getLink()
    {
        return link;
    }

}
