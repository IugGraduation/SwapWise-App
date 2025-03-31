# ![Webp net-resizeimage (2)](https://github.com/user-attachments/assets/7b5f22c9-1453-4883-b441-a8e2ef1fd178) SwapWise  

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue?logo=kotlin)](https://kotlinlang.org/)  
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-2.0.0-4285F4?logo=jetpack-compose)](https://developer.android.com/jetpack/compose)  
[![Hilt](https://img.shields.io/badge/Hilt-Dependency%20Injection-FF8C42?logo=hilt)](https://dagger.dev/hilt/)  
[![MVVM](https://img.shields.io/badge/Architecture-MVVM-6A1B9A)](https://developer.android.com/jetpack/guide)  
[![GitHub Commits](https://img.shields.io/badge/Commits-250%2B-brightgreen)](https://github.com/IugGraduation/Android/commits/main)  

**An offer-based bartering app showcasing modern Android development with Jetpack Compose & Recommended app Architecture**  

---

## 📌 Overview  
A feature-rich Android trading platform demonstrating:  
- **10+ screens** built with Jetpack Compose  
- **MVVM** architecture with Hilt Dependency Injection  
- Optimized state management using **StateFlow & Coroutines**  
- Collaborative development via **GitHub Organization**  

*(Note: Project is for code demonstration only – backend services are currently offline.)*

---

## ✨ Features  
**✅ Offer-Based Trading** – Users can propose and negotiate item swaps.  
**✅ Jetpack Compose UI** – Smooth, responsive UI with custom components.  
**✅ State Management** – Powered by StateFlow and coroutines for real-time updates.  
**✅ Error Handling** – Centralized via BaseViewModel & BaseUiState.  
**✅ Debounced Search** – Optimized search input to prevent unnecessary API calls.  
**✅ Swipe Gestures & Animations** – Enhances usability and engagement.  
**✅ Auto-Login** – Secure user sessions via DataStore.  

---

## 🏗 Architecture  
**MVVM with Recommended Architecture**  
```mermaid
graph TD
  A[UI Layer] -->|Events| B[ViewModel]
  B -->|State| A
  B --> C[Domain Layer]
  C -->|Use Cases| D[Data Layer]
  D -->|Retrofit API| E[Remote Data]
  D -->|DataStore| F[Local Data]
```

---

## 🛠 Tech Stack

| **Technology**         | **Purpose**                    |
|------------------------|--------------------------------|
| Kotlin                 | Primary language               |
| Jetpack Compose        | Declarative UI framework       |
| Hilt                   | Dependency Injection           |
| Retrofit               | API communication              |
| StateFlow + Coroutines | State management               |
| DataStore              | Local data storage             |

---

## 📥 Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/SwapWise.git

2. Open in Android Studio

3. Build & run `app` module

---

## UI Preview 📸

| Home Screen |Search | Notifications |
|----------|----------|----------|
| ![home screen rounded](https://github.com/user-attachments/assets/b272f355-d3a8-461d-adc3-321d1de8d488) | ![search](https://github.com/user-attachments/assets/7d81eef9-7d1d-42ae-a9bc-04c02cf1a164) |![Notification Screen](https://github.com/user-attachments/assets/f4fe517c-3843-4991-83aa-62c8b0161fb1) |
| Post Details | Profile | Settings |
| ![Post Detiles Screen](https://github.com/user-attachments/assets/e6f7000c-fc85-42ae-a92e-24e72f878819) | ![Profile Screen](https://github.com/user-attachments/assets/8f585ad9-81e6-43fa-a3a1-2a73e8b337d5) | ![Profile Screen (1)](https://github.com/user-attachments/assets/53c02ffc-32b0-4347-9980-948552d5e32c) |

---

## 🚀 Development Highlights  
- **15% faster state management** with reusable `BaseViewModel` & `BaseUiState` for centralized state & error handling.  
- **Enhanced UX** with debounced search and custom swipe gestures for seamless interactions.  
- **Efficient navigation** using Jetpack Compose `NavController` for smooth app flow.  
- **250+ structured commits** managed via GitHub, Trello, and Figma for streamlined collaboration.

---

## 🤝 Collaboration  
### Team Tools  
- **GitHub Organization** for repository management and version control.  
- **Figma** for seamless UI/UX design handoff and collaboration.  
- **Trello** for task tracking and project management.  
- **WhatsApp** for real-time team communication and coordination.



