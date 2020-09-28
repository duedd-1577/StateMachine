package com.danhdueexoictif.statemachine.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.danhdueexoictif.statemachine.BR
import com.danhdueexoictif.statemachine.utils.d
import com.danhdueexoictif.statemachine.utils.hideKeyBoard
import kotlinx.coroutines.launch

abstract class BaseFragment<ViewBinding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment(),
    NavController.OnDestinationChangedListener {

    @get:LayoutRes
    abstract val layoutId: Int

    lateinit var viewBinding: ViewBinding

    abstract val viewModel: ViewModel

    protected open val navController: NavController? by lazy { findNavController() }

    private var isLightStatusBar = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<ViewBinding>(inflater, layoutId, container, false).let {
        this.viewBinding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            setVariable(BR.viewModel, viewModel)
            root.isClickable = true
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // listen Navigation Destination Change.
        navController?.addOnDestinationChangedListener(this)
        setupViewModel()
    }

    override fun onDestroyView() {
        // remove Navigation Destination Changed Listener.
        navController?.removeOnDestinationChangedListener(this)
        super.onDestroyView()
    }

    /**
     * pre setup view models.
     */
    private fun setupViewModel() {
        viewModel.apply {
            // observer base fields.
        }
    }

    /**
     * Hide keyboard when user clicks outside.
     * Note: only use this one for screen that have a EditText.
     */
    @SuppressLint("ClickableViewAccessibility")
    open fun setupHideKeyboardWhenTouchOutSide(view: View?) {
        viewLifecycleOwner.lifecycleScope.launch {
            // Set up touch listener for non-text box views to hide keyboard.
            if (view !is EditText) {
                view?.setOnTouchListener { _, _ ->
                    hideKeyBoard()
                    false
                }
            }
            // If a layout container, iterate over children and seed recursion.
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val innerView = view.getChildAt(i)
                    setupHideKeyboardWhenTouchOutSide(innerView)
                }
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        d { "onDestinationChanged" }
    }
}
