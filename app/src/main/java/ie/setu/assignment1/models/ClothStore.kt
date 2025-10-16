package ie.setu.assignment1.models 

interface ClothStore {
    fun findAll(): List<ClothModel>
    fun create(cloth: ClothModel)
    fun update(cloth: ClothModel)
}