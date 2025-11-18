GreetAndEat2 – Food Ordering, Rewards & Delivery Tracking App

Final PROG7314 POE Submission
Android | Kotlin | Firebase | Render API | RoomDB | GitHub Actions


🚀 Project Overview

GreetAndEat2 is a modern Android food-ordering application designed for the PROG7314 module.
The app allows users to browse restaurants, place orders, track deliveries in real time, earn loyalty rewards, and enjoy offline functionality through RoomDB.

This project demonstrates full-stack mobile development using:

Firebase Authentication (SSO login, secure identity)

Firebase Cloud Messaging (FCM) (real-time notifications)

Render-hosted REST API (order creation & order tracking)

RoomDB (offline mode & local caching)

GitHub Actions (automated testing & CI builds)

Material Design 3 UI

The application is fully functional, runs on a real Android device, and meets all PROG7314 POE requirements.



How to Run the Application

This section explains how to build, install, and run GreetAndEat2 on a real Android device, as required for the PROG7314 POE.

Prerequisites

Before running the app, ensure you have:

✅ Android Studio (latest version)

https://developer.android.com/studio

✅ A real Android device

Android 8.0 (API 26) or higher

At least 1GB free storage

Developer mode enabled

❗ Required for POE

The app must run on a mobile device — not the emulator.



Clone the Repository

Open a terminal or Git Bash:
git clone https://github.com/Dylan-Munsami/Prog7314

Open the project in Android Studio:
File → Open → Select “GreetAndEat2” Folder  (Gradle will automatically download and sync all dependencies.)




Configure Firebase (Optional but recommended)

If the app detects that Firebase is not configured, it falls back to offline mode.

To enable full features:

1-Go to https://console.firebase.google.com

2-Create a new Firebase project

3-Add an Android app

4-Download google-services.json


5-Place the file into:

app/src/main/

Sync Gradle again.



Build the Project (APK)

In Android Studio:

Build → Build Bundle(s) / APK(s) → Build APK(s)


When the build completes, click locate to find your APK:

/app/release/app-release.apk

5️⃣ Enable Developer Mode on Your Phone

On your Android device:

Settings → About Phone → Tap “Build Number” 7 times  
Settings → Developer Options → Enable USB Debugging

6️⃣ Install the APK on a Real Device
Method A — Android Studio (Recommended)

Connect your phone via USB

Select your device from Device Manager

Click Run ▶

Method B — Manual Installation

Transfer the APK to your phone

Open the APK

Allow "Install from unknown sources"

Tap Install

7️⃣ Running the API (ProgApi)
Option A — Use the hosted Render API (already deployed)

Nothing to set up — the app connects automatically to:

https://progapi-service.onrender.com

Option B — Run locally (optional)
git clone https://github.com/Dylan-Munsami/ProgApi.git
npm install
npm start


Then update BASE_URL inside the app:

const val BASE_URL = "http://10.0.2.2:3000/"

8️⃣ Log In / Test Accounts

You can register a new account directly from the app.

OR use existing test accounts:

Email:   testuser@gmail.com
Pass:    Test1234


Biometric authentication appears after first successful login.

9️⃣ Using the App

Once installed:

Log in / register

Select a restaurant

Add items to cart

Checkout

Track order with live updates

Receive push notifications

Offline mode automatically activates if the device loses internet.

🔧 Troubleshooting
Issue	Fix
Firebase not connecting	Ensure google-services.json is added correctly
API not responding	Use the Render URL or start local server
Notifications not arriving	Ensure device has Google Play Services
Biometric prompt not showing	Register biometrics in the phone settings





🏗 System Architecture

GreetAndEat2 uses a Hybrid Cloud + Offline-First Architecture:



| Layer                  | Technology                 | Purpose                               |
| ---------------------- | -------------------------- | ------------------------------------- |
| **Frontend (Android)** | Kotlin, XML, Material 3    | UI, app logic, user interactions      |
| **Backend API**        | Render (REST API)          | Order creation, updates, tracking     |
| **Cloud Services**     | Firebase Auth + FCM        | Authentication + notifications        |
| **Local Storage**      | RoomDB                     | Offline mode, caching, cart & history |
| **Security**           | EncryptedSharedPreferences | Protect stored credentials            |


🔄 High-Level Flow

User logs in (Firebase Auth / Biometrics)

App fetches restaurants & menu

Order is created → API (Render)

Realtime updates → FCM notifications

Offline data stored in RoomDB

User tracks order progress visually








🔑 Core Features
1️⃣ User Authentication

Email + Password login

Google Sign-In

Biometric login (Fingerprint / FaceID)

Secure token handling with EncryptedSharedPreferences

Firebase Authentication backend

2️⃣ Menu & Ordering

Browse restaurants

View menu items

Add items to cart

Cart persistence even offline

Checkout flow with payment simulation

3️⃣ Order Management & Tracking

Orders sent to custom Render API

Order ID returned to the app

Real-time order progress (Ordered → Preparing → Ready → Delivered)

Timeline & progress bar

4️⃣ Real-Time Notifications

FCM push notifications for order status

Backend triggered messages

Works in background

5️⃣ Rewards System

Tiered points system (Bronze / Silver / Gold)

Auto-calculated from order activity

Stored in RoomDB for offline visibility

6️⃣ Offline Mode (RoomDB)

Local caching of:

Orders

Cart items

Rewards

User activity

App functions without internet

Syncs when connection is restored

7️⃣ Mini-Game: Food Delivery Dash

Custom arcade-style mini-game

Points & best score tracking

Adds gamification to the app

8️⃣ Multi-Language Support

English

Afrikaans

isiXhosa

9️⃣ Settings & Personalisation

Edit profile

Change password

Theme toggle (Dark Mode)

Notification toggle

Language selector



🌐 Backend (ProgApi) – Render-Hosted REST API
🔗 Repository

https://github.com/Dylan-Munsami/ProgApi.git

The API handles:
✔ Creating orders
✔ Updating order statuses
✔ Returning order information
✔ Triggering notifications (via Firebase)

All requests are performed over HTTPS for security.

🗄 Database Structure
🔹 Firebase Authentication

Stores user credentials

Generates secure tokens

Manages sessions

🔹 RoomDB (Local NoSQL Database)

Tables include:

orders

cart_items

rewards

recent_activities

Used for:
✔ Offline order history
✔ Cart persistence
✔ Fast local reads
✔ Efficient caching

🔔 Notifications Workflow (FCM)

Triggered when:

Order status changes

Rewards are unlocked

FCM sends push notifications directly to the user device.

🔧 GitHub Actions – Automated Testing

The repository includes:

CI pipeline to build the APK

Automated unit test runner

Workflow file (build.yml)

Purpose:

Ensures code builds correctly on every push

Validates functionality across devices

Satisfies POE automation requirement







📥 Installation Guide
1. Clone the project
git clone https://github.com/Dylan-Munsami/GreetAndEat2.git

2. Open Project

Open with Android Studio → let Gradle sync.

3. Add Dependencies

Required libraries:

implementation("androidx.recyclerview:recyclerview:1.3.2")
implementation("androidx.cardview:cardview:1.0.0")
implementation("com.google.android.material:material:1.12.0")

4. Firebase Setup (Optional)

Add google-services.json inside:

/app/src/main/

5. Run on a real device

App is optimized for mobile hardware (POE requirement).

🛠 Project Structure
app/
├── java/
│   └── com.example.greetandeat2/
│       ├── activities/
│       ├── adapters/
│       ├── models/
│       ├── offline/
│       ├── api/
│       └── utils/
├── res/
│   ├── layout/
│   ├── drawable/
│   ├── values/
└── AndroidManifest.xml

🗒 Release Notes – Final POE Version
Part 1 – Planning & Design

Completed app design document

UI mockups

API architecture & UML diagrams

Feature lists & requirements

Part 2 – Prototype Development

Implemented login, registration, and settings

Built basic REST API

Menu & ordering prototype

Added dark mode

Initial RoomDB structure

Final POE – Completed Features

✔ Fully working Render API
✔ Firebase Auth integrated
✔ Biometric login
✔ Push notifications (FCM)
✔ Complete ordering flow
✔ Real-time order tracking
✔ Rewards system
✔ Mini-game
✔ Multi-language
✔ Offline mode (RoomDB)
✔ GitHub Actions automation
✔ Professional UI upgrade
✔ App runs on mobile device
✔ Video demo completed

🤖 AI Tool Transparency (Required for POE)

AI tools (ChatGPT) were used only to:

Generate documentation structures

Improve clarity of written content

Suggest UI/UX improvements

Provide explanations during debugging

Assist with diagram creation

AI was NOT used to:

Generate application code

Build layouts

Construct API logic

Develop features

All development — Kotlin code, XML layouts, API, RoomDB, Firebase integration, and UI design — was completed manually by the project team.

👥 Contributors

Dylan Munsami

Kamil Maharaj

Rushalen Delomoneys

Idris Khan

Imaad Kajee

📄 License

MIT License
Free for educational and non-commercial reuse.



