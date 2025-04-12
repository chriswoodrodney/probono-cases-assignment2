package com.chrisy.probonocases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.chrisy.probonocases.data.AppDatabase
import com.chrisy.probonocases.data.CaseRepository
import com.chrisy.probonocases.data.DatabaseSeeder
import com.chrisy.probonocases.network.RetrofitInstance
import com.chrisy.probonocases.ui.theme.ProBonoCaseTheme
import com.chrisy.probonocases.viewmodel.CaseViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Initialize database
        val database = AppDatabase.getDatabase(applicationContext)
        val caseDao = database.caseDao()

        // ✅ Initialize Retrofit API
        val legalReferenceApi = RetrofitInstance.api

        // ✅ Create repository with DAO and API
        val repository = CaseRepository(caseDao, legalReferenceApi)

        // ✅ Create view model factory
        val factory = CaseViewModelFactory(repository)

        // Optional: Seed sample data
        lifecycleScope.launch {
            val seeder = DatabaseSeeder(database)
            seeder.populateSampleData()
        }

        setContent {
            ProBonoCaseTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProBonoCaseApp(factory)
                }
            }
        }
    }
}
