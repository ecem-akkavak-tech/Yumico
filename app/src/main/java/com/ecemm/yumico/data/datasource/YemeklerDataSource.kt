package com.ecemm.yumico.data.datasource

import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var yemeklerDao : YemeklerDao) {

    //TODO- Tüm yemekleri getir
    suspend fun yemekleriGetir() : List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yemeklerDao.yemekleriYukle().yemekler
    }

    //TODO- Yemek ara
    suspend fun yemekAra(aramaKelimesi:String,tumYemekler:List<Yemekler>):List<Yemekler> = withContext(Dispatchers.Default){
      //no api call for this process
      tumYemekler.filter {
          it.yemek_adi.contains(aramaKelimesi,ignoreCase = true)
      }
    }
}