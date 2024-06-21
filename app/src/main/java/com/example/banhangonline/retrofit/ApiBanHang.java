package com.example.banhangonline.retrofit;

import com.example.banhangonline.model.LoaiSpModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiBanHang {
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSP();
}
