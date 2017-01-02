package com.intelligentz.cashpal.rest_resources.ezcash;

import javax.ws.rs.Path;

/**
 * Created by lakshan on 1/2/17.
 */
@Path("m")
public class MCashRouter {

    @Path("/authenticate")
    public AuthenticateResource authenticate() {
        return new AuthenticateResource();
    }

}
