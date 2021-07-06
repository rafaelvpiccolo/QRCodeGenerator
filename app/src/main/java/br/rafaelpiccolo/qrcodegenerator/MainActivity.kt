package br.rafaelpiccolo.qrcodegenerator

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class MainActivity : AppCompatActivity() {

    var qrCodeContent: EditText? = null
    var qrCodeIMG: ImageView? = null
    var generatorBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        generatorBtn!!.setOnClickListener{

            if (TextUtils.isEmpty(qrCodeContent!!.text.toString())){

                qrCodeContent!!.error = "This field can not be empty!"
                qrCodeContent!!.requestFocus()

            }
            else{
                generateQrCode(qrCodeContent!!.text.toString())
            }

        }

    }

    fun generateQrCode(qrCodeContent: String) {

        val qrCode = QRCodeWriter()

        try {

            val bitMatrix = qrCode.encode(qrCodeContent, BarcodeFormat.QR_CODE, 220, 220)

            val width = bitMatrix.width
            val height = bitMatrix.height

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width){
                for (y in 0 until height){
                    bitmap.setPixel(x,y, if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }

            qrCodeIMG!!.setImageBitmap(bitmap)

        }catch (e: WriterException){}

    }

    fun initComponents() {
        qrCodeContent = findViewById(R.id.qrCodeContent)
        qrCodeIMG = findViewById(R.id.qrCode)
        generatorBtn = findViewById(R.id.generatorBtn)
    }
}