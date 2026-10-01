const { onCall } = require("firebase-functions/v2/https");
const { initializeApp } = require("firebase-admin/app");
const { getFirestore, FieldValue } = require("firebase-admin/firestore");

initializeApp();
const db = getFirestore();

exports.createOrder = onCall(async (request) => {
  if (!request.auth) throw new Error("Authentication required");

  const data = request.data || {};
  if (!Array.isArray(data.items) || data.items.length === 0) {
    throw new Error("Cart is empty");
  }

  const subtotal = Number(data.subtotal || 0);
  const deliveryCharge = Number(data.deliveryCharge || 0);
  const labourCharge = Number(data.labourCharge || 0);
  const palladariCharge = Number(data.palladariCharge || 0);
  const grandTotal = subtotal + deliveryCharge + labourCharge + palladariCharge;

  const ref = db.collection("orders").doc();
  await ref.set({
    customerUid: request.auth.uid,
    items: data.items,
    subtotal,
    deliveryCharge,
    labourCharge,
    palladariCharge,
    grandTotal,
    paymentMethod: String(data.paymentMethod || "cod"),
    paymentStatus: "pending",
    orderStatus: "pending",
    deliveryAddress: String(data.deliveryAddress || ""),
    createdAt: FieldValue.serverTimestamp(),
    updatedAt: FieldValue.serverTimestamp(),
  });

  return { orderId: ref.id, grandTotal };
});

exports.updateOrderStatus = onCall(async (request) => {
  if (!request.auth || request.auth.token.admin !== true) {
    throw new Error("Admin only");
  }

  const { orderId, status } = request.data || {};
  const allowed = [
    "pending",
    "confirmed",
    "processing",
    "out_for_delivery",
    "delivered",
    "cancelled",
  ];
  if (!allowed.includes(status)) throw new Error("Invalid status");

  await db.collection("orders").doc(orderId).update({
    orderStatus: status,
    updatedAt: FieldValue.serverTimestamp(),
  });

  return { ok: true };
});

/*
PAYMENT DESIGN:
- No payment gateway.
- Default payment method: Cash on Delivery.
- Optional manual UPI/QR payment can be displayed in the app.
- Customer pays by scanning the shop's QR and informs the shop.
- Admin manually verifies and marks the order paymentStatus as "paid".
- Never put bank passwords, OTPs, or secret banking credentials in the APK.
*/
