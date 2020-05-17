package com.neves.external.api;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.log4j.*;
import org.apache.log4j.spi.LoggerFactory;

import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class StarWarsApiExternal {
		
	// USER_AGENT utilizado p/ envio das consultas. Utilizado de referência o: developer.mozzila.org
	private static final String USER_AGENT = "Mozilla/5.0";
	
	// URL BASE para consultas na api do Star Wars
	private static final String BASE_URL = "https://swapi.dev/api/";
	
	// Variáveis utilizadas p/ instânciar as requisições enviadas.
    private 	   StarWars			   swService;
    private static StarWarsApiExternal instance;
    
    private StarWarsApiExternal() {
    	OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new UserAgentInterceptor(USER_AGENT))
                .addInterceptor(new RequestLoggingInterceptor());
		
		Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        swService = retrofit.create(StarWars.class);
    }

    public static StarWars getApi() {
        if (instance == null) {
            instance = new StarWarsApiExternal();
        }
        return instance.swService;
    }

    private static class RequestLoggingInterceptor implements Interceptor {
    	private Logger logger = Logger.getLogger(StarWarsApiExternal.class.getName());
    	
    	@Override
        public Response intercept(Chain chain) throws IOException {
        	Request request = chain.request();
        	
        	System.out.println("HTTP Request : {}" + request);
        	System.out.println("HTTP Request headers : {}" + request.headers());

            return chain.proceed(request);
        }
    }

    private static class UserAgentInterceptor implements Interceptor {
        private final String userAgent;

        public UserAgentInterceptor(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request requestWithUserAgent = request.newBuilder()
                    .header("User-Agent", userAgent)
                    .build();

            return chain.proceed(requestWithUserAgent);
        }
    }

}