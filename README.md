🩺 DocConnect – Doctor Appointment Booking App

A mobile application for booking doctor appointments and managing medical records, built using Android (Kotlin) and powered by Firebase.
The app follows MVVM architecture to ensure clean code, scalability, and maintainability.

📌 Features

🔑 Authentication – Secure login & signup using Firebase

📅 Appointment Booking – Book doctor appointments

📜 Medical History – View and manage past records

📤 Prescription Uploads – Upload and store prescriptions

🔄 Realtime Updates – Firestore-based live data sync

📱 Responsive UI – Smooth and user-friendly experience

🚀 Tech Stack

Frontend: Android (Kotlin)

Architecture: MVVM

Backend / Cloud: Firebase

Authentication

Cloud Firestore

Firebase Storage

📂 Project Structure
app/
├── ui/               # Activities & Fragments
├── viewmodel/        # ViewModels
├── repository/       # Data layer
├── model/            # Data models
├── utils/            # Helper classes
├── res/              # Layouts, drawables
└── AndroidManifest.xml

⚙️ Setup Instructions

Clone the repository:

git clone https://github.com/Yogeshpant942/docconnect.git


Open the project in Android Studio

Configure Firebase:

Create a Firebase project

Register the Android app

Download google-services.json

Place it inside the app/ directory

Sync Gradle and run the app on emulator or device

🔒 Security Notes

Firebase Authentication for secure access

User-specific data isolation

Prescription uploads secured via Firebase Storage

Sensitive files excluded via .gitignore

📱 Usage Flow

User logs in

Books a doctor appointment

Uploads prescriptions

Views medical history and appointment status

🛠️ Future Enhancements

Doctor-side dashboard

Appointment reminders via notifications

Video consultation support

UI improvements with Material Design

🤝 Contributing

Contributions are welcome!

Fork the repository

Create a new branch

Commit and push changes

Open a Pull Request

📄 License

This project is licensed under the MIT License.
