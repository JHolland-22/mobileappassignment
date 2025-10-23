package ie.setu.assignment1.activities

import android.content.Intent
import ie.setu.assignment1.R
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.ListView
import android.widget.SearchView.*
import ie.setu.assignment1.databinding.ActivityClothListBinding
import ie.setu.assignment1.adapters.ClothAdapter
import ie.setu.assignment1.adapters.ClothListener
import ie.setu.assignment1.databinding.ActivityMainBinding
import ie.setu.assignment1.main.MainApp
import ie.setu.assignment1.models.ClothModel
import androidx.appcompat.widget.SearchView



class ClothListActivity : AppCompatActivity(), ClothListener {

    private val clothingItems = arrayOf("jumpers", "crewnecks", "socks", "shorts")
    lateinit var app: MainApp
    lateinit var searchView : SearchView
    lateinit var listView: ListView
    lateinit var list : ArrayList<String>




    private lateinit var binding: ActivityClothListBinding
    private lateinit var adapter: ClothAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        adapter = ClothAdapter(app.cloths.findAll(), this)
        binding.recyclerView.adapter = adapter

        setContentView(binding.root)

        setupSearchView()
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val isMatchFound = clothingItems.any{it.contains(p0
                    .orEmpty(), ignoreCase = true)}
                val msg = if (isMatchFound) "Found: $p0" else getString(R.string.no_match)
                Toast.makeText(this@ClothListActivity, msg, Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0?.lowercase())
                return false
            }
        })


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


