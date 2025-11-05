package me.augusto.todoapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun TodoDetailsDialog(
    item: TodoItem,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: (TodoItem) -> Unit
) {
    var editLabel by remember(item) { mutableStateOf(item.label) }
    var editDescription by remember(item) { mutableStateOf(item.description) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = editLabel,
                    onValueChange = { editLabel = it },
                    label = { Text("Task") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = editDescription,
                    onValueChange = { editDescription = it },
                    label = { Text("Description (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDelete) {
                        Text("Delete")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        val updatedItem = item.copy(
                            label = editLabel,
                            description = editDescription
                        )
                        onUpdate(updatedItem)
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
fun CreateTaskDialog(
    onDismiss: () -> Unit,
    onSave: (title: String, description: String) -> Unit
) {
    // These states hold the text for the new task
    var newLabel by remember { mutableStateOf("") }
    var newDescription by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // New Task Title
                OutlinedTextField(
                    value = newLabel,
                    onValueChange = { newLabel = it },
                    label = { Text("New Task") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = newLabel.isBlank() // Show error if title is empty
                )

                // New Task Description
                OutlinedTextField(
                    value = newDescription,
                    onValueChange = { newDescription = it },
                    label = { Text("Description (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp)
                )

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // Cancel Button
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Save Button
                    Button(
                        onClick = { onSave(newLabel, newDescription) },
                        // Only enable saving if the title is not empty
                        enabled = newLabel.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}