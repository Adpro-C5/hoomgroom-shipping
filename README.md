# Hoomgroom - Shipping
**Samuel Farrel Bagasputra - 2206826614 - Pemrograman Lanjut C**
<br>

**Deployment Link :** http://34.143.167.93/shipment

## Fitur :

- Membuat Shipment (C)  http://34.143.167.93/shipment/create/{orderId}
- Memfilter Shipment (R) : 
  - Get All Shipment (R) http://34.143.167.93/shipment/get-all
  - Berdasarkan ID (R) http://34.143.167.93/shipment/get/{id}
  - Berdasarkan Order ID (R) http://34.143.167.93/shipment/get-by-order-id/{orderId}
- Mengubah Status Subscription (U) :
  - Sesuai ID (U) http://34.143.167.93/shipment/update-status/{id}/{status}
  - Sesuai Order ID (U) http://34.143.167.93/shipment/update-status-order/{orderId}/{status}

## Informasi Microservice
### Design Pattern
Factory Pattern
<br>

### Software Architecture:
Microservice Architecture menggunakan database PostgreSQL dengan Supabase
<br>

### High-Level Networking:
REST API
<br>

### Asynchronous Programming:
Spring Async Annotations