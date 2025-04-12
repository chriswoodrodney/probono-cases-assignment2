package com.chrisy.probonocases.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chrisy.probonocases.data.Case
import com.chrisy.probonocases.data.CaseStatus.ASSIGNED
import com.chrisy.probonocases.data.CaseStatus.CLOSED
import com.chrisy.probonocases.data.CaseStatus.DISMISSED
import com.chrisy.probonocases.data.CaseStatus.IN_PROGRESS
import com.chrisy.probonocases.data.CaseStatus.LOST
import com.chrisy.probonocases.data.CaseStatus.NEW
import com.chrisy.probonocases.data.CaseStatus.PENDING
import com.chrisy.probonocases.data.CaseStatus.WON
import com.chrisy.probonocases.viewmodel.CaseViewModel

@Composable
fun DashboardScreen(
    viewModel: CaseViewModel = viewModel(),
    onCaseClick: (Long) -> Unit
) {
    val cases by viewModel.allCases.observeAsState(emptyList())
    val pendingCases = cases.filter { it.status == PENDING }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Pro Bono Case Tracker",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Case Summary", style = MaterialTheme.typography.titleLarge)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CaseSummaryItem("Total", cases.size, MaterialTheme.colorScheme.primary)
                    CaseSummaryItem("Pending", pendingCases.size, Color(0xFFFFA000))
                    CaseSummaryItem("Won", cases.count { it.status == WON }, Color(0xFF4CAF50))
                    CaseSummaryItem("Lost", cases.count { it.status == LOST }, Color(0xFFF44336))
                }
            }
        }

        Text(
            text = "Recent Cases",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn {
            items(pendingCases.take(5)) { case ->
                CaseListItemDashboard(case = case, onClick = { onCaseClick(case.id) })
            }
        }
    }
}

@Composable
fun CaseSummaryItem(
    title: String,
    count: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count.toString(), style = MaterialTheme.typography.headlineMedium, color = color)
        Text(text = title, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun CaseListItemDashboard(
    case: Case,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .background(
                        color = when (case.status) {
                            PENDING -> Color(0xFFFFA000)
                            WON -> Color(0xFF4CAF50)
                            LOST -> Color(0xFFF44336)
                            DISMISSED -> Color(0xFF9E9E9E)
                            NEW -> Color(0xFF2196F3)
                            ASSIGNED -> Color(0xFF673AB7)
                            IN_PROGRESS -> Color(0xFF03A9F4)
                            CLOSED -> Color(0xFF795548)
                        },
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = case.clientName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                )
            }

            Text(
                text = case.status.name,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
