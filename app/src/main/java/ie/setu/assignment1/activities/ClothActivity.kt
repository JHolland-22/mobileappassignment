package ie.setu.assignment1.activities


import ie.setu.assignment1.R
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    private lateinit var binding: ActivityClothBinding
    var cloth: ClothModel = ClothModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityClothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        Timber.i("Clothes Activity started...")

        if (intent.hasExtra("cloth_edit")) {
            edit = true
            // Requires API 33+
            // cloth = intent.getParcelableExtra("cloth_edit",ClothModel::class.java)!!
            cloth = intent.extras?.getParcelable("cloth_edit")!!
            binding.clothTitle.setText(cloth.title)
            binding.description.setText(cloth.description)
            binding.btnAdd.setText(R.string.save_cloth)
            Picasso.get()
                .load(cloth.image)
                .into(binding.clothImage)
        }

        binding.btnAdd.setOnClickListener() {
            cloth.title = binding.clothTitle.text.toString()
            cloth.description = binding.description.text.toString()
            if (cloth.title!!.isEmpty()) {
                Snackbar.make(it, R.string.enter_cloth_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.cloths.update(cloth.copy())
                } else {
                    app.cloths.create(cloth.copy())
                }
            }
            Timber.i("add Button Pressed: $cloth")
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cloth, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            cloth.image = result.data!!.data!!
                            Picasso.get()
                                .load(cloth.image)
                                .into(binding.clothImage)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}


