package com.intelligentz.cashpal.handler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.intelligentz.cashpal.exception.IdeabizException;
import com.intelligentz.cashpal.model.Authenticator;
import com.intelligentz.cashpal.model.Data.DataImp;
import com.intelligentz.cashpal.model.Data.DataInterface;
import com.intelligentz.cashpal.model.RequestHandller;
import com.intelligentz.cashpal.model.RequestMethod;

public class IdeaBizAPIHandler {

    DataInterface df;
    public IdeaBizAPIHandler(){
        this.df=new DataImp();
    }

    public   String sendAPICall(String url, RequestMethod requestMethod, String body, String urlPara, String contenctType, String accpet, String authorizationType) throws IdeabizException {
        RequestHandller requestHandller = new RequestHandller();
        Authenticator authenticator = new Authenticator(df);
        String results = "{}";
        JsonParser parser = new JsonParser();
     switch (requestMethod){
         case GET:
             try {
                 results = requestHandller.getHTTP(url, authenticator.getAccessToken(),contenctType,accpet, authorizationType);
                 JsonObject tokenOut = (JsonObject)parser.parse(results);
                 return results;
             }
             catch(Exception e){
                 if(results.contains("Access Token")){
                     authenticator.renewToken();
                     results = sendAPICall(url,requestMethod,body,urlPara, contenctType, accpet, authorizationType);
                 }else {
                     throw new IdeabizException("Error Accessing Ideabiz APIs: "+e.getMessage());
                 }
             }
             break;
         case POST:

            try {

                results = requestHandller.postHTTP(url, urlPara, authenticator.getAccessToken(), body, contenctType, accpet, authorizationType);
                JsonObject tokenOut = (JsonObject)parser.parse(results );
                return results;
            }
            catch(Exception e){
                    if(results.contains("Access Token") || results.contains("Access failure")){
                        authenticator.renewToken();
                        results = sendAPICall(url,requestMethod,body,urlPara, contenctType, accpet, authorizationType);
                    }else {
                        throw new IdeabizException("Error Accessing Ideabiz APIs: "+e.getMessage());
                    }
                }
             break;
     }
        return results;
    }
}
