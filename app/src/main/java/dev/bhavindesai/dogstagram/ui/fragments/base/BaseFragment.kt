package dev.bhavindesai.dogstagram.ui.fragments.base

import android.content.Context
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * Lazily get the view model
     */
    inline fun <reified VM : ViewModel> lazyViewModel() = lazy {
        ViewModelProvider(this, viewModelFactory).get(VM::class.java)
    }

    @CallSuper
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}