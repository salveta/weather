package salva.perez.weather.domain.rest;



import android.content.Context;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import salva.perez.weather.domain.api.Api;


public class RetrofitAdapter {

    private static RetrofitAdapter sInstance = null;
    public Retrofit retrofit;

    public RetrofitAdapter() {
        super();
    }

    public static RetrofitAdapter getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RetrofitAdapter();
            sInstance.init(context);
        }
        return sInstance;
    }

    private void init(Context context) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(provideOkHTTPClient(context))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Retrofit getAdapter() {
        return this.retrofit;
    }


    private OkHttpClient provideOkHTTPClient(final Context context) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Set timeout
        httpClient.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        // Request interceptor
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");

                return chain.proceed(requestBuilder.build());
            }
        });

        // Logging interceptor
        httpClient.addInterceptor(logging);


        return httpClient.build();
    }
}
