# Myanmar Dictionary

An offline Myanmar language dictionary application for Android that provides definitions and explanations for Myanmar language words.

## Table of Contents
- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Developer](#developer)
- [License](#license)

## Features

- 🔍 **Offline Dictionary**: Extensive word database that works without an internet connection
- 🔎 **Search Functionality**: Quick word lookup with real-time search
- ❤️ **Favorites Management**: Save your favorite words for quick access
- 🗣️ **Text-to-Speech**: Listen to word pronunciations
- 🌙 **Dark/Light Theme**: Supports both light and dark themes
- 📱 **Material Design**: Clean and intuitive user interface following Material Design guidelines
- 🚀 **Fast Performance**: Optimized for quick searches and smooth navigation

## Screenshots

| Home Screen | Search Results | Word Details | Favorites |
|-------------|----------------|--------------|-----------|
| ![Home Screen](screenshots/home.png) | ![Search Results](screenshots/search.png) | ![Word Details](screenshots/detail.png) | ![Favorites](screenshots/favorites.png) |

## Installation

### Prerequisites
- Android 5.0 (API level 21) or higher
- Android Studio for development

### For Users
1. Download the latest APK from the [Releases](https://github.com/SaingHmineTun/MyanmarDictionary/releases) section
2. Enable "Install from Unknown Sources" in your device settings
3. Install the APK file

### For Developers
1. Clone the repository:
   ```bash
   git clone https://github.com/SaingHmineTun/MyanmarDictionary.git
   ```
2. Open the project in Android Studio
3. Build and run the application

## Usage

1. **Search for Words**: Use the search bar at the top of the home screen to look up Myanmar words
2. **View Word Details**: Tap on any word to see its detailed definition
3. **Listen to Pronunciation**: Tap the speaker icon to hear the word pronunciation
4. **Save Favorites**: Tap the heart icon to add/remove words from your favorites
5. **Browse Favorites**: Switch to the Favorites tab to view saved words
6. **Keyword Navigation**: Tap on keywords or synonyms in word details to navigate to related words

## Technology Stack

- **Language**: Java
- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 36 (Android 15)
- **Architecture**: MVVM with Repository pattern
- **Database**: Room Persistence Library
- **UI Framework**: Android Jetpack Components
- **Dependencies**:
  - AndroidX AppCompat
  - Material Components
  - Room Database
  - View Binding

## Architecture

The application follows a clean architecture pattern with the following components:

### Data Layer
- **Room Database**: Local storage for dictionary and favorite words
- **DAOs**: Data Access Objects for database operations
- **Entities**: Data models representing dictionary and favorite entries

### Presentation Layer
- **Activities**: 
  - `MainActivity`: Main container with bottom navigation
  - `DictActivity`: Word detail view
  - `AboutActivity`: Developer and app information
- **Fragments**:
  - `HomeFragment`: Search and dictionary listing
  - `FavoriteFragment`: Favorite words listing
- **Adapters**: RecyclerView adapters for list displays

### Key Components
- **View Binding**: Type-safe view access
- **Text-to-Speech**: Audio pronunciation
- **Material Design**: Modern UI components

## Developer

### Sai Saing Hmine Tun (Sai Mao)

A passionate Android developer with expertise in creating user-friendly mobile applications. With a strong foundation in Java and Android development, Sai has focused on creating applications that serve the Myanmar community with useful tools and resources.

### Contact Information

- 📧 **Email**: [saimao.muse@gmail.com](mailto:saimao.muse@gmail.com)
- 💼 **LinkedIn**: [Sai Saing Hmine Tun](https://www.linkedin.com/in/sai-saing-hmine-tun-08b67114b/)
- 🌐 **WordPress**: [itsaimao.wordpress.com](https://itsaimao.wordpress.com/)
- 📘 **Facebook**: [Saing Hmine Tun](https://www.facebook.com/saing.hmine.tun/)
- 🐱 **GitHub**: [SaingHmineTun](https://github.com/SaingHmineTun)

## License

```
Copyright 2025 Sai Saing Hmine Tun

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```