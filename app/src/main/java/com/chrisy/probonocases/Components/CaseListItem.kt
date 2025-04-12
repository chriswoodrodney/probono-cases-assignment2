package com.chrisy.probonocases.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chrisy.probonocases.data.Case
import com.chrisy.probonocases.data.CaseStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CaseListItem(
    case: Case,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Case title
                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                // Status badge
                CaseStatusBadge(status = case.status)
            }

            // Client name
            Text(
                text = "Client: ${case.clientName}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Case description (truncated)
            if (!case.description.isNullOrEmpty()) {
                Text(
                    text = case.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Court date
            case.courtDate?.let {
                Text(
                    text = "Court Date: ${formatDate(it)}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun CaseStatusBadge(status: CaseStatus) {
    val color = when (status) {
        CaseStatus.PENDING -> Color(0xFFFFA000) // Amber
        CaseStatus.WON -> Color(0xFF4CAF50) // Green
        CaseStatus.LOST -> Color(0xFFF44336) // Red
        CaseStatus.DISMISSED -> Color(0xFF9E9E9E) // Gray
        CaseStatus.NEW -> TODO()
        CaseStatus.ASSIGNED -> TODO()
        CaseStatus.IN_PROGRESS -> TODO()
        CaseStatus.CLOSED -> TODO()
    }

    Surface(
        color = color,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status.name,
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

private fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatter.format(date)
}