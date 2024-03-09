package com.example.taskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    private lateinit var editTextItem: EditText
    private lateinit var textViewEmpty: TextView
    private lateinit var buttonAdd: Button
    private lateinit var buttonDelete: Button
    private lateinit var linearLayoutItems: LinearLayout
    private lateinit var selectedItems: MutableList<String>
    private lateinit var buttonDone: Button
    private lateinit var linearLayoutCompletedItems: LinearLayout
    private lateinit var textViewEmptyCompleted: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextItem = findViewById(R.id.editTextItem)
        textViewEmpty = findViewById(R.id.textViewEmpty)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonDelete = findViewById(R.id.buttonDelete)
        linearLayoutItems = findViewById(R.id.linearLayoutItems)
        buttonDone = findViewById(R.id.buttonDone)
        linearLayoutCompletedItems = findViewById(R.id.linearLayoutCompletedItems)
        textViewEmptyCompleted = findViewById(R.id.textViewEmptyCompleted)

        selectedItems = mutableListOf()

        buttonAdd.setOnClickListener {
            val itemName = editTextItem.text.toString().trim()

            if(itemName.isEmpty()) {
                editTextItem.error = "Please enter task name"
                return@setOnClickListener
            }
            addItem()
        }
        buttonDone.setOnClickListener {
            moveCompletedItems()
        }

        buttonDelete.setOnClickListener {
            deleteSelectedItems()
        }
    }

    private fun addItem() {
        val itemName = editTextItem.text.toString()

        if (itemName.isNotEmpty()) {
            // Create a new CheckBox for the new item
            val checkBox = CheckBox(this)
            checkBox.text = itemName

            // Add a Padding on the linearLayoutItems
            linearLayoutItems.setPadding(0, 16, 0, 16)

            // Add the CheckBox to the LinearLayout
            linearLayoutItems.addView(checkBox)

            // Clear the EditText
            editTextItem.text.clear()

            // Hide the textViewEmpty
            textViewEmpty.visibility = View.GONE
        }
    }

    private fun moveCompletedItems() {
        // Create a list to hold the CheckBox views that need to be moved
        val checkBoxesToMove = mutableListOf<CheckBox>()

        // Loop through all the child views of the LinearLayout
        for (i in 0 until linearLayoutItems.childCount) {
            val view = linearLayoutItems.getChildAt(i)

            // Check if the child view is a CheckBox and if it's checked
            if (view is CheckBox && view.isChecked) {
                // Add the CheckBox to the list of views to move
                checkBoxesToMove.add(view)

                // Remove the item from the selected items list
                selectedItems.remove(view.text.toString())
            }
        }

        // Add a Padding on the linearLayoutItems
        linearLayoutCompletedItems.setPadding(0, 16, 0, 16)

        // Move all the CheckBox views that need to be moved
        for (checkBox in checkBoxesToMove) {
            linearLayoutItems.removeView(checkBox)
            linearLayoutCompletedItems.addView(checkBox)
        }

        // Clear the list of CheckBox views to move
        checkBoxesToMove.clear()

        // Show the Empty Text
        if(linearLayoutItems.childCount == 0){
            textViewEmpty.visibility = View.VISIBLE
        }

        // Remove a Padding on the linearLayoutItems
        linearLayoutItems.setPadding(0, 0, 0, 0)

        // Check if the completed task section is empty
        if (linearLayoutCompletedItems.childCount == 0) {
            // Show the empty message
            textViewEmptyCompleted.visibility = View.VISIBLE
        } else {
            // Hide the empty message
            textViewEmptyCompleted.visibility = View.GONE
        }
    }

    private fun deleteSelectedItems() {
        // Create a list to hold the CheckBox views that need to be removed
        val checkBoxesToRemove = mutableListOf<CheckBox>()

        // Loop through all the child views of the LinearLayout
        for (i in 0 until linearLayoutItems.childCount) {
            val view = linearLayoutItems.getChildAt(i)

            // Check if the child view is a CheckBox and if it's checked
            if (view is CheckBox && view.isChecked) {

                // Add the CheckBox to the list of views to remove
                checkBoxesToRemove.add(view)

                // Remove the item from the selected items list
                selectedItems.remove(view.text.toString())
            }
        }

        // Remove all the CheckBox views that need to be removed
        for (checkBox in checkBoxesToRemove) {
            linearLayoutItems.removeView(checkBox)
        }

        // Clear the list of CheckBox views to remove
        checkBoxesToRemove.clear()

        // Show the Empty Text
        if(linearLayoutItems.childCount == 0){
            textViewEmpty.visibility = View.VISIBLE
        }

        // Remove a Padding on the linearLayoutItems
        linearLayoutItems.setPadding(0, 0, 0, 0)
    }
}