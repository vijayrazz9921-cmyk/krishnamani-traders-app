# Krishnamani Traders — Production Setup Pack

Business: Krishnamani Traders
Address: Near DAB Public School, Bhisa Halt, Dumra Road, Sitamarhi
Phone: 7979077199 / 9608547799

This pack contains:
- Android starter application
- Firebase Authentication/Firestore/Storage dependencies
- Firestore and Storage security rules
- Data model for products, customers, orders and credit ledger
- Cloud Functions skeleton for secure order creation/status updates
- GitHub Actions cloud APK build
- Mobile setup guide
- Supplied shop photos
- Security notes

Real SMS OTP and real payments require accounts/configuration owned by the shop.
Never put payment secret keys in the APK.

## Payment simplified
No online payment gateway is included. Orders support Cash on Delivery and optional manual UPI/QR payment. Admin verifies manual payments.

## Shop UPI payment (manual)
- UPI ID: `eazypay.587035223@icici`
- QR image is bundled in the Android app as `upi_qr.jpg`.
- Customers can scan the QR, copy the UPI ID, or open a UPI app with the order amount prefilled.
- The app does not store or display the shop bank account number/IFSC. Manual UPI payments are verified by the admin.
