package unions

import java.lang.AssertionError
import kotlin.reflect.KClass

/**
 * Represents a union of types
 */
class UnionType(val nullable: Boolean, vararg types: KClass<*>)
{
    val types = types.toSet()
    
    /**
     * returns true if value is an instance of any types in this union
     */
    fun isInstance(value: Any?): Boolean
    {
        if(value === null)
            return nullable
        return types.any {it.isInstance(value)}
    }
    
    /**
     * throws an AssertionError if value is not an instance of any types in this union
     */
    fun assertIsInstance(value: Any?)
    {
        if(!isInstance(value))
            throw AssertionError("$value is not an instance of $this")
    }
    
    override fun equals(other: Any?) = other is UnionType && (types - other.types).union(other.types - types).isEmpty()
    override fun hashCode() = types.hashCode()
    override fun toString() = types.joinToString(" | ", "Union { ", " }") {it.qualifiedName ?: it.simpleName ?: it.toString()}
}