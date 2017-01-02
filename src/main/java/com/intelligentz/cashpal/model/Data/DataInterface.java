package com.intelligentz.cashpal.model.Data;

import com.intelligentz.cashpal.exception.IdeabizException;
import com.intelligentz.cashpal.model.ApplicationInformation;

/**
 * Created by Malinda on 10/19/2015.
 *
 * Impliment this interface depend on database connection
 */
public interface DataInterface {
    public ApplicationInformation getAppInfo(String applicationId);
    public ApplicationInformation loadPropFile() throws IdeabizException;
    boolean saveTokenFile(ApplicationInformation applicationInformation);
}
