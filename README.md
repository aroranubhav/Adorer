# Adorer 💌

Adorer is an Android app that sends a heartfelt message via SMS to a registered contact every day at a user-selected time. Messages are seeded into a local Room database and each message is sent only once, ensuring a fresh, non-repeating experience every day.

---

## How It Works

1. Messages are pre-seeded into a Room database on first launch.
2. The user registers a contact number and selects a preferred time for daily delivery.
3. A WorkManager job is scheduled to trigger at the chosen time each day.
4. On trigger, the app picks an unsent message, sends it via `SmsManager`, and moves it to a separate sent messages table so it is never sent again.
5. Two `BroadcastReceiver`s track delivery status:
   - `SmsSentReceiver` confirms whether the SMS was sent successfully.
   - `SmsDeliveredReceiver` confirms whether the SMS was delivered to the recipient.

---

## Architecture

Adorer follows **single-module Clean Architecture**, with clear separation between the data, domain, and presentation layers.

```
com.maxi.adorer
├── common
├── data
│   ├── model
│   ├── repository
│   ├── source
│   └── usecase
├── domain
│   ├── model
│   ├── repository
│   ├── source
│   └── usecase
├── framework
│   ├── di
│   ├── receiver
│   ├── sms
│   └── work
├── presentation
└── QuotesApp.kt
```

---

## Tech Stack

| Category | Library |
|---|---|
| Language | Kotlin |
| UI | View Binding, Material Design, ConstraintLayout |
| Architecture | ViewModel, Flow / Lifecycle Runtime |
| Dependency Injection | Hilt |
| Local Database | Room (runtime + KTX) |
| Background Processing | WorkManager + Hilt Work |
| Preferences | DataStore |
| Serialisation | Gson |

---

## Requirements

- Android **API 26** (Oreo) or higher
- SMS permission must be granted at runtime
- A valid registered contact number

---

## Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/aroranubhav/Adorer.git
   ```
2. Open in **Android Studio**.
3. Build and run on a physical device (SMS sending requires real hardware).
4. Grant SMS permissions when prompted.
5. Register a contact number and select your preferred daily send time.

---

## Permissions

```xml
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.RECEIVE_SMS" />
```

---

## License

This project is open source and available under the [MIT License](LICENSE).
