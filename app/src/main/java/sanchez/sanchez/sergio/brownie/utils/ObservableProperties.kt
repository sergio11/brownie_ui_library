package sanchez.sanchez.sergio.brownie.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

// We specify a typealias for the listeners
typealias BiConsumer<O> = (O, O) -> Unit


interface IObservable<CLASS> {
    val manager: ObservableManager<CLASS>
}

open class Observable<CLASS> :
    IObservable<CLASS> {
    override val manager =
        ObservableManager<CLASS>()
}

interface DelegateProvider<CLASS, PROP> {
    operator fun provideDelegate(thisRef: CLASS, prop: KProperty<*>): ReadWriteProperty<CLASS, PROP>
}

class ObservableValue<CLASS, PROP>(
    private val delegater: ObservableManager<CLASS>,
    val value: Any?,
    private val converter: (Any?) -> PROP
) : DelegateProvider<CLASS, PROP> {
    override operator fun provideDelegate(thisRef: CLASS, prop: KProperty<*>): ReadWriteProperty<CLASS, PROP> = delegater.WrappingRWProperty(prop, value, converter)
}

/**
 * To add the functionality to register properties and listeners, we use extension-functions,
 * such that we don’t have to repeat us twice (once in the interface and once in the IObservable).
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified CLASS, PROP> IObservable<CLASS>.observable(value: PROP): DelegateProvider<CLASS, PROP>
        = ObservableValue(
    manager,
    value
) { a: Any? -> a as PROP }

/**
 * To safely register listeners, we use two generics: the one of the classType an the
 * one for the propertyType. with those two properties.
 * This function needs to be inlined, as we are going to use PROP for casting.
 */
inline fun <CLASS, reified PROP> IObservable<CLASS>.registerListener(
    property: KProperty1<CLASS, PROP>,
    crossinline consumer: BiConsumer<PROP>
) = manager.registerListener(property, consumer)

inline fun <CLASS, reified PROP> IObservable<CLASS>.unregisterListener(
    property: KProperty1<CLASS, PROP>) = manager.unregisterListener(property)

fun <CLASS> IObservable<CLASS>.unregisterAll() = manager.unregisterAll()

class ObservableManager<T> {

    private val properties = mutableMapOf<String, Any?>()
    private val listeners = mutableMapOf<String, MutableList<BiConsumer<Any?>>>()

    private fun updateValue(name: String, value: Any?) {
        val old = properties.put(name, value)
        listeners[name]?.forEach { it(old, value) }
    }

    fun registerListener(name: String, biConsumer: BiConsumer<Any?>) {
        listeners.getOrPut(name) { mutableListOf() }.add(biConsumer)
    }

    fun unregisterListener(name: String) {
        listeners.remove(name)
    }

    fun unregisterAll() {
        listeners.clear()
    }

    inline fun <reified PROP> registerListener(property: KProperty1<T, PROP>, crossinline consumer: BiConsumer<PROP>) =
        registerListener(property.name) { old, new -> consumer(old as PROP, new as PROP) }

    inline fun <reified PROP> unregisterListener(property: KProperty1<T, PROP>) =
        unregisterListener(property.name)


    inner class WrappingRWProperty<PROP>(prop: KProperty<*>, value: Any?, private val converter: (Any?) -> PROP
    ) : ReadWriteProperty<T, PROP> {

        //register the initial value
        init {
            properties[prop.name] = value
        }
        override operator fun getValue(thisRef: T, property: KProperty<*>)
                = properties[property.name].let(converter)
        override operator fun setValue(thisRef: T, property: KProperty<*>, value: PROP)
                = updateValue(property.name, value)
    }
}
