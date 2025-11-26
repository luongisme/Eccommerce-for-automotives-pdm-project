# E-Commerce for Automotives

A desktop application for automotive parts retail management built with **Java Swing** and **MySQL** following a clean multi-layered architecture.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Technologies](#technologies)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Troubleshooting](#troubleshooting)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)
- [License](#license)
- [Contributing](#contributing)

---

## Overview

This project is a mini e-commerce system for **automotive parts sales**.  
It supports both **Customer** and **Admin** flows with:

- Professional Swing UI
- Multi-layered architecture (UI → Service → DAO → Model → DB)
- Integration with **MySQL** via **JDBC**
- Clear separation of concerns and application of **OOP + SOLID**

**Project Goals:**

- Build an end-to-end retail management workflow (browse → cart → checkout → review)
- Practice OOP, SOLID, and common design patterns (MVC, DAO, Singleton, Factory)
- Work with a real relational database (MySQL)
- Design a maintainable and extensible Java desktop application

---

## Features

### Customer Features

**Product Browsing**
- View product list with images
- Search & filter products
- Sort by price, rating, or name
- View detailed specifications

**Shopping Cart**
- Add / remove products
- Update quantity
- Auto-calculate totals

**Checkout**
- View order summary
- Select payment method (mock)
- Order history view

**Account Management**
- Login / Registration
- Profile management
- Manage shipping addresses
- View past orders

**Product Reviews**
- Write 1–5 star reviews
- View other users’ reviews

---

### Admin Features

**Dashboard Overview**
- Revenue statistics
- Total orders & customers
- Inventory status
- Best-selling products
- Low-stock alerts

**Product Management**
- Add / Edit / Delete products
- Update stock quantity
- Manage product images
- Manage vehicle compatibility

**Order Management**
- View order list
- Update order status
- View order details

**User Management**
- View user list
- Manage roles (Admin / Customer)

---

## System Architecture

### Multi-Layered Architecture

```text
┌─────────────────────────────────────────┐
│              UI Layer (Swing)          │
│  Login, Store, Cart, Admin, Profile    │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│            Service Layer                │
│  AuthService, ProductService,           │
│  CartService, ReviewService, ...        │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│              DAO Layer                  │
│  UserDAO, ProductDAO, OrderDAO,         │
│  CartDAO, ReviewDAO, AddressDAO, ...    │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│              Model Layer                │
│  User, Product, Order, Cart, Review, .. │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│              MySQL Database             │
└─────────────────────────────────────────┘
