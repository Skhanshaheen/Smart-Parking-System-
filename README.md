# 🚗 Smart Parking Reservation System

A high-performance, full-stack web application for real-time parking management. Built with a Java Spring Boot backend and deployed using modern DevOps practices (Docker & Cloud MicroVMs).

**🌐 [View Live Demo](different-nedda-none12334-a145be5e.koyeb.app/)**

---

## 🚀 Key Features

- **Interactive Parking Grid:** Real-time visualization of available and occupied spots.
- **Secure Reservations:** Users can book spots using a unique ID and a personalized PIN.
- **Instant Cancellation:** Secure deletion of bookings verified via PIN to prevent unauthorized changes.
- **Responsive Design:** Optimized for mobile and desktop users (crucial for on-the-go parking).
- **RESTful API:** Clean, decoupled architecture with a structured API for easy scalability.

---

## 🛠 Tech Stack

### Backend
- **Java 17** with **Spring Boot 3**
- **Maven** for dependency management
- **Spring Web** for RESTful API development

### Frontend
- **HTML5 / CSS3** (Modern, grid-based UI)
- **JavaScript (ES6+)** using asynchronous `fetch` for seamless updates without page reloads.

### DevOps & Deployment
- **Docker:** Containerized for consistent environments across development and production.
- **Koyeb:** Hosted on high-performance MicroVMs in **Frankfurt (EU)** for low-latency access from the UK.
- **CI/CD:** Automated deployment pipeline via GitHub integration.

---

## 🏗 System Architecture

The application follows a standard **Controller-Service-Repository** pattern to ensure clean separation of concerns.



1. **Frontend:** Sends JSON requests to the API.
2. **Controller:** Handles routing and input validation (e.g., PIN checks).
3. **Data Layer:** Manages the state of the parking grid in-memory (optimized for fast demo performance).

---

## 🔧 Installation & Local Setup

To run this project on your local machine:

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/your-username/your-repo-name.git](https://github.com/your-username/your-repo-name.git)
   cd your-repo-name/demo
