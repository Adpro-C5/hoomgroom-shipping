# Hoomgroom - Shipping
**Samuel Farrel Bagasputra - 2206826614 - Pemrograman Lanjut C**
<br>

**Deployment Link :** http://34.143.167.93/shipment
<br><br>

## Fitur :

- _Membuat Shipment (C)_<br>
  http://34.143.167.93/shipment/create/{orderId}<br><br>
- _Memfilter Shipment (R)_ : 
  - Get All Shipment (R)<br>
    http://34.143.167.93/shipment/get-all
  - Berdasarkan ID (R)<br>
    http://34.143.167.93/shipment/get/{id}
  - Berdasarkan Order ID (R)<br>
    http://34.143.167.93/shipment/get-by-order-id/{orderId}<br><br>
- _Mengubah Status Subscription (U)_ :
  - Sesuai ID (U)<br>
    http://34.143.167.93/shipment/update-status/{id}/{status}
  - Sesuai Order ID (U)<br>
    http://34.143.167.93/shipment/update-status-order/{orderId}/{status}<br><br>
- _Mengubah Jenis Transportasi (U)_ :
  - Sesuai ID (U)<br>
    http://34.143.167.93/shipment/set-transportation-type/{id}/{transportationType}<br>
  - Sesuai Order ID (U)<br>
    http://34.143.167.93/shipment/{orderId}/{transportationType}<br><br>

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
Spring Async Annotations<br><br>

## Tutorial 2
### Component Diagram
<img src="images/component diagram.png"><br><br>

### Code Diagram
<img src="images/code diagram.png"><br><br>