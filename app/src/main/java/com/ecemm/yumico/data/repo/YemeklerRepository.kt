package com.ecemm.yumico.data.repo
import com.ecemm.yumico.data.datasource.YemeklerDataSource
import com.ecemm.yumico.data.entity.Yemekler

// todo: DataSource ile bağlama işlemi , İlk amaç data source'a erişmektir ve yönetmektir
class YemeklerRepository(var yemeklerDataSource : YemeklerDataSource) {

    //TODO- Tüm yemekleri getir
    suspend fun yemekleriGetir() : List<Yemekler>{
        return yemeklerDataSource.yemekleriGetir()
    }

    //TODO- Yemek ara
    suspend fun yemekAra(aramaKelimesi:String,tumYemekler:List<Yemekler>) : List<Yemekler>{
        return yemeklerDataSource.yemekAra(aramaKelimesi,tumYemekler)
    }

    //TODO- Sepete yemek ekle
    suspend fun sepeteYemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        return yemeklerDataSource.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
    }
}