package com.chrisy.probonocases.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chrisy.probonocases.data.Case
import com.chrisy.probonocases.data.CaseStatus

/**
 * Shared component for displaying a case item in a list.
 */
@Composable
fun CaseListItem(
    case: Case,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Client: ${case.clientName}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Surface(
                shape = MaterialTheme.shapes.small,
                color = when (case.status) {
                    CaseStatus.PENDING -> Color(0xFFFFA000)
                    CaseStatus.WON -> Color(0xFF4CAF50)
                    CaseStatus.LOST -> Color(0xFFF44336)
                    CaseStatus.DISMISSED -> Color(0xFF9E9E9E)
                    CaseStatus.NEW -> Color(0xFF2196F3)
                    CaseStatus.ASSIGNED -> Color(0xFF673AB7)
                    CaseStatus.IN_PROGRESS -> Color(0xFF03A9F4)
                    CaseStatus.CLOSED -> Color(0xFF795548)
                }
            ) {
                Text(
                    text = case.status.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}