# 🤖 QuasiProVerb Bot

Java Telegram-бот, который превращает народные пословицы в шуточные квазипословицы — с юмором про Wi-Fi, TikTok, баги и кофе ☕️

---

## 🚀 Возможности

- Команда `/proverb` — выдаёт случайную пословицу и одну шуточную интерпретацию
- Можно ввести начало пословицы — бот найдёт её и пошутит
- Загружает 200+ блоков из `proverbs.txt`
- Бот построен на Spring Boot + TelegramBots

---

## 📂 Структура

src/ ├── main/ │ ├── java/org.example.quasiproverb/ │ │ ├── bot/QuasiProverbBot.java │ │ ├── service/TxtProverbService.java │ │ └── QuasiproverbApplication.java │ └── resources/ │ ├── application.properties │ └── proverbs.txt build.gradle Dockerfile (опционально)

yaml
Copy
Edit

---

## ⚙️ Запуск локально

```bash
./gradlew build
java -jar build/libs/QuasiProVerb-0.0.1-SNAPSHOT.jar
