package ie.setu.assignment1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import ie.setu.assignment1.models.ClothModel
import ie.setu.assignment1.databinding.CardClothBinding


interface ClothListener {
    fun onClothClick(cloth: ClothModel)
}

class ClothAdapter(private var cloths: List<ClothModel>,
                       private val listener: ClothListener) :
    RecyclerView.Adapter<ClothAdapter.MainHolder>(), android.widget.Filterable
{

        private var filteredCloths: MutableList<ClothModel> =  cloths.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardClothBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val cloth = cloths[holder.adapterPosition]
        holder.bind(cloth, listener)
    }

    override fun getItemCount(): Int = cloths.size

   override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                val filteredList = ArrayList<ClothModel>()

                if (charString.isEmpty()) {
                    filteredList.addAll(cloths)
                }else{
                    cloths.filter {
                        it.title?.contains(charString, ignoreCase = true) == true || it.description?.contains(
                            charString,
                            ignoreCase = true
                        ) == true
                    }.forEach { filteredList.add(it) }

                    }
                return FilterResults().apply { values = filteredList }

                }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filtered = results?.values as? ArrayList<ClothModel> ?: ArrayList ()
                cloths = filtered
                notifyDataSetChanged()

            }

            }
        }



    class MainHolder(private val binding : CardClothBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cloth: ClothModel, listener: ClothListener) {
            binding.clothTitle.text = cloth.title
            binding.description.text = cloth.description
            binding.root.setOnClickListener { listener.onClothClick(cloth) }
        }
    }
}

