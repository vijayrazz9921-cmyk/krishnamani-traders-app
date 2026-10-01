# Security rules

- Do not put payment secret keys in the Android app.
- Do not commit google-services.json if your repository is public.
- Admin privileges must be enforced with Firebase custom claims/rules.
- Prices must be re-read/recalculated on the server when creating an order.
- Payment status must be verified server-side.
- Customer data must be protected with Firestore Security Rules.
