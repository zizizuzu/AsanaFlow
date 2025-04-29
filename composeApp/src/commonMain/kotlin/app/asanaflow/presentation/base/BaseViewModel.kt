package app.asanaflow.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event, State, Effect> : ViewModel() {
    private val initialState: State by lazy(mode = LazyThreadSafetyMode.NONE) { setInitialState() }

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _event: Channel<Event> = Channel(Channel.BUFFERED)
    private val eventFlow: Flow<Event> = _event.receiveAsFlow()

    private val _effect: Channel<Effect> = Channel(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            eventFlow.collect { event ->
                handleEvent(event)
            }
        }
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.send(event)
        }
    }

    protected fun updateState(update: State.() -> State) {
        _state.update(update)
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    protected abstract fun setInitialState(): State

    protected abstract fun handleEvent(event: Event)
}