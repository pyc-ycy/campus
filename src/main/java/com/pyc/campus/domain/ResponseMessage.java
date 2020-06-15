//IntelliJ IDEA
//campus
//ResponseMessage
//2020/6/15
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

public class ResponseMessage {
    private final String responseMessageContent;

    public ResponseMessage(String responseMessageContent){
        this.responseMessageContent=responseMessageContent;
    }

    public String getResponseMessageContent() {
        return responseMessageContent;
    }
}
