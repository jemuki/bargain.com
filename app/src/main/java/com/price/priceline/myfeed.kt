package com.price.priceline

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.Toast

class myfeed : AppCompatActivity() {
    lateinit var foods: CheckBox
    lateinit var fashion: CheckBox
    lateinit var electronics: CheckBox
    lateinit var beauty: CheckBox
    lateinit var home: CheckBox
    lateinit var automotive: CheckBox
    lateinit var stationery: CheckBox
    lateinit var health: CheckBox
    lateinit var construction: CheckBox
    lateinit var all: CheckBox
    lateinit var button: Button

    lateinit var home2: CheckBox
    lateinit var entertainment: CheckBox
    lateinit var beauty2: CheckBox
    lateinit var travel: CheckBox
    lateinit var business: CheckBox
    lateinit var health2: CheckBox
    lateinit var housing: CheckBox
    lateinit var repair: CheckBox
    lateinit var all2: CheckBox
    lateinit var button2: Button


    var feeds: ArrayList<String> = ArrayList<String>()
    var feeds2: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myfeed)
        val back = findViewById<ImageButton>(R.id.myfeedtoback)
        back.setOnClickListener {
            startActivity(Intent(applicationContext, profile::class.java))
        }

        foods = findViewById(R.id.checkBox1)
        fashion = findViewById(R.id.checkBox2)
        electronics = findViewById(R.id.checkBox3)
        beauty = findViewById(R.id.checkBox4)
        home = findViewById(R.id.checkBox5)
        automotive = findViewById(R.id.checkBox6)
        stationery = findViewById(R.id.checkBox7)
        health = findViewById(R.id.checkBox8)
        construction = findViewById(R.id.checkBox9)
        all = findViewById(R.id.checkBox10)
        button = findViewById(R.id.button)

        home2 = findViewById(R.id.checkBox1s)
        entertainment = findViewById(R.id.checkBox2s)
        beauty2 = findViewById(R.id.checkBox3s)
        travel = findViewById(R.id.checkBox4s)
        business = findViewById(R.id.checkBox5s)
        health2 = findViewById(R.id.checkBox6s)
        housing = findViewById(R.id.checkBox7s)
        repair = findViewById(R.id.checkBox8s)
        all2 = findViewById(R.id.checkBox9s)
        button2 = findViewById(R.id.button2)

        feeds = ArrayList()
        feeds2 = ArrayList()
        button.setOnClickListener {
            if (all.isChecked) {
                val prefs2 = getSharedPreferences("myfeed", Context.MODE_PRIVATE)
                if (prefs2.getString("feed", null) != null){
                    prefs2.edit().remove("feed").commit()
                    Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
                }//ends if
                else {
                    Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
                }//ends else
            }//ends if all is checked
            else {
                if (foods.isChecked) {
                    feeds.add("foods")

                }
                if (fashion.isChecked) {
                    feeds.add("fashion")
                }
                if (electronics.isChecked) {
                    feeds.add("electronics")
                }
                if (beauty.isChecked) {
                    feeds.add("beauty_products")
                }
                if (home.isChecked) {
                    feeds.add("home_products")
                }
                if (automotive.isChecked) {
                    feeds.add("automotive")
                }
                if (stationery.isChecked) {
                    feeds.add("stationery")
                }
                if (health.isChecked) {
                    feeds.add("health_products")
                }
                if (construction.isChecked) {
                    feeds.add("construction_products")
                }
                val prefs = getSharedPreferences("myfeed", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("feed", feeds.toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", "plus"))
                editor.commit()
                println("my feed is "+prefs.getString("feed", ""))
                Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
            }//if all is not clicked
        }//ends on click


        button2.setOnClickListener {
            if (all2.isChecked) {
                val prefs2 = getSharedPreferences("myfeed2", Context.MODE_PRIVATE)
                if (prefs2.getString("feed", null) != null){
                    prefs2.edit().remove("feed").commit()
                    Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
                }//ends if
                else {
                    Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
                }//ends else
            }//ends if all is checked
            else {
                if (home2.isChecked) {
                    feeds2.add("home_services")

                }
                if (entertainment.isChecked) {
                    feeds2.add("entertainment")
                }
                if (beauty2.isChecked) {
                    feeds2.add("beauty_and_fashion")
                }
                if (travel.isChecked) {
                    feeds2.add("travel")
                }
                if (business.isChecked) {
                    feeds2.add("business_services")
                }
                if (health2.isChecked) {
                    feeds2.add("health")
                }
                if (housing.isChecked) {
                    feeds2.add("housing")
                }
                if (repair.isChecked) {
                    feeds2.add("repair_and_maintenance")
                }
                val prefs2 = getSharedPreferences("myfeed2", Context.MODE_PRIVATE)
                val editor2 = prefs2.edit()
                editor2.putString("feed", feeds2.toString().replace("[", "").replace("]", "").replace(",", "").replace(" ", "plus"))
                editor2.commit()
                println("my feed is "+prefs2.getString("feed", ""))
                Toast.makeText(applicationContext, "done", Toast.LENGTH_LONG).show()
            }//if all is not clicked

        }//ends services on click

}//ends oncreate
}//ends class
