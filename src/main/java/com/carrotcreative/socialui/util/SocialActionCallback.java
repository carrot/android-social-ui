package com.carrotcreative.socialui.util;

import com.carrotcreative.socialui.annotation.SocialActionIntDef;

/**
 * Created by roide on 1/6/16.
 */
public interface SocialActionCallback
{
    void onMatch(@SocialActionIntDef int type, String value);
}
