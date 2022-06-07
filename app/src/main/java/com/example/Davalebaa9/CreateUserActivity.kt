package com.example.Davalebaa9

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

import retrofit2.Response


class CreateUserActivity : AppCompatActivity() {

    private lateinit var api: API

    private lateinit var etName: EditText
    private lateinit var etJob: EditText
    private lateinit var btnCreate: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        api = API.create()

        etName = findViewById(R.id.etName)
        etJob = findViewById(R.id.etJob)
        btnCreate = findViewById(R.id.btnCreate)

        btnCreate.setOnClickListener {
            val name = etName.text.toString()
            val email = etJob.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                api.createUser(CreateUserRequest(name, email))
                    .enqueue(object : retrofit2.Callback<CreateUserResponse> {

                        override fun onResponse(
                            call: retrofit2.Call<CreateUserResponse>,
                            response: Response<CreateUserResponse>
                        ) {
                            Toast.makeText(
                                this@CreateUserActivity,
                                "User with ID ${response.body()?.id} create success",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }

                        override fun onFailure(call: retrofit2.Call<CreateUserResponse>, t: Throwable) {
                            Toast.makeText(this@CreateUserActivity, t.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
            }
        }
    }
}


    

