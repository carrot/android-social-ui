package com.carrotcreative.socialui.widget;

import android.content.Context;
import android.text.method.MovementMethod;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.widget.TextView;

import com.carrotcreative.socialui.annotation.SocialActionIntDef;
import com.carrotcreative.socialui.util.SocialActionCallback;
import com.carrotcreative.socialui.util.SocialActionHandler;
import com.carrotcreative.socialui.util.SocialActionType;
import com.carrotcreative.socialui.util.SocialMovementMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialTextView extends TextView
{
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([A-Za-z0-9_-]+)");
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#([A-Za-z0-9_-]+)");

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
        // Do Nothing.  Here to allow subclassing
    }

    public void linkify(final SocialActionHandler actionHandler)
    {
        linkify(new SocialActionCallback()
        {
            @Override
            public void onMatch(@SocialActionIntDef int type, String value)
            {
                switch(type)
                {
                    case SocialActionType.EMAIL:
                        actionHandler.handleEmail(value);
                        break;
                    case SocialActionType.HASH_TAG:
                        actionHandler.handleHashtag(value);
                        break;
                    case SocialActionType.MENTION:
                        actionHandler.handleMention(value);
                        break;
                    case SocialActionType.URL:
                        actionHandler.handleUrl(value);
                        break;
                }
            }
        });
    }

    public void linkify(SocialActionCallback actionCallback)
    {
        Linkify.TransformFilter filter = new Linkify.TransformFilter()
        {
            public final String transformUrl(final Matcher match, String url)
            {
                return match.group();
            }
        };

        // emails
        Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter);

        // @mentions
        Linkify.addLinks(this, MENTION_PATTERN, SocialMovementMethod.SOCIAL_UI_MENTION_SCHEME, null, filter);

        // #hashtags
        Linkify.addLinks(this, HASHTAG_PATTERN, SocialMovementMethod.SOCIAL_UI_HASHTAG_SCHEME, null, filter);

        // Links
        Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter);

        // Hooking up the actionHandler
        MovementMethod movementMethod = null;
        if(actionCallback != null)
        {
            movementMethod = new SocialMovementMethod(actionCallback);
        }
        setMovementMethod(movementMethod);
    }

}
