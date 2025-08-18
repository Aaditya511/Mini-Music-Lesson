<h1 align="center">🎶 Mini Music Lesson</h1>

<p align="center"><b>
A demo Android app for music lessons built with Jetpack Compose, MVVM, Clean Architecture,
</b></p>

---

<h2>✨ Features</h2>

- 📱 <b>MVVM Architecture</b> – clean separation of concerns with reactive state management  
- 🏛️ <b>Clean Architecture Principles</b> – modular, testable, and scalable project structure  
- 🌐 <b>Retrofit</b> – API integration with JSON parsing  
- 🖼️ <b>Coil</b> – load lesson thumbnails efficiently  
- 💉 <b>Hilt</b> – dependency injection for better code organization  
- 🎨 <b>Material Icons Extended</b> – modern icons for UI  
- 🎬 <b>ExoPlayer</b> – video playback support for lessons  
- 🚀 <b>SplashScreen API</b> – smooth and modern app launch experience  

---

<h2>🎥 Demo Video</h2>

👉 [Watch the Demo](https://drive.google.com/file/d/1b-kwAq9FSoqKu709w6CuE67obxCIzokh/view)  

---

<h2>📂 Project Structure</h2>

```bash
app/
 ┣ core/
 ┃ ┣ utils/              # CommonApi, Constants
 ┃ ┗ network/            # ApiResponse, NetworkChecker
 ┣ data/
 ┃ ┣ model/              # Lesson models
 ┃ ┣ remote/             # API services
 ┃ ┗ retrofitObject/     # RetrofitObject
 ┣ di/                   # Hilt modules
 ┣ presentation/
 ┃ ┣ home/               # Home screen UI + ViewModel
 ┃ ┣ detail/             # Detail screen UI + ViewModel
 ┃ ┗ navigation/         # Navigation graph
 ┣ ui/theme/             # App theming (colors, typography)
