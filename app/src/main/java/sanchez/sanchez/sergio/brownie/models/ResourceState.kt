package sanchez.sanchez.sergio.brownie.models

/**
 * Resource State
 */
sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}