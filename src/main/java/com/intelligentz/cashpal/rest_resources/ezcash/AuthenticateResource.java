package com.intelligentz.cashpal.rest_resources.ezcash;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.cashpal.constants.IdeaBizConstants;
import com.intelligentz.cashpal.exception.IdeabizException;
import com.intelligentz.cashpal.handler.OTPHandler;
import com.intelligentz.cashpal.handler.SMSHandler;
import com.intelligentz.cashpal.handler.SubscriptionHandler;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by lakshan on 11/12/16.
 */

public class AuthenticateResource {

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("otp/request")
    public Response get(String jsonRequest) {
        JsonObject jsonObject = new JsonParser().parse(jsonRequest).getAsJsonObject();
        String mobile = jsonObject.get("agent_mobile").getAsString();
        String result = "";
        JsonArray numbers = new JsonArray();
        numbers.add(IdeaBizConstants.MSISDN_PREFIX+mobile);
        String message = "CashPal Pass Code: " + OTPHandler.addNewOTPToCache(numbers.get(0).getAsString());
        try {
            if (new SubscriptionHandler().suscribe(IdeaBizConstants.MSISDN_PREFIX+mobile)){
                result = new SMSHandler().sendSMS(numbers,message);
            }else {
                throw new IdeabizException("User Could Not be Subscribed");
            }

        } catch (IdeabizException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("otp/validate")
    public Response validate(String jsonRequest) {
        JsonObject jsonObject = new JsonParser().parse(jsonRequest).getAsJsonObject();
        String mobile = jsonObject.get("agent_mobile").getAsString();
        mobile = IdeaBizConstants.MSISDN_PREFIX+mobile;
        String otp = jsonObject.get("otp").getAsString();
        String cached_otp = OTPHandler.getFromOTPCache(mobile);
        boolean isAuthenticated = false;
        if (cached_otp != null && cached_otp.equals(otp)) {
            isAuthenticated = true;
        }
        JsonObject result = new JsonObject();
        result.addProperty("authenticated" , isAuthenticated);
        return Response.status(Response.Status.OK).entity(result.toString()).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/status")
    public Response status(String jsonRequest) {
        return Response.status(Response.Status.OK).build();
    }

}
