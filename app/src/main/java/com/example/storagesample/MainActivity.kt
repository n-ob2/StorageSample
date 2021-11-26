package com.example.storagesample

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.storagesample.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var dataText: EditText? = null
    private var storageRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataText = findViewById(R.id.dataText);

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(binding.toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener(object : DialogInterface.OnClickListener, View.OnClickListener {
            override fun onClick(view: View?) {}
            override fun onClick(p0: DialogInterface?, p1: Int) { }
        })

        /*
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
         */

        storageRef = FirebaseStorage.getInstance().reference
        val fileRef = storageRef!!.child("StorageSampleText.txt")


        val ONE_MEGABYTE = (1024 * 1024).toLong()
        fileRef.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { bytes ->
                val data = String(bytes!!)
                val arr = data.split("\n").toTypedArray()
                var res = ""
                for (i in arr.indices) {
                    res += """
            ${i + 1}:${arr[i]}
            
            """.trimIndent()
                }
                dataText?.setText(res)
            }.addOnFailureListener {
                // Handle any errors
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}