package com.sol.itunes.model;

import okhttp3.Response;

public class ResultResponse <T> {
    private T response;
    private Response responseBody;

    public ResultResponse(T response, Response responseBody){
        this.response = response;
        this.responseBody = responseBody;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Response getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Response responseBody) {
        this.responseBody = responseBody;
    }
}
