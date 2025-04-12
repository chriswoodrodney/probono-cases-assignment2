package com.chrisy.probonocases.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chrisy.probonocases.data.Case
import com.chrisy.probonocases.data.CaseStatus
import com.chrisy.probonocases.viewmodel.CaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailScreen(
    caseId: Long,
    viewModel: CaseViewModel = viewModel(),
    onBackClick: () -> Unit,
    onEditClick: () -> Unit
) {
    LaunchedEffect(caseId) {
        viewModel.loadCaseDetail(caseId)
    }

    val caseDetail by viewModel.currentCaseDetail.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Case Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            caseDetail?.let { case ->
                CaseDetailContent(case = case, onStatusChange = { newStatus ->
                    viewModel.updateCaseStatus(case.id, newStatus)
                })
            } ?: run {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CaseDetailContent(
    case: Case,
    onStatusChange: (CaseStatus) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Case title
        Text(
            text = case.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Status badge
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Status: ",
                style = MaterialTheme.typography.titleMedium
            )

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
                },
                modifier = Modifier.padding(start = 4.dp)
            ) {
                Text(
                    text = case.status.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Client information
        SectionTitle(title = "Client Information")
        DetailItem(label = "Name", value = case.clientName)

        Spacer(modifier = Modifier.height(16.dp))

        // Case details
        SectionTitle(title = "Case Details")
        DetailItem(label = "Description", value = case.description)

        if (case.courtDate != null) {
            DetailItem(
                label = "Court Date",
                value = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(Date(case.courtDate))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // People involved
        SectionTitle(title = "People Involved")
        DetailItem(label = "Lawyer ID", value = "ID: ${case.lawyerId}")
        case.judgeId?.let {
            DetailItem(label = "Judge ID", value = "ID: $it")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Status update section
        if (case.status == CaseStatus.PENDING) {
            SectionTitle(title = "Update Case Status")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusButton(
                    text = "Won",
                    color = Color(0xFF4CAF50),
                    onClick = { onStatusChange(CaseStatus.WON) },
                    modifier = Modifier.weight(1f)
                )

                StatusButton(
                    text = "Lost",
                    color = Color(0xFFF44336),
                    onClick = { onStatusChange(CaseStatus.LOST) },
                    modifier = Modifier.weight(1f)
                )

                StatusButton(
                    text = "Dismissed",
                    color = Color(0xFF9E9E9E),
                    onClick = { onStatusChange(CaseStatus.DISMISSED) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Timeline/History
        SectionTitle(title = "Timeline")
        Text(
            text = "Case created on ${
                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(Date(case.dateCreated))
            }",
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "Last updated on ${
                SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                    .format(Date(case.lastUpdated))
            }",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun StatusButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}