//package com.chrisy.probonocases.screens
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableIntStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.chrisy.probonocases.data.Judge
//import com.chrisy.probonocases.data.Lawyer
//import com.chrisy.probonocases.viewmodel.LawyerJudgeViewModel
//
//@Composable
//fun LawyerJudgeScreen(
//    viewModel: LawyerJudgeViewModel = viewModel()
//) {
//    var selectedTab by remember { mutableIntStateOf(0) }
//    val tabs = listOf("Lawyers", "Judges")
//
//    val lawyers by viewModel.allLawyers.observeAsState(emptyList())
//    val judges by viewModel.allJudges.observeAsState(emptyList())
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        TabRow(
//            selectedTabIndex = selectedTab,
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary
//        ) {
//            tabs.forEachIndexed { index, title ->
//                Tab(
//                    selected = selectedTab == index,
//                    onClick = { selectedTab = index },
//                    text = { Text(title) }
//                )
//            }
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            when (selectedTab) {
//                0 -> LawyerListContent(lawyers)
//                1 -> JudgeListContent(judges)
//            }
//        }
//    }
//}
//
////private fun <T> Flow<T>.observeAsState(emptyList: Any): T {
//
//}
//
//@Composable
//fun LawyerListContent(lawyers: List<Lawyer>) {
//    if (lawyers.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "No lawyers found",
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//            )
//        }
//    } else {
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(lawyers) { lawyer ->
//                LawyerItem(lawyer = lawyer)
//            }
//        }
//    }
//}
//
//@Composable
//fun JudgeListContent(judges: List<Judge>) {
//    if (judges.isEmpty()) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = "No judges found",
//                style = MaterialTheme.typography.titleLarge,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//            )
//        }
//    } else {
//        LazyColumn(
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(judges) { judge ->
//                JudgeItem(judge = judge)
//            }
//        }
//    }
//}
//
//@Composable
//fun LawyerItem(lawyer: Lawyer) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Lawyer icon/avatar
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .background(MaterialTheme.colorScheme.primary, CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = lawyer.name.first().toString(),
//                    color = MaterialTheme.colorScheme.onPrimary,
//                    style = MaterialTheme.typography.titleLarge
//                )
//            }
//
//            Column(
//                modifier = Modifier
//                    .padding(start = 16.dp)
//                    .weight(1f)
//            ) {
//                Text(
//                    text = lawyer.name,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = "Specialization: ${lawyer.specialization}",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                )
//
//                Text(
//                    text = lawyer.contactInfo,
//                    style = MaterialTheme.typography.labelMedium
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun JudgeItem(judge: Judge) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Row(
//            modifier = Modifier.padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Judge icon/avatar
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .background(MaterialTheme.colorScheme.secondary, CircleShape),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = judge.name.first().toString(),
//                    color = MaterialTheme.colorScheme.onSecondary,
//                    style = MaterialTheme.typography.titleLarge
//                )
//            }
//
//            Column(
//                modifier = Modifier
//                    .padding(start = 16.dp)
//                    .weight(1f)
//            ) {
//                Text(
//                    text = judge.name,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Text(
//                    text = "Court: ${judge.court}",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                )
//
//                Text(
//                    text = judge.contactInfo,
//                    style = MaterialTheme.typography.labelMedium
//                )
//            }
//        }
//    }
//}