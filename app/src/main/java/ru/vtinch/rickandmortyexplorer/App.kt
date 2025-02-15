package ru.vtinch.rickandmortyexplorer

import android.app.Application
import ru.vtinch.rickandmortyexplorer.core.Core
import ru.vtinch.rickandmortyexplorer.core.MyViewModel
import ru.vtinch.rickandmortyexplorer.core.ProvideViewModel

class App : Application(), ProvideViewModel {

    private val core by lazy {
        Core()
    }

    override fun <T : MyViewModel> viewModel(clazz: Class<T>): T {
        return core.viewModel(clazz)
    }

}

