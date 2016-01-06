package com.carrotcreative.socialui.util;

import com.carrotcreative.socialui.annotation.SocialActionDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by roide on 1/6/16.
 */
public interface SocialActionType
{
    int HASH_TAG = 0x1;
    int MENTION = 0x2;
    int EMAIL = 0x3;
    int URL = 0x4;
}
