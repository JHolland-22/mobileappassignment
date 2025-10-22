package ie.setu.assignment1.activities

import android.content.Intent
import ie.setu.assignment1.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.SearchView
import android.widget.ListView
import ie.setu.assignment1.databinding.ActivityClothListBinding
import ie.setu.assignment1.adapters.ClothAdapter
import ie.setu.assignment1.adapters.ClothListener
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.ClothModel


class ClothListActivity : AppCompatActivity(), ClothListener {

    private val clothingItems = arrayOf("jumpers", "crewnecks", "socks", "shorts")
    lateinit var app: MainApp
    lateinit var searchView : SearchView
    lateinit var listView: ListView
    lateinit var list : ArrayList<String>


    private lateinit var binding: ActivityClothListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        setContentView(R.layout.activity_cloth_list)
        title = "search for clothing"

        searchView = findViewById(R.id.searchView)



        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ClothAdapter(app.cloths.findAll(), this)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ClothActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.cloths.findAll().size)
            }
        }

    override fun onClothClick(cloth: ClothModel) {
        val launcherIntent = Intent(this, ClothActivity::class.java)
        launcherIntent.putExtra("cloth_edit", cloth)
        getClickResult.launch(launcherIntent)
    }


    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.notifyItemRangeChanged(0, app.cloths.findAll().size)
            }
        }

}


