package com.example.greetandeat2

import android.content.Context
import android.content.SharedPreferences
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
    private lateinit var backButton: Button

    private lateinit var currentUserEmail: String
    private val prefsPrefix = "user_cards_"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_methods)

        cardListLayout = findViewById(R.id.cardListLayout)
        addCardButton = findViewById(R.id.addCardButton)
        backButton = findViewById(R.id.backButton)

        // Back button functionality
        backButton.setOnClickListener { finish() }

        // Get current user
        val userPrefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        currentUserEmail = userPrefs.getString("lastUserEmail", "") ?: ""
        if (currentUserEmail.isEmpty()) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadCards()

        addCardButton.setOnClickListener {
            showAddCardDialog()
        }
    }

    private fun getUserPrefs(): SharedPreferences {
        val safeEmail = currentUserEmail.replace(".", "_")
        return getSharedPreferences("$prefsPrefix$safeEmail", Context.MODE_PRIVATE)
    }

    private fun loadCards() {
        cardListLayout.removeAllViews()
        val prefs = getUserPrefs()
        val cardsSet = prefs.getStringSet("cards", emptySet()) ?: emptySet()
        val cards = cardsSet.toMutableList()

        if (cards.isEmpty()) {
            val emptyText = TextView(this)
            emptyText.text = "No cards added"
            emptyText.textSize = 16f
            emptyText.setPadding(16, 16, 16, 16)
            cardListLayout.addView(emptyText)
        } else {
            cards.forEach { card ->
                val cardView = CardView(this).apply {
                    radius = 20f
                    cardElevation = 8f
                    setCardBackgroundColor(resources.getColor(R.color.purple_500))
                    useCompatPadding = true
                    setContentPadding(24, 24, 24, 24)
                }

                val layout = LinearLayout(this)
                layout.orientation = LinearLayout.VERTICAL

                val parts = card.split("|")
                val number = parts.getOrElse(0) { "" }
                val name = parts.getOrElse(1) { "" }
                val expiry = parts.getOrElse(2) { "" }

                val numberText = TextView(this)
                numberText.text = "**** **** **** ${number.takeLast(4)}"
                numberText.textSize = 18f
                numberText.setTextColor(resources.getColor(android.R.color.white))
                numberText.setPadding(0, 0, 0, 8)

                val nameText = TextView(this)
                nameText.text = name
                nameText.textSize = 14f
                nameText.setTextColor(resources.getColor(android.R.color.white))

                val expiryText = TextView(this)
                expiryText.text = "Expiry: $expiry"
                expiryText.textSize = 14f
                expiryText.setTextColor(resources.getColor(android.R.color.white))
                expiryText.setPadding(0, 8, 0, 0)

                val deleteBtn = Button(this)
                deleteBtn.text = "Delete"
                deleteBtn.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
                deleteBtn.setTextColor(resources.getColor(android.R.color.white))
                deleteBtn.setOnClickListener {
                    cards.remove(card)
                    prefs.edit().putStringSet("cards", cards.toSet()).apply()
                    loadCards()
                }

                layout.addView(numberText)
                layout.addView(nameText)
                layout.addView(expiryText)
                layout.addView(deleteBtn)

                cardView.addView(layout)

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 16, 0, 16)
                cardListLayout.addView(cardView, params)
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

        AlertDialog.Builder(this)
            .setTitle("Add Card")
            .setView(view)
            .setPositiveButton("Add") { _, _ ->
                val number = cardNumberInput.text.toString()
                val name = cardNameInput.text.toString()
                val expiry = cardExpiryInput.text.toString()
                val cvv = cardCvvInput.text.toString()

                if (number.length == 16 && name.isNotBlank() && expiry.isNotBlank() && cvv.length in 3..4) {
                    val prefs = getUserPrefs()
                    val cardsSet = prefs.getStringSet("cards", emptySet())?.toMutableSet() ?: mutableSetOf()
                    cardsSet.add("$number|$name|$expiry")
                    prefs.edit().putStringSet("cards", cardsSet).apply()
                    loadCards()
                } else {
                    Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
