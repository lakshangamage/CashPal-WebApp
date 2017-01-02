package com.intelligentz.cashpal.rest_resources.ezcash;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Created by lakshan on 12/31/16.
 */
@Path("ez")
public class EzCashRouter {

    @Path("/authenticate")
    public AuthenticateResource authenticate() {
        return new AuthenticateResource();
    }

}
