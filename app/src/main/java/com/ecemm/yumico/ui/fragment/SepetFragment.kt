package com.ecemm.yumico.ui.fragment
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.Visibility
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentSepetBinding
import com.ecemm.yumico.ui.adapter.SepetAdapter
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel
import com.ecemm.yumico.ui.viewmodel.SepetViewModel
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {

    private lateinit var binding: FragmentSepetBinding

    // **TODO-1-:  ATIVITY VIEW MODEL BAĞLAMA-> ActivityViewModels ile shared olarak çalıştırır ve liste 0lanmadan güncel veriler korunur
    private val viewModel: SepetViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet , container, false)


        binding.buttonCloseSepet.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.anasayfaFragment)
        }

        //TODO: Veriyi alan taraftayız,bu yüzden *args*  && xml ve fragment tarafındaki nesneler eşleşir **/
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
                mevcutYemek.yemek_siparis_adet += gelenYemek.yemek_siparis_adet
            } else {
                mevcutSepetList.add(yemek)
            }

            //UI- livedata güncelle
            viewModel.sepetListesi.value = mevcutSepetList //RecyclerView otomatik yenilenir

        }


        //TODO- adapter & recyclerview arası veri aktarma işlemi & liste gönderimi
        //TODO-RecyclerView’i önce boş bir adapter ile başlat, sonra LiveData geldiğinde veriyi güncelle.
        //TODO-UI management (Manage Sepet Fill & Blank)
        val sepetAdapter = SepetAdapter(requireContext(), listOf(), viewModel)
        binding.recyclerViewSepet.adapter = sepetAdapter

        viewModel.sepetListesi.observe(viewLifecycleOwner) { liste ->
            // UI management
            if (liste.isNullOrEmpty()) {
                binding.manageSepetBlank.visibility = View.VISIBLE
                binding.manageSepetFill.visibility = View.GONE
            } else {
                binding.manageSepetBlank.visibility = View.GONE
                binding.manageSepetFill.visibility = View.VISIBLE


                // toplam güncelle
                binding.textViewSepetToplam.text = "₺" + viewModel.toplamSepetHesapla()

                // adapter update
                sepetAdapter.sepettekiYemeklerListesi = liste

                sepetAdapter.notifyDataSetChanged()
                Log.e("SepetFragment", "Liste geldi: $liste")
            }


        }


        //sepeti onayla
        binding.buttonSepetiOnayla.setOnClickListener {
            val total = viewModel.toplamSepetHesapla()
            val sepetListesi = viewModel.sepetListesi.value?.toMutableList() ?: mutableListOf()

            MaterialAlertDialogBuilder(requireContext(), R.style.PopupStyle)
                .setTitle("Sipariş Onayı")
                .setMessage("\uD83D\uDECD\uFE0F Sepetinizdeki yemekleri onaylıyor musunuz?\n\nToplam: ₺$total")
                .setPositiveButton("Onayla") { d, _ ->
                    d.dismiss()

                    //todo- Başarı popup
                    MaterialAlertDialogBuilder(requireContext(), R.style.PopupTextStyle)
                        .setMessage(
                            "\uD83D\uDED2 Siparişiniz başarıyla alındı! \uD83C\uDF89\n\n" +
                                    sepetListesi.joinToString(separator = "\n") { "${it.yemek_adi} - (${it.yemek_siparis_adet})" }
                        )
                        .setPositiveButton("Tamam") { ok, _ -> ok.dismiss() }
                        .show()


                    //TODO- Notification - Bildirim gönderme
                    val channelId = "siparis_channel"

                    // Android 13+ izin
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
                            return@setPositiveButton
                        }
                    }

                    val intent = requireActivity().intent
                    val pendingIntent = PendingIntent.getActivity(
                        requireContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )

                    val builder = NotificationCompat.Builder(requireContext(), channelId)
                        .setSmallIcon(R.drawable.shop_img) // kendi iconunu kullan
                        .setContentTitle("Siparişiniz Alındı")
                        .setContentText("Siparişiniz alındı ve hazırlanıyor! \uD83D\uDED2\uD83C\uDF72\uD83D\uDE0B\uD83E\uDD73⏳️")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    with(NotificationManagerCompat.from(requireContext())) {
                        notify(1001, builder.build())
                    }
                }

                 //todo- başarısız popup
                .setNegativeButton("Vazgeç") { d, _ -> d.dismiss() }
                .show()
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val userEmail = authViewModel.getCurrentUserEmail().toString()
        viewModel.sepettekiYemekleriGetir(userEmail)
    }


}