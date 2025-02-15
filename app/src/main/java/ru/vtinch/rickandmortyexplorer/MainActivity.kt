package ru.vtinch.rickandmortyexplorer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.vtinch.rickandmortyexplorer.navigation.AppNavHost
import ru.vtinch.rickandmortyexplorer.core.MyViewModel
import ru.vtinch.rickandmortyexplorer.core.ProvideViewModel
import ru.vtinch.rickandmortyexplorer.ui.theme.RickAndMortyExplorerTheme

class MainActivity : ComponentActivity(), ProvideViewModel {
    @OptIn(ExperimentalSharedTransitionApi::class)
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RickAndMortyExplorerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(modifier = Modifier.fillMaxSize().padding(innerPadding))

                }
            }
        }
    }

    override fun <T : MyViewModel> viewModel(clazz: Class<T>): T {
        return (application as ProvideViewModel).viewModel(clazz)
    }
}


