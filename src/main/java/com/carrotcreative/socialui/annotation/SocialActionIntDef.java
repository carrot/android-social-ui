package com.carrotcreative.socialui.annotation;

import com.carrotcreative.socialui.util.SocialActionType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by roide on 1/6/16.
 */
@SocialActionDef(
        {
                SocialActionType.HASH_TAG,
                SocialActionType.MENTION,
                SocialActionType.EMAIL,
                SocialActionType.URL
        })
@Retention(RetentionPolicy.SOURCE)
public @interface SocialActionIntDef
{
}
