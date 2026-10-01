# Krishnamani Traders — Firestore data model

## products/{productId}
- name: string
- category: steel | cement | paint | sand | aggregate
- brand: string
- sizeMm: number|null
- unit: piece | bag | litre | CFT | other
- price: number
- active: boolean
- imageUrl: string|null
- updatedAt: server timestamp

## customers/{uid}
- name
- phone
- address
- partyType: retail | party
- creditLimit: number
- outstanding: number
- createdAt

## orders/{orderId}
- customerUid
- items: [{productId, name, brand, sizeMm, unit, qty, unitPrice, lineTotal}]
- subtotal
- deliveryCharge
- labourCharge
- palladariCharge
- grandTotal
- paymentStatus: pending | paid | credit
- orderStatus: pending | confirmed | processing | out_for_delivery | delivered | cancelled
- deliveryAddress
- createdAt
- updatedAt

## ledger/{entryId}
- customerUid
- orderId
- type: debit | credit | payment
- amount
- note
- createdAt

## settings/shop
- name: "Krishnamani Traders"
- address: "Near DAB Public School, Bhisa Halt, Dumra Road, Sitamarhi"
- phone1: "7979077199"
- phone2: "9608547799"
