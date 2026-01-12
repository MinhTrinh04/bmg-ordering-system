# BMG Ordering System

A Spring Boot-based ordering system for Bánh Mỳ Gác restaurant.

## Project Structure

```
bmg-ordering-system/
├── backend/          # Spring Boot backend application
├── ui/
│   ├── admin/       # Admin interface
│   └── guest/       # Guest ordering interface
└── postman/         # API collections
```

## Getting Started

### Backend

Navigate to the backend folder and run:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

For more details, see [backend/README.md](backend/README.md)

### UI

The UI applications are located in the `ui/` folder:

- **Admin**: `ui/admin/` - Admin interface
- **Guest**: `ui/guest/` - Guest ordering interface

## Development

- **Backend**: All backend development should be done in the `backend/` folder
- **UI**: Frontend development should be done in respective `ui/admin/` or `ui/guest/` folders
- **API Testing**: Postman collections are available in the `postman/` folder
