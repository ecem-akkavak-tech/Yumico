import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.ecemm.yumico.R

class LoadingDialog(private val context: Context) {

    private var dialog: AlertDialog? = null

    fun show() {
        if (dialog != null && dialog!!.isShowing) return

        val dialogView = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null)

        // Glide ile GIF yükleme
        val imageView = dialogView.findViewById<ImageView>(R.id.loadingGif)
        Glide.with(context)
            .asGif()
            .load(R.drawable.loading) // drawable içindeki GIF
            .into(imageView)

        dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
    }

    fun hide() {
        dialog?.dismiss()
    }
}
