//package com.chrisy.probonocases.screens
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.material.icons.filled.Close
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.Button
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.chrisy.probonocases.data.Case
//import com.chrisy.probonocases.data.CaseStatus
//import com.chrisy.probonocases.data.Judge
//import com.chrisy.probonocases.viewmodel.CaseViewModel
//import com.chrisy.probonocases.viewmodel.LawyerJudgeViewModel
//import kotlinx.coroutines.flow.forEach
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddEditCaseScreen(
//    caseId: Long?,
//    viewModel: CaseViewModel = viewModel(),
//    lawyerJudgeViewModel: LawyerJudgeViewModel = viewModel(),
//    onSaveComplete: () -> Unit,
//    onCancelClick: () -> Unit
//) {
//    val isEditMode = caseId != null
//    val screenTitle = if (isEditMode) "Edit Case" else "Add New Case"
//
//    // Load case data if in edit mode
//    if (isEditMode) {
//        LaunchedEffect(caseId) {
//            viewModel.loadCaseDetail(caseId!!)
//        }
//    }
//
//    val currentCase by viewModel.currentCaseDetail.observeAsState()
//
//    // Form state
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var clientName by remember { mutableStateOf("") }
//    var status by remember { mutableStateOf(CaseStatus.PENDING) }
//    var courtDate by remember { mutableStateOf<Long?>(null) }
//    var selectedLawyerId by remember { mutableStateOf<Long?>(null) }
//    var selectedJudgeId by remember { mutableStateOf<Long?>(null) }
//
//    // Observe lawyers and judges for dropdowns
//    val lawyers by lawyerJudgeViewModel.allLawyers.observeAsState(emptyList())
//    val judges = lawyerJudgeViewModel.allJudges
//
//    // Initialize form with case data when available
//    LaunchedEffect(currentCase) {
//        currentCase?.let { case ->
//            title = case.title
//            description = case.description
//            clientName = case.clientName
//            status = case.status
//            courtDate = case.courtDate
//            selectedLawyerId = case.lawyerId
//            selectedJudgeId = case.judgeId
//        }
//    }
//
//    // Date picker state
//    var showDatePicker by remember { mutableStateOf(false) }
//
//    // Dropdown state
//    var showLawyerDropdown by remember { mutableStateOf(false) }
//    var showJudgeDropdown by remember { mutableStateOf(false) }
//    var showStatusDropdown by remember { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(screenTitle) },
//                navigationIcon = {
//                    IconButton(onClick = onCancelClick) {
//                        Icon(Icons.Default.Close, contentDescription = "Cancel")
//                    }
//                },
//                actions = {
//                    // Save button
//                    IconButton(
//                        onClick = {
//                            if (title.isNotBlank() && clientName.isNotBlank() && selectedLawyerId != null) {
//                                val updatedCase = Case(
//                                    id = currentCase?.id ?: 0L,
//                                    title = title,
//                                    description = description,
//                                    clientName = clientName,
//                                    status = status,
//                                    courtDate = courtDate,
//                                    lawyerId = selectedLawyerId!!,
//                                    judgeId = selectedJudgeId,
//                                    dateCreated = currentCase?.dateCreated ?: System.currentTimeMillis(),
//                                    lastUpdated = System.currentTimeMillis()
//                                )
//                                viewModel.saveCase(updatedCase)
//                                onSaveComplete()
//                            }
//                        }
//                    ) {
//                        Icon(Icons.Default.Check, contentDescription = "Save")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp)
//                .verticalScroll(rememberScrollState())
//        ) {
//            // Title input
//            OutlinedTextField(
//                value = title,
//                onValueChange = { title = it },
//                label = { Text("Case Title *") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                isError = title.isBlank()
//            )
//
//            // Client name input
//            OutlinedTextField(
//                value = clientName,
//                onValueChange = { clientName = it },
//                label = { Text("Client Name *") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                isError = clientName.isBlank()
//            )
//
//            // Description input
//            OutlinedTextField(
//                value = description,
//                onValueChange = { description = it },
//                label = { Text("Case Description") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp)
//                    .height(120.dp),
//                maxLines = 5
//            )
//
//            // Status dropdown (only for edit mode)
//            if (isEditMode) {
//                Text(
//                    text = "Status",
//                    style = MaterialTheme.typography.bodySmall,
//                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
//                )
//
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { showStatusDropdown = true }
//                        .padding(bottom = 8.dp),
//                    color = when (status) {
//                        CaseStatus.PENDING -> Color(0xFFFFA000)
//                        CaseStatus.WON -> Color(0xFF4CAF50)
//                        CaseStatus.LOST -> Color(0xFFF44336)
//                        CaseStatus.DISMISSED -> Color(0xFF9E9E9E)
//                        else -> MaterialTheme.colorScheme.surface
//                    },
//                    shape = MaterialTheme.shapes.small
//                ) {
//                    Text(
//                        text = status.name,
//                        color = Color.White,
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
//                    )
//                }
//
//                // Status dropdown menu
//                DropdownMenu(
//                    expanded = showStatusDropdown,
//                    onDismissRequest = { showStatusDropdown = false }
//                ) {
//                    CaseStatus.entries.forEach { statusOption ->
//                        DropdownMenuItem(
//                            text = { Text(statusOption.name) },
//                            onClick = {
//                                status = statusOption
//                                showStatusDropdown = false
//                            }
//                        )
//                    }
//                }
//            }
//
//            // Court date picker
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Court Date",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.weight(1f)
//                )
//
//                Button(
//                    onClick = { showDatePicker = true }
//                ) {
//                    Text(
//                        text = if (courtDate != null) {
//                            SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
//                                .format(Date(courtDate!!))
//                        } else {
//                            "Select Date"
//                        }
//                    )
//                }
//
//                if (courtDate != null) {
//                    IconButton(
//                        onClick = { courtDate = null }
//                    ) {
//                        Icon(Icons.Default.Clear, contentDescription = "Clear date")
//                    }
//                }
//            }
//
//            // Lawyer selection
//            Text(
//                text = "Assigned Lawyer *",
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
//            )
//
//            if (lawyers.isNotEmpty()) {
//                // Fix here: Convert Int to Long when assigning
//                val selectedLawyer = lawyers.find { it.id.toLong() == selectedLawyerId }
//
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { showLawyerDropdown = true },
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//                    shape = MaterialTheme.shapes.small
//                ) {
//                    Row(
//                        modifier = Modifier.padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = selectedLawyer?.name ?: "Select a lawyer",
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//
//                        Icon(
//                            Icons.Default.ArrowDropDown,
//                            contentDescription = "Select lawyer"
//                        )
//                    }
//                }
//
//                // Lawyer dropdown menu
//                DropdownMenu(
//                    expanded = showLawyerDropdown,
//                    onDismissRequest = { showLawyerDropdown = false }
//                ) {
//                    lawyers.forEach { lawyer ->
//                        DropdownMenuItem(
//                            text = { Text(lawyer.name) },
//                            onClick = {
//                                // Fix here: Convert Int to Long
//                                selectedLawyerId = lawyer.id.toLong()
//                                showLawyerDropdown = false
//                            }
//                        )
//                    }
//                }
//            } else {
//                Text(
//                    text = "No lawyers available",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//
//            // Judge selection
//            Text(
//                text = "Assigned Judge",
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
//            )
//
////            if (judges.isNotEmpty()) {
////                // Fix here: Convert Int to Long when assigning
////                val selectedJudge = judges.find { it.id.toLong() == selectedJudgeId }
//
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { showJudgeDropdown = true },
//                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
//                    shape = MaterialTheme.shapes.small
//                ) {
//                    Row(
//                        modifier = Modifier.padding(16.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        val selectedJudge = Judge(
//                            id = 1,
//                            name = "steven",
//                            court = "the bad one",
//                            contactInfo = "nah"
//                        )
//                            Text(
//                            text = selectedJudge.name ?: "Select a judge (optional)",
//                            style = MaterialTheme.typography.bodyMedium
//                        )
//
//                        Icon(
//                            Icons.Default.ArrowDropDown,
//                            contentDescription = "Select judge"
//                        )
//                    }
//                }
//
//                // Judge dropdown menu
//                DropdownMenu(
//                    expanded = showJudgeDropdown,
//                    onDismissRequest = { showJudgeDropdown = false }
//                ) {
//                    judges.collect { judge ->
//                        DropdownMenuItem(
//                            text = { Text(judge.name) },
//                            onClick = {
//                                // Fix here: Convert Int to Long
//                                selectedJudgeId = judge.id.toLong()
//                                showJudgeDropdown = false
//                            }
//                        )
//                    }
//                }
////            } else {
////                Text(
////                    text = "No judges available",
////                    style = MaterialTheme.typography.bodyMedium,
////                    color = MaterialTheme.colorScheme.onSurfaceVariant
////                )
////            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Form validation message
//            if (title.isBlank() || clientName.isBlank() || selectedLawyerId == null) {
//                Text(
//                    text = "* Required fields",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//    }
//
//    // Date picker dialog (simplified)
//    if (showDatePicker) {
//        // In a real implementation, you would use a DatePickerDialog here
//        // For simplicity, I'm just showing how it would be structured
//        AlertDialog(
//            onDismissRequest = { showDatePicker = false },
//            title = { Text("Select Court Date") },
//            text = { Text("Date picker would appear here") },
//            confirmButton = {
//                TextButton(onClick = {
//                    // Would set courtDate to selected date
//                    courtDate = System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000) // Example: 1 week from now
//                    showDatePicker = false
//                }) {
//                    Text("OK")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDatePicker = false }) {
//                    Text("Cancel")
//                }
//            }
//        )
//    }
//}
//
//private fun <T> Flow<T>.observeAsState(emptyList: Any): T {
//
//}
