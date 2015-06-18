package com.carrotcreative.socialui.util;

public interface SocialActionHandler {
    public void handleHashtag(String hashtag);
    public void handleMention(String mention);
    public void handleUrl(String url);
}
