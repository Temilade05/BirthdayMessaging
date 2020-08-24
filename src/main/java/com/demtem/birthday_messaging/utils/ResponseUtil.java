package com.demtem.birthday_messaging.utils;

import com.demtem.birthday_messaging.models.enums.ResponseCode;
import com.demtem.birthday_messaging.models.responses.Response;

import java.util.List;

public class ResponseUtil<T> {

    public Response<T> successfulResponse(List<T> list){
        Response<T> response = new Response<>();
        response.setResponseCode(ResponseCode.SuccessFul.getValue());
        response.setResponseMessage("Successful");
        response.setStatusCode("200");
        response.setModels(list);
        return response;
    }
}
