package com.example.greetandeat2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputEditText

class PaymentMethodsActivity : AppCompatActivity() {

    private lateinit var cardListLayout: LinearLayout
    private lateinit var addCardButton: Button
    private val prefsName = "user_cards"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_methods)

        cardListLayout = findViewById(R.id.cardListLayout)
        addCardButton = findViewById(R.id.addCardButton)

        loadCards()

        addCardButton.setOnClickListener {
            showAddCardDialog()
        }
    }

    private fun loadCards() {
        cardListLayout.removeAllViews()
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        val cards = prefs.getStringSet("cards", emptySet()) ?: emptySet()

        if (cards.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No cards added"
            emptyText.textSize = 16f
            emptyText.setPadding(16, 16, 16, 16)
            cardListLayout.addView(emptyText)
        } else {
            for (card in cards) {
                val cardView = CardView(this)
                cardView.radius = 20f
                cardView.cardElevation = 8f
                cardView.setContentPadding(24, 24, 24, 24)
                cardView.setCardBackgroundColor(resources.getColor(R.color.purple_200))
                cardView.useCompatPadding = true

                val layout = LinearLayout(this)
                layout.orientation = LinearLayout.VERTICAL

                val parts = card.split("|")
                val number = parts[0]
                val name = if (parts.size > 1) parts[1] else ""
                val expiry = if (parts.size > 2) parts[2] else ""

                val cardNumberText = TextView(this)
                cardNumberText.text = "**** **** **** ${number.takeLast(4)}"
                cardNumberText.textSize = 18f
                cardNumberText.setTextColor(resources.getColor(android.R.color.white))
                cardNumberText.setPadding(0,0,0,8)
                layout.addView(cardNumberText)

                val nameText = TextView(this)
                nameText.text = name
                nameText.textSize = 14f
                nameText.setTextColor(resources.getColor(android.R.color.white))
                layout.addView(nameText)

                val expiryText = TextView(this)
                expiryText.text = "Expiry: $expiry"
                expiryText.textSize = 14f
                expiryText.setTextColor(resources.getColor(android.R.color.white))
                expiryText.setPadding(0,8,0,0)
                layout.addView(expiryText)

                val deleteBtn = Button(this)
                deleteBtn.text = "Delete"
                deleteBtn.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
                deleteBtn.setTextColor(resources.getColor(android.R.color.white))
                deleteBtn.setPadding(0,16,0,0)
                deleteBtn.setOnClickListener {
                    val newCards = cards.toMutableSet()
                    newCards.remove(card)
                    prefs.edit().putStringSet("cards", newCards).apply()
                    loadCards()
                }
                layout.addView(deleteBtn)

                cardView.addView(layout)

                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, 16, 0, 16)
                cardListLayout.addView(cardView, layoutParams)
            }
        }
    }

    private fun showAddCardDialog() {
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_add_card, null)

        val cardNumberInput = view.findViewById<TextInputEditText>(R.id.cardNumber)
        val cardNameInput = view.findViewById<TextInputEditText>(R.id.cardName)
        val cardExpiryInput = view.findViewById<TextInputEditText>(R.id.cardExpiry)
        val cardCvvInput = view.findViewById<TextInputEditText>(R.id.cardCvv)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add Card")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val cardNumber = cardNumberInput.text.toString()
                val cardName = cardNameInput.text.toString()
                val cardExpiry = cardExpiryInput.text.toString()
                val cardCvv = cardCvvInput.text.toString()

                if (cardNumber.length == 16 && cardName.isNotBlank() && cardExpiry.isNotBlank() && cardCvv.length in 3..4) {
                    val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
                    val cards = prefs.getStringSet("cards", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
                    cards.add("$cardNumber|$cardName|$cardExpiry")
                    prefs.edit().putStringSet("cards", cards).apply()
                    loadCards()
                } else {
                    Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
