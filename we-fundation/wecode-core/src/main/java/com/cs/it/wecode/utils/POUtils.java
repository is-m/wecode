package com.cs.it.wecode.utils;

import com.cs.it.wecode.base.po.BasePO;

import java.util.Date;
import java.util.UUID;

public class POUtils {

    public static void setOperation(BasePO po, boolean isCreate) {
        String userId = "";
        Date now = new Date();
        if (isCreate) {
            po.setId(UUID.randomUUID().toString());
            po.setCreateBy(userId);
            po.setCreationDate(now);
        }
        po.setLastUpdateBy(userId);
        po.setLastUpdatedDate(now);
        po.setVersion(po.getVersion() + 1);
    }

}
