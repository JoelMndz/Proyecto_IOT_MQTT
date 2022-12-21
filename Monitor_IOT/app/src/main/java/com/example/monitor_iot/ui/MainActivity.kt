package com.example.monitor_iot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.monitor_iot.databinding.ActivityMainBinding
import com.example.monitor_iot.http.adapters.RetrofitAdapter
import com.example.monitor_iot.http.models.Login
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                login();
            }
        })
    }

    fun login(){
        CoroutineScope(Dispatchers.IO).launch {
            var email = binding.txtEmail.editText?.text.toString()
            var password = binding.txtPassword.editText?.text.toString()
            var adaptador = RetrofitAdapter()
            var call = adaptador.login(Login(email,password))

            call.enqueue(object : Callback<Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@MainActivity, "Bienvenido!", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@MainActivity,Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas!", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Error",t.message.toString())
                }
            })
        }
    }
}