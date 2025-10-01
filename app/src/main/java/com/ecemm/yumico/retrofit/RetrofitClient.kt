package com.ecemm.yumico.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object{ //static
        fun getClient(baseUrl:String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl) //http://kasimadalan.pe.hu/ -> base url
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            //addConverterFactory sayesinde entity classlarımıza json formatını aktarıcaz
        }
    }
}