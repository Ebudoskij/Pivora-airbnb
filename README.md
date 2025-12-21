# Accomodation-search-Java
🏠 Платформа для онлайн-оренди житла

🎯 Мета
Створити веб-додаток на Java з використанням Spring Boot та Hibernate, який дозволяє:
- Створювати, редагувати, видаляти оголошення
- Бронювати житло та відміняти бронь
- Залишати відгуки
- Використовувати внутрішній меседжер

📚 Опис сутностей

👤 User (Користувач)
- `id`: унікальний ідентифікатор
- `name`: ім’я
- `email`: електронна пошта (унікальна)
- `password`: пароль

🏡 Property (Нерухомість) 
- `id`, `title`, `description`, `location`, `pricePerNight`, `rooms`
- 🔗 Належить одному власнику
- 🔗 Має багато доступних дат (`Availability`)
- 🔗 Має багато бронювань (`Booking`)
- 🔗 Має багато відгуків (`Review`)
  
📆 Booking (Бронювання) - виконано
- `id`, `property`, `tenant`, `startDate`, `endDate`, `totalPrice`

💬 Message (Повідомлення)
- `id`, `fromUser`, `toUser`, `property?`, `content`, `timestamp`

🌟 Review (Відгук)
- `id`, `tenant`, `property`, `rating (1–5)`, `comment`, `date`

## 🔗 Зв’язки між сутностями

- **User ↔️ Property**: OneToMany
- **Property ↔️ Booking / Availability / Review**: OneToMany
- **User ↔️ Booking**: OneToMany
- **User ↔️ Message**: OneToMany (як відправник і отримувач)
- **User ↔️ Review**: OneToMany

✅ Функціональні вимоги

✅ - вже реалізовано
❌ - ще в розробці

- Реєстрація та логін для користувачів - ✅
- Створення, редагування, видалення оголошень - ✅
- Завантаження фото, управління доступністю - ✅
- Пошук житла за фільтрами - ✅
- Бронювання з вибором дат - ✅
- Онлайн-оплата (опціонально) - ❌
- Повідомлення між користувачами - ❌
- Відгуки й оцінки після проживання - ❌
- Перегляд історії бронювань - ✅

🌟 Додаткові можливості (опціонально)

- Перевірка перетинів дат при бронюванні - ✅
- Відображення середньої оцінки житла - ❌
- Скасування бронювання - ✅
- Модерація оголошень - ❌
- Email-сповіщення про активність - ❌

🛠 Технології

- Java
- Spring Boot
- Hibernate (JPA)
- PostgreSQL
- Spring Security
- Maven
- Stripe або PayPal API - не реалізовано

> 💡 Архітектура: `model` — `repository` — `service` — `controller` - `view`, з розділенням DTO та entity.

👨‍💻 Автори
- [Ebudoskij](https://github.com/Ebudoskij)
