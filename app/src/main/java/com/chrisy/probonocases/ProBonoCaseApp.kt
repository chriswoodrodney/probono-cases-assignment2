package com.chrisy.probonocases

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chrisy.probonocases.screens.CaseDetailScreen
import com.chrisy.probonocases.screens.CaseListScreen
import com.chrisy.probonocases.screens.DashboardScreen
import com.chrisy.probonocases.viewmodel.CaseViewModel
import com.chrisy.probonocases.viewmodel.CaseViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProBonoCaseApp(factory: CaseViewModelFactory) {
    val navController = rememberNavController()
    val caseViewModel: CaseViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pro Bono Cases") },
                actions = {
                    TextButton(onClick = { navController.navigate("dashboard") }) {
                        Text("Dashboard", color = MaterialTheme.colorScheme.onPrimary)
                    }
                    TextButton(onClick = { navController.navigate("case_list") }) {
                        Text("Cases", color = MaterialTheme.colorScheme.onPrimary)
                    }
                    TextButton(onClick = { navController.navigate("lawyer_list") }) {
                        Text("Lawyers", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            composable("dashboard") {
                DashboardScreen(
                    viewModel = caseViewModel,
                    onCaseClick = { caseId ->
                        navController.navigate("case_detail/$caseId")
                    }
                )
            }

            composable("case_list") {
                CaseListScreen(
                    viewModel = caseViewModel,
                    onCaseClick = { caseId ->
                        navController.navigate("case_detail/$caseId")
                    },
                    onAddNewCase = {
                        // Add navigation for new case entry if needed
                    }
                )
            }

            composable(
                route = "case_detail/{caseId}",
                arguments = listOf(navArgument("caseId") { type = NavType.LongType })
            ) { backStackEntry ->
                val caseId = backStackEntry.arguments?.getLong("caseId") ?: 0L
                CaseDetailScreen(
                    caseId = caseId,
                    viewModel = caseViewModel,
                    onBackClick = { navController.popBackStack() },
                    onEditClick = { /* navigate to edit screen if needed */ }
                )
            }

            // Placeholder for Lawyer List Screen (you can implement it later)
            composable("lawyer_list") {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Text(
                        text = "Lawyer List Screen (Coming Soon)",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }
        }
    }
}
