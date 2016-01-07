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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialTextView extends TextView
{
    private static final Pattern MENTION_PATTERN = Pattern.compile("@([A-Za-z0-9_-]+)");
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#([A-Za-z0-9_-]+)");
    private Map<Pattern, Integer> mCustomPatterns = new HashMap<>();

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

        //Add Custom patterns
        for(Pattern pattern : mCustomPatterns.keySet())
        {
            int type = mCustomPatterns.get(pattern);
            String scheme = getScheme(String.valueOf(type));
            Linkify.addLinks(this, pattern, scheme, null, filter);
        }

        // emails
        Linkify.addLinks(this, Patterns.EMAIL_ADDRESS, null, null, filter);

        // @mentions
        Linkify.addLinks(this, MENTION_PATTERN, SocialMovementMethod.SOCIAL_UI_MENTION_SCHEME, null, filter);

        // #hashtags
        Linkify.addLinks(this, HASHTAG_PATTERN, SocialMovementMethod.SOCIAL_UI_HASHTAG_SCHEME, null, filter);

        // Links
        Linkify.addLinks(this, Patterns.WEB_URL, null, null, filter);

        // Hooking up the actionHandler
        SocialMovementMethod movementMethod = null;
        if(actionCallback != null)
        {
            movementMethod = new SocialMovementMethod(actionCallback);
        }

        //Add the custom schemes to the movement method
        if(movementMethod != null)
        {
            for(Pattern pattern : mCustomPatterns.keySet())
            {
                int type = mCustomPatterns.get(pattern);
                movementMethod.addCustomScheme(getScheme(String.valueOf(type)), type);
            }
        }

        //set the movement method for the text view
        setMovementMethod(movementMethod);
    }

    public void registerPattern(Pattern pattern, @SocialActionIntDef int type)
    {
        mCustomPatterns.put(pattern, type);
    }

    public void deregisterPattern(Pattern pattern)
    {
        if(mCustomPatterns.containsKey(pattern))
        {
            removePatternFromMovementMethod(mCustomPatterns.get(pattern));
            mCustomPatterns.remove(pattern);
        }
    }

    private void removePatternFromMovementMethod(int patternType)
    {
        MovementMethod movementMethod = getMovementMethod();
        if(movementMethod != null && movementMethod instanceof SocialMovementMethod)
        {
            SocialMovementMethod method = (SocialMovementMethod) movementMethod;
            method.removeCustomScheme(getScheme(String.valueOf(patternType)));
        }
    }

    private String getScheme(String scheme)
    {
        return SocialMovementMethod.SOCIAL_UI_BASE_SCHEME + "/" + scheme;
    }
}
