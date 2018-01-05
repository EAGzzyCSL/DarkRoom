package me.eagzzycsl.darkroom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import me.eagzzycsl.darkroom.ui.ActivityToolbar

class BackRestoreActivity : ActivityToolbar(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_backup-> {

            }
            R.id.button_restore-> {

            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_back_restore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_back_restore)
        val restoreButton = findViewById<Button>(R.id.button_restore)
        val backupButton =findViewById<Button>(R.id.button_backup)
        restoreButton.setOnClickListener(this)
        backupButton.setOnClickListener(this)
    }
}
