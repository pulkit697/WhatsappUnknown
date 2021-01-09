package com.example.whatsappunknown

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //via app
        btbutton.setOnClickListener {
            val s = etPhone.text.toString()
            if (s.isNotEmpty()) {
                val number: String = refineNumber(s)
                if (isValid(number))
                    openWhatsApp(number)
                else
                    Toast.makeText(this, "please enter a valid number.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "please enter a number.", Toast.LENGTH_SHORT).show()
            }
        }


        //via search
        if(intent!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val s: String = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
                val number: String = refineNumber(s)
                if (isValid(number))
                    openWhatsApp(number)
                else
                    Toast.makeText(this, "please enter a valid number.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Sorry this android version doesn't support this feature.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun openWhatsApp(number: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.apply {
            //package set krne se sirf whatsapp ka hi option aayega, link ko braowser mi kholne ka option nhi aayega
            setPackage("com.whatsapp")
            data = Uri.parse("https://wa.me/$number")
        }
        if(packageManager.resolveActivity(i,0)!=null)
                startActivity(i)
        else
            Toast.makeText(this,"Please install WhatsApp!!!",Toast.LENGTH_SHORT).show()
    }

    private fun refineNumber(s:String):String = if(s[0]=='+'){
        s.substring(1)
    }else if (s.length==10){
        "91$s"
    }else{
        ""
    }

    private fun isValid(number:String):Boolean = number.length==12 && number.isDigitsOnly()
}