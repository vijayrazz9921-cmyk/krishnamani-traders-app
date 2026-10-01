# कृष्णा मणि ट्रेडर्स — मोबाइल से APK बनाने का तरीका

## सबसे आसान तरीका: GitHub Actions से Cloud Build

आपके मोबाइल में Android Studio जरूरी नहीं है।

### चरण 1 — GitHub account
1. GitHub पर account बनाइए/लॉगिन कीजिए।
2. नया repository बनाइए: `krishnamani-traders`.
3. इस ZIP को extract करके पूरा project repository में upload कीजिए।

### चरण 2 — APK build
Project में पहले से `.github/workflows/build-apk.yml` है।
GitHub में:
Actions → Build Krishnamani Traders APK → Run workflow.

Build पूरा होने पर:
Actions → उस run को खोलें → Artifacts → `Krishnamani-Traders-debug-apk`
से APK डाउनलोड कर सकते हैं।

## असली OTP

Firebase Authentication में Phone provider चालू करना होगा।
Firebase Android phone authentication SMS OTP भेजकर sign-in कर सकती है।
Production में app verification और SMS region policy configure करनी होती है।

Firebase console में:
Authentication → Sign-in method → Phone → Enable.

फिर Firebase project में Android app register करके उसका `google-services.json`
डाउनलोड करके `app/` folder में रखें।

## असली Customer/Party database

Firestore में products, customers, orders और ledger collections बनेंगी।
Admin को custom claim `admin=true` देना चाहिए।
Customer को केवल अपने customer/order data तक access मिलेगा।

## Payment — आसान तरीका

Payment gateway नहीं रखा गया है।

ऐप में दो विकल्प रहेंगे:

1. **Cash on Delivery**
2. **Manual UPI / QR**
   - Admin अपने UPI ID को सेट करेगा।
   - Admin अपना bank/UPI QR image लगाएगा।
   - Customer ऐप में QR देखकर scan करके payment कर सकता है।
   - Customer payment reference/सूचना भेज सकता है।
   - Admin payment verify करके order को `paid` mark करेगा।

**महत्वपूर्ण:** बैंक account password, UPI PIN, OTP या कोई secret credential APK में नहीं रखना है।
सिर्फ public UPI ID और QR code दिखाया जा सकता है।

## Billing

Invoice में:
- Customer/Party
- Items
- Brand + MM/variant
- Quantity
- Unit price
- Subtotal
- Delivery/Freight
- Labour/Palladari
- Other charge/discount
- Grand total
- Paid
- Outstanding/credit

रखा जाएगा।

## Production से पहले

1. Firebase project बनाइए।
2. Phone OTP enable करें।
3. `google-services.json` जोड़ें।
4. Firestore rules deploy करें।
5. Admin user बनाकर admin claim दें।
6. Payment gateway merchant account बनाइए।
7. Server-side payment verification credentials जोड़ें।
8. Real prices और products भरें।
9. Test orders और test payments करें।
10. उसके बाद signed release APK बनाकर distribute करें।

### ध्यान दें
पहले से दिए गए demo APK/project में OTP/payment placeholders थे।
यह pack production integration के लिए structure देता है, लेकिन आपके Firebase/payment
accounts की credentials के बिना कोई भी assistant असली SMS या payment processing को
आपकी ओर से activate नहीं कर सकता।


### UPI/QR payment already added
- UPI ID: `eazypay.587035223@icici`
- QR image is included in the app.
- Customer can scan QR, copy UPI ID, or open a UPI app with amount prefilled.
- Bank account number/IFSC are not used in the app; admin manually verifies the payment.
