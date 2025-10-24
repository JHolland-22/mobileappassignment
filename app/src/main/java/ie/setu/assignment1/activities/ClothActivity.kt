package ie.setu.assignment1.activities



import ie.setu.assignment1.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import ie.setu.assignment1.databinding.ActivityClothBinding
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.assignment1.helpers.showImagePicker
import ie.setu.assignment1.models.ClothModel
import ie.setu.assignment1.main.MainApp
import timber.log.Timber

class ClothActivity : AppCompatActivity() {

    private val clothingItems = arrayOf("jumpers", "crewnecks", "socks", "shorts")

    private lateinit var binding: ActivityClothBinding
    private var cloth: ClothModel = ClothModel()
    private lateinit var app: MainApp
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClothBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        Timber.i("Clothes Activity started...")

        val autocomp = binding.autoCompleteTxt
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, clothingItems)
        autocomp.setAdapter(adapter)
        autocomp.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "Clicked: ${clothingItems[position]}", Toast.LENGTH_SHORT).show()
        }

        var edit = false
        if (intent.hasExtra("cloth_edit")) {
            edit = true
            cloth = intent.extras?.getParcelable("cloth_edit")!!
           // binding.clothTitle.setText(cloth.title)
            binding.description.setText(cloth.description)
            binding.btnAdd.setText(R.string.save_cloth)

        }

        binding.btnAdd.setOnClickListener {
            val selectedTitle = binding.autoCompleteTxt.text.toString()
            cloth.title = selectedTitle
           // cloth.title = binding.clothTitle.text.toString()
            cloth.description = binding.description.text.toString()
            if (cloth.title!!.isEmpty()) {
                Snackbar.make(it, "Please select a clothing item", Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    app.cloths.update(cloth.copy())
                } else {
                    app.cloths.create(cloth.copy())
                }
            }
            setResult(RESULT_OK)
            Snackbar.make(it, "Cloth added successfully!", Snackbar.LENGTH_SHORT).show()
            binding.description.text.clear()
            setResult(RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cloth, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == R.id.item_cancel) finish()
        return super.onOptionsItemSelected(item)
    }


}
