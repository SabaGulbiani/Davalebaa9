package com.example.Davalebaa9

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var progress: FrameLayout

    private lateinit var btnAddUser: MaterialButton
    private lateinit var btnStartService: MaterialButton
    private lateinit var rvUsers: RecyclerView

    private lateinit var adapter: UserAdapter

    private lateinit var db: Dao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DB.build(applicationContext).dao()

        RetrieveUsersService.onStart { progress.isVisible = true }
        RetrieveUsersService.doOnComplete(this::onComplete)
        RetrieveUsersService.doOnFailure(this::onFailure)

        progress = findViewById(R.id.progress)
        btnAddUser = findViewById(R.id.btnAddUser)
        btnStartService = findViewById(R.id.btnStartBackgroundService)
        rvUsers = findViewById(R.id.rvUsers)
        adapter = UserAdapter {
            startActivity(
                Intent(this, UserDetailsActivity::class.java).putExtra("user_id", it.id)
            )
        }

        btnAddUser.setOnClickListener {
            startActivity(Intent(this, CreateUserActivity::class.java))
        }

        btnStartService.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, RetrieveUsersService::class.java))
            } else {
                startService(Intent(this, RetrieveUsersService::class.java))
            }
        }

        rvUsers.adapter = adapter
    }

    private fun onComplete(users: List<User>) {
        db.insert(users.map { it.toEntity() })

        adapter.submitList(db.getAll().map { it.toModel() })

        progress.isVisible = false
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }
}