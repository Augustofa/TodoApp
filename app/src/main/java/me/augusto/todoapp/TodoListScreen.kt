package me.augusto.todoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TodoListScreen() {
    val todoItems = remember {
        mutableStateListOf(
            TodoItem(label = "Buy groceries"),
            TodoItem(label = "Walk the dog", isChecked = true),
            TodoItem(label = "Complete Mobile Development Assignment", description = "Complete the mobile To-Do app using Jetpack Compose and present it in class")
        )
    }

    var selectedItem by remember { mutableStateOf<TodoItem?>(null) }
    var showCreateDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Tasks",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 32.dp, 32.dp)
                    .size(64.dp),
                onClick = { showCreateDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            LazyColumn (
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = todoItems,
                    key = { it.id }
                ) { item ->
                    TodoItemRow(
                        item = item,
                        onItemChecked = { isChecked ->
                            val index = todoItems.indexOf(item)
                            if (index >= 0) {
                                todoItems[index] = item.copy(isChecked = isChecked)
                            }
                        },
                        onItemClicked = {
                            selectedItem = item
                        }
                    )
                }
            }
        }

        if (selectedItem != null) {
            TodoDetailsDialog(
                item = selectedItem!!,
                onDismiss = { selectedItem = null },
                onDelete = {
                    todoItems.remove(selectedItem)
                    selectedItem = null
                },
                onUpdate = { updatedItem: TodoItem ->
                    val index = todoItems.indexOfFirst { it.id == updatedItem.id }
                    if (index != -1) {
                        todoItems[index] = updatedItem
                    }
                    selectedItem = null
                }
            )
        }

        if (showCreateDialog) {
            CreateTaskDialog(
                onDismiss = { showCreateDialog = false },
                onSave = { title: String, description: String ->
                    todoItems.add(0, TodoItem(label = title, description = description))
                    showCreateDialog = false
                }
            )
        }
    }
}


@Composable
fun TodoItemRow(
    item: TodoItem,
    onItemChecked: (Boolean) -> Unit,
    onItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onItemClicked() }, // Make the whole card clickable
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = onItemChecked
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.label,
                textDecoration = if (item.isChecked) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
