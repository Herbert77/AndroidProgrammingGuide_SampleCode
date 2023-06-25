package com.example.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.criminalintent.database.CrimeRepository
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var insertItemButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insertItemButton = findViewById(R.id.insert_item_button)
        insertItemButton.bringToFront()
        insertItemButton.setOnClickListener {
            println("insert item button clicked")
            runBlocking {
                CrimeRepository.get().insertCrimes()
            }
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
//            val fragment = CrimeFragment()
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment).commit()
        }
    }
}