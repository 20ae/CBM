package ac.duksung.cbm_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UpdateProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        val curName = findViewById<TextView>(R.id.curName)
        val newName = findViewById<EditText>(R.id.newName)
        val update = findViewById<Button>(R.id.update)

        curName.setText(MySharedPreferences.getUserName(this))
        //val curName = findViewById<TextView>(R.id.curName)
        //val newName = findViewById<EditText>(R.id.newName)
        //val update = findViewById<Button>(R.id.update)
    }
}