# ProgApi

GitHub Repo-- https://github.com/Dylan-Munsami/ProgApi.git 

Render.com – Creation of the API

 


 
 


 

 

GreetAndEat2 – Android Food Ordering & Rewards App

GreetAndEat2 is a modern Android application built with **Kotlin** and **XML**, designed to let users browse restaurants, place orders, and earn loyalty rewards.  
The app provides a clean, user-friendly interface and demonstrates key Android development concepts including Activities, RecyclerViews, custom Adapters, and Firebase/Room integration.

Overview

GreetAndEat2** allows users to:
- Register and log in securely.
- Browse restaurant menus.
- Add food items to a cart and view total costs.
- Access the exclusive **2000 Club** rewards program.
- Earn and claim rewards (Gold, Silver, Bronze tiers).
- Manage their profiles and view order history.

This app combines design simplicity with robust architecture for an enjoyable user experience.


Key Features 


User Authentication 

Register > Log In > Manage user Data

Menu and Ordering 

View available food items and add them to cart.

Checkout Flow 

Proceed with simulated payment or confirmation screens

Reward System 


Tiered Rewards 

Bronze, Silver, and Gold levels with customizable icons and colors.

Persistent Background 

Consistent background image across all app screens for branding

Modern Ui Components 

CardViews , RecyclerViews, Buttons, and custom XML layouts.

Firebase Ready 

Easy integration for authentication and real-time data (optional)


Tech Stack


Language > Kotlin

UI “XML Layout” with Material Design Components 

Database > “Room/Firebase Realtime Database”

Image Management “ImageView”, drawable resources 

Tools>

Android studio Gradle 





---

 Installation & Setup

1. **Clone the project**
   ```bash
   git clone https://github.com/yourusername/GreetAndEat2.git






Project Structure 

app/
├── java/
│ └── com.example.greetandeat2/
│ ├── Home.kt
│ ├── Login.kt
│ ├── Register.kt
│ ├── RewardsAdapter.kt
│ ├── Reward.kt
│ ├── RewardLevel.kt
│ └── MainMenu.kt
├── res/
│ ├── layout/
│ │ ├── activity_home.xml
│ │ ├── activity_login.xml
│ │ ├── activity_register.xml
│ │ ├── activity_rewards.xml
│ │ └── item_reward.xml
│ ├── drawable/
│ │ ├── bg_main.png
│ │ └── rounded_button.xml
│ ├── values/
│ │ ├── colors.xml
│ │ ├── styles.xml
│ │ └── strings.xml
└── AndroidManifest.xml



---
 Installation & Setup

1. **Clone the project**
   ```bash
   git clone https://github.com/yourusername/GreetAndEat2.git



2. Open in Android Studio

Open the cloned folder with Android Studio.

Let Gradle sync all dependencies automatically.


3. In your Buil.gradle:

implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("androidx.cardview:cardview:1.0.0")
implementation("com.google.android.material:material:1.12.0")

4. Add your background image

Copy your background image (bg_main.png) into:

app/src/main/res/drawable/

>>Ensure the XML references it properly:
android:src="@drawable/bg_main"


5. Optional – Connect Firebase

Create a Firebase project and download google-services.json.

Place it inside:




How It Works

-The Main Menu Activity serves as the navigation hub for users.

-The RewardsAdapter binds reward data (title, description, icon, tier) to each CardView in the RecyclerView.

-The Reward model class defines each reward’s properties and tier.

-Users can click on rewards to claim them, which triggers the onClick   callback.

-The background image is set globally for consistent branding. 


UI Design Highlights

-Consistent color palette using colors.xml

-Rounded buttons and elevated cards

-Transparent background overlay using android:alpha

-Professional typography and spacing for readability

-Tier-based color highlights for rewards



Contributors


Dylan Munsami 

Kamil Maharaj 

Rushalen Delomoneys

Idris Khan 

Imaad Kajee


📄 License

This project is released under the MIT License.
Feel free to use, modify, and distribute as long as credit is provided.



💬 Feedback

If you’d like to suggest improvements or report bugs, please open an issue or contact the project author.

GreetAndEat2 – Bringing flavor, convenience, and rewards to your fingertips
