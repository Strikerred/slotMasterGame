package com.faustogomez.slotmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_auth_ops.*

class AuthOpsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_ops)

        login_button.setOnClickListener{
            startActivity(AuthActivity.newIntent(AuthMode.Login, this))
        }

        register_button.setOnClickListener {
            startActivity(AuthActivity.newIntent(AuthMode.Register, this))
        }
    }
}

sealed class AuthMode() : Parcelable{
    @Parcelize
    object Login: AuthMode()

    @Parcelize
    object Register : AuthMode()
}
