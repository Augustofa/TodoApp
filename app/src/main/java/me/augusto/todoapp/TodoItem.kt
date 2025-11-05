package me.augusto.todoapp

import java.util.UUID

data class TodoItem (
    val id: UUID = UUID.randomUUID(),
    val label: String,
    val description: String = "",
    val isChecked: Boolean = false,
)