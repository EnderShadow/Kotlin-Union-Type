import unions.UnionType
import java.lang.AssertionError

val JsonType = UnionType(true, Number::class, String::class, Boolean::class, JsonObject::class, JsonArray::class)

fun main(args: Array<String>)
{
    println(JsonType)
    
    val obj = JsonObject()
    obj["int"] = 4
    obj["double"] = 2.0
    obj["text"] = "Hello World!"
    obj["bool"] = true
    obj["null"] = null
    
    val array = JsonArray()
    array.add(4.0)
    array.add("Hi")
    array.add(true)
    
    obj["array"] = array
    
    println("Passed JsonObj test")
    
    try
    {
        obj["shouldFail"] = Any()
        println("Failed instance of Any test")
    }
    catch(ae: AssertionError)
    {
        println("Passed instance of Any test")
    }
}

class JsonObject
{
    private val values = mutableMapOf<String, Any?>()
    
    operator fun set(key: String, value: Any?)
    {
        JsonType.assertIsInstance(value)
        values[key] = value
    }
    
    operator fun get(key: String) = values[key]
    
    override fun toString() = values.map {entry -> "${entry.key}: ${entry.value}"}.joinToString(", ", "{", "}")
}

class JsonArray
{
    private val values = mutableListOf<Any?>()
    
    fun add(element: Any?)
    {
        JsonType.assertIsInstance(element)
        values.add(element)
    }
    
    fun remove(element: Any?) = values.remove(element)
    
    operator fun get(index: Int) = values[index]
    
    override fun toString() = values.joinToString(", ", "[", "]")
}