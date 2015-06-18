# Android Social UI

Android Social UI is a library of android widgets that work well for social apps.


## SocialTextView

A SocialTextView is a standard android TextView with callbacks to handle the onClicks of `#hashtags` `@mentions` and `https://weblinks.com/`.

You can use it in any XML file just as you would a normal TextView:

```xml
<com.carrotcreative.socialui.widget.SocialTextView
    android:id="@+id/social_text_example"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="This view handles #hashtags, @mentions, and https://weblinks.com/"
    />
```

The one last thing you have to do for the SocialTextView is to set it's SocialActionHandler.

```java
socialTextView = (SocialTextView) findViewById(R.id.social_text_example);
socialTextView.setSocialActionHandler(new SocialActionHandler() {
    @Override
    public void handleHashtag(String hashtag)
    {
        Toast.makeText(SignUpActivity.this, "Hashtag: " + hashtag, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleMention(String mention)
    {
        Toast.makeText(SignUpActivity.this, "Mention: " + mention, Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleUrl(String url)
    {
        Toast.makeText(SignUpActivity.this, "URL: " + url, Toast.LENGTH_LONG).show();
    }
});
```

After this is done, the SocialTextView will display highlighted/underlined hashtags, mentions, and weblinks that are clickable and will be handled by their appropriate method.

## License

Android Social UI is licensed under [MIT](LICENSE.md)
