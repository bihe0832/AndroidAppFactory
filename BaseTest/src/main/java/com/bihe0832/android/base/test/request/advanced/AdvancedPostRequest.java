package com.bihe0832.android.base.test.request.advanced;


import com.bihe0832.android.lib.http.advanced.HttpAdvancedRequest;
import com.bihe0832.android.base.test.request.Constants;

import org.jetbrains.annotations.NotNull;

public class AdvancedPostRequest extends HttpAdvancedRequest<TestResponse> {

	private AdvancedResponseHandler<TestResponse> mAdvancedResponseHandlerHandler;

	public AdvancedPostRequest(String para, AdvancedResponseHandler<TestResponse> handler) {
        String encodedParam = Constants.PARA_PARA + HTTP_REQ_ENTITY_MERGE + para;
        try {
            this.data = encodedParam.getBytes("UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        this.mAdvancedResponseHandlerHandler = handler;
    }

    @NotNull
    @Override
    public AdvancedResponseHandler getAdvancedResponseHandler() {
        return mAdvancedResponseHandlerHandler;
    }

	@Override
	public String getUrl() {
        return getBaseUrl();
	}

    private String getBaseUrl(){
        return Constants.HTTP_DOMAIN + Constants.PATH_POST;
    }

}
