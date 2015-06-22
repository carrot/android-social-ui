package com.carrotcreative.socialui.widget;

import android.content.Context;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.widget.TextView;

import com.carrotcreative.socialui.util.SocialActionHandler;
import com.carrotcreative.socialui.util.SocialMovementMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialTextView extends TextView {

    public SocialTextView(Context context)
    {
        this(context, null);
    }

    public SocialTextView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SocialTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyle)
    {
        linkify();
    }

    public void setSocialActionHandler(SocialActionHandler actionHandler)
    {
        SocialMovementMethod movementMethod = new SocialMovementMethod(actionHandler);
        setMovementMethod(movementMethod);
    }

    public void linkify()
    {
        Linkify.TransformFilter filter = new Linkify.TransformFilter() {
            public final String transformUrl(final Matcher match, String url) {
                return match.group();
            }
        };

        // @mentions
        Pattern mentionPattern = Pattern.compile("@([A-Za-z0-9_-]+)");
        Linkify.addLinks(this, mentionPattern, SocialMovementMethod.SOCIAL_UI_MENTION_SCHEME, null, filter);

        // #hashtags
        Pattern hashtagPattern = Pattern.compile("#([A-Za-z0-9_-]+)");
        Linkify.addLinks(this, hashtagPattern, SocialMovementMethod.SOCIAL_UI_HASHTAG_SCHEME, null, filter);

        // Links
        Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter);
    }

}
