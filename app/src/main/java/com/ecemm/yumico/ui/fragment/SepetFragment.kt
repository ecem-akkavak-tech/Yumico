package com.ecemm.yumico.ui.fragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentSepetBinding
import com.ecemm.yumico.ui.adapter.SepetAdapter
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel
import com.ecemm.yumico.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {

    private lateinit var binding: FragmentSepetBinding

    // **TODO-1-:  ATIVITY VIEW MODEL BAĞLAMA-> ActivityViewModels ile shared olarak çalıştırır ve liste 0lanmadan güncel veriler korunur
    private val viewModel: SepetViewModel by activityViewModels() //activityViewModels: ViewModel’i fragmentlar arasında ortak(shared) yapar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet , container, false)

        
        binding.imageViewCloseSepet.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.anasayfaFragment)
        }

        //TODO: Veriyi alan taraftayız,bu yüzden **args**  && xml ve fragment tarafındaki nesneler eşleşir **/
        val bundle:SepetFragmentArgs by navArgs()
        val gelenYemek = bundle.yemekSepeti


        //TODO- Eğer aynı yemek zaten varsa sadece yemek adetini güncelle

        gelenYemek?.let { yemek ->
         /* LiveData’daki mevcut listeyi al.
            Eğer liste varsa onu değiştirilebilir (tuMutableList) hâle getir, yoksa boş bir değiştirilebilir liste (mutableListOf) oluştur.
         */
          val mevcutSepetList = viewModel.sepetListesi.value?.toMutableList() ?: mutableListOf()
          val mevcutYemek = mevcutSepetList.find{it.yemek_adi == yemek.yemek_adi}

          if (mevcutYemek != null) {
              // Eğer yemek zaten varsa adeti artır
              mevcutYemek.yemek_siparis_adet += gelenYemek.yemek_siparis_adet
          }else {
              mevcutSepetList.add(yemek)
          }
            //livedata güncellenir
            viewModel.sepetListesi.value = mevcutSepetList //RecyclerView otomatik yenilenir
        }



        //TODO- adapter & recyclerview arası veri aktarma işlemi & liste gönderimi
        //TODO-RecyclerView’i önce boş bir adapter ile başlat, sonra LiveData geldiğinde veriyi güncelle.
        val sepetAdapter = SepetAdapter(requireContext(), listOf(), viewModel)
        binding.recyclerViewSepet.adapter = sepetAdapter

        viewModel.sepetListesi.observe(viewLifecycleOwner){ list ->
            sepetAdapter.sepettekiYemeklerListesi = list
            sepetAdapter.notifyDataSetChanged()
            binding.textViewSepetToplam.text = "₺" + viewModel.toplamSepetHesapla() //sepetten ürün silinse de observe sayesinde güncel hesap yapar
        }


        //sepeti onayla - Alert Dialog
        binding.buttonSepetiOnayla.setOnClickListener {
            binding.buttonSepetiOnayla.setOnClickListener {
                val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                builder.setTitle("❓ Sipariş Onayı")
                builder.setMessage("Sepetinizdeki yemekleri onaylıyor musunuz?\nToplam: ₺${viewModel.toplamSepetHesapla()}")

                builder.setPositiveButton("EVET") { dialog, _ ->
                    dialog.dismiss()
                    val confirmDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                        .setMessage("\uD83C\uDF7D\uFE0F Siparişiniz başarıyla alındı! ")
                        .setPositiveButton("TAMAM") { itDialog, _ -> itDialog.dismiss() }
                        .create()

                    confirmDialog.show()
                    confirmDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    confirmDialog.window?.setBackgroundDrawable(
                        ColorDrawable(ContextCompat.getColor(requireContext(), R.color.purp))
                    )
                }

                builder.setNegativeButton("HAYIR") { dialog, _ -> dialog.dismiss() }
                val dialog = builder.create()
                dialog.show()

                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
                    ?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE)
                    ?.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                dialog.window?.setBackgroundDrawable(
                    ColorDrawable(ContextCompat.getColor(requireContext(), R.color.purp))
                )
            }
        }
            return binding.root
    }



// TODO- Sepetteki Tüm yemekleri  getir (viewModel üstte livedatayı güncellediği için buna gerek yok)
//    fun getSepettekiYemek(){
//        val bundle:SepetFragmentArgs by navArgs()
//        val kullaniciAdi= bundle.yemekSepeti?.kullanici_adi ?: "Ecem Akkavak"
//        viewModel.sepettekiYemekleriGetir(kullaniciAdi)
//    }


}