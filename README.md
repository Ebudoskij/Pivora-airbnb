# Accomodation-search-Java
🏠 Платформа для онлайн-оренди житла

🎯 Мета

Створити веб-додаток на Java з використанням Spring Boot та Hibernate, який дозволяє:
- Власникам нерухомості — розміщувати оголошення про житло.
- Орендарям — знаходити, бронювати та оплачувати житло онлайн.

📚 Опис сутностей

👤 User (Користувач)
- `id`: унікальний ідентифікатор
- `name`: ім’я
- `email`: електронна пошта (унікальна)
- `password`: пароль
- `role`: OWNER або TENANT

🏡 Property (Нерухомість)
- `id`, `title`, `description`, `location`, `pricePerNight`, `rooms`
- 🔗 Належить одному власнику
- 🔗 Має багато доступних дат (`Availability`)
- 🔗 Має багато бронювань (`Booking`)
- 🔗 Має багато відгуків (`Review`)

📅 Availability (Доступність)
- `id`, `property`, `availableDate`

📆 Booking (Бронювання)
- `id`, `property`, `tenant`, `startDate`, `endDate`, `totalPrice`, `status` (PENDING, CONFIRMED, CANCELLED)

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

- Реєстрація та логін для двох типів користувачів
- Створення, редагування, видалення оголошень
- Завантаження фото, управління доступністю
- Пошук житла за фільтрами
- Бронювання з вибором дат
- Онлайн-оплата (опціонально)
- Повідомлення між користувачами
- Відгуки й оцінки після проживання
- Перегляд історії бронювань

🌟 Додаткові можливості (опціонально)

- Перевірка перетинів дат при бронюванні
- Відображення середньої оцінки житла
- Скасування бронювання
- Модерація оголошень
- Email-сповіщення про активність

🛠 Технології

- Java
- Spring Boot
- Hibernate (JPA)
- PostgreSQL / H2
- Spring Security
- Maven
- (Опційно) Stripe або PayPal API

> 💡 Архітектура: `model` — `repository` — `service` — `controller`, з розділенням DTO та entity.

👨‍💻 Автори

- [Ebudoskij](https://github.com/Ebudoskij)
- [grishakotilevskiy](https://github.com/grishakotilevskiy)
- [LoFiSwanL](https://github.com/LoFiSwanL)
- [nermline](https://github.com/nermline)

