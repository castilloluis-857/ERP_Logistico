# 📦 ERP Logística Zaragoza - v1.0

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-blue?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

Sistema de gestión empresarial (ERP) desarrollado para optimizar la logística y facturación. Este proyecto implementa una arquitectura robusta basada en **DAO (Data Access Object)** y **MVC**, garantizando la integridad de los datos mediante transacciones SQL manuales.

## 📺 Demostración Visual

### Vista Principal y Facturación
<P ALIGN="CENTER">
  <img width="33%" height="AUTO" alt="Captura de pantalla 2026-02-06 062524" src="https://github.com/user-attachments/assets/41a35465-d2a5-49c9-b1fe-8f1ec69ebce7" />
  <img width="33%" height="AUTO" alt="image" src="https://github.com/user-attachments/assets/153062b9-98af-4ddf-950e-97d6b287d591" />

  <img width="33%" height="AUTO" alt="image" src="https://github.com/user-attachments/assets/1d921d5a-5371-4da7-80f6-bd516b30b4f3" />

</P>
---

## 🚀 Funcionalidades Clave

* **Gestión de Clientes:** CRUD completo con persistencia en MySQL.
* **Control de Inventario:** Listado de productos con actualización automática de stock tras cada venta.
* **Módulo de Facturación:** * Selección dinámica mediante `ComboBox` con autocompletado.
    * Cálculo de totales en tiempo real.
    * **Transacciones Atómicas:** El sistema utiliza `conn.setAutoCommit(false)` para asegurar que la factura y el descuento de stock ocurran simultáneamente o no ocurran en absoluto (Rollback).

## 🛠️ Instalación y Configuración

1. **Base de Datos:**
   Importa el archivo `database.sql` en tu servidor MySQL:
   ```sql
    CREATE DATABASE erp_logistica;
    USE erp_logistica;

    CREATE TABLE productos (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        descripcion TEXT,
        stock INT DEFAULT 0,
        precio_venta DECIMAL(10, 2) NOT NULL
    );
    
    CREATE TABLE clientes (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nombre_fiscal VARCHAR(150) NOT NULL,
        cif_nif VARCHAR(20) UNIQUE NOT NULL,
        email VARCHAR(100),
        direccion VARCHAR(255)
    );
    
    CREATE TABLE facturas (
        id INT AUTO_INCREMENT PRIMARY KEY,
        fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        cliente_id INT,
        total DECIMAL(10, 2),
        FOREIGN KEY (cliente_id) REFERENCES clientes(id)
    );
    
    CREATE TABLE lineas_factura (
        id INT AUTO_INCREMENT PRIMARY KEY,
        factura_id INT,
        producto_id INT,
        cantidad INT,
        precio_unitario DECIMAL(10, 2),
        FOREIGN KEY (factura_id) REFERENCES facturas(id),
        FOREIGN KEY (producto_id) REFERENCES productos(id)
    );
