package com.krishnamanitraders.app;

import android.os.Bundle;
import android.graphics.Color;
import android.content.Intent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    LinearLayout root, content;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<CartItem> cart = new ArrayList<>();
    TextView totalView;

    static class Product {
        String name, category, unit;
        double price;
        Product(String n,String c,String u,double p){name=n;category=c;unit=u;price=p;}
    }
    static class CartItem {
        Product p; int qty;
        CartItem(Product p,int q){this.p=p;qty=q;}
    }

    @Override public void onCreate(Bundle b){
        super.onCreate(b);
        seedProducts();
        showHome();
    }

    void seedProducts(){
        products.add(new Product("Tata TMT 6mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 8mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 10mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 12mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 16mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 20mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 25mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 28mm","TMT / Steel","piece",0));
        products.add(new Product("Tata TMT 32mm","TMT / Steel","piece",0));
        products.add(new Product("Shyam Steel 8mm","TMT / Steel","piece",0));
        products.add(new Product("Shyam Steel 10mm","TMT / Steel","piece",0));
        products.add(new Product("JSW Neo Steel 8mm","TMT / Steel","piece",0));
        products.add(new Product("JSW Neo Steel 10mm","TMT / Steel","piece",0));
        products.add(new Product("ACC Cement","Cement","bag",0));
        products.add(new Product("JSW Cement","Cement","bag",0));
        products.add(new Product("UltraTech Cement","Cement","bag",0));
        products.add(new Product("Asian Paints","Paint","unit",0));
        products.add(new Product("बालू","Construction Material","CFT",0));
        products.add(new Product("गिट्टी","Construction Material","CFT",0));
    }

    TextView tv(String s,int sp){
        TextView t=new TextView(this); t.setText(s); t.setTextSize(sp); t.setTextColor(Color.DKGRAY); t.setPadding(20,14,20,14); return t;
    }
    Button btn(String s){ Button b=new Button(this); b.setText(s); return b; }

    void base(String title){
        root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.WHITE);
        TextView bar=tv(title,22); bar.setTextColor(Color.WHITE); bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setBackgroundColor(Color.rgb(127,0,0)); root.addView(bar,new LinearLayout.LayoutParams(-1,70));
        content=new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL);
        ScrollView sv=new ScrollView(this); sv.addView(content); root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));
        setContentView(root);
    }

    void showHome(){
        base("कृष्णा मणि ट्रेडर्स");
        TextView info=tv("Near DAB Public School, Bhisa Halt, Dumra Road, Sitamarhi",16);
        info.setGravity(Gravity.CENTER); content.addView(info);
        content.addView(tv("Steel • Cement • Paint • बालू • गिट्टी",18));
        Button productsBtn=btn("🛒 Products / Order"); productsBtn.setOnClickListener(v->showProducts()); content.addView(productsBtn);
        Button login=btn("👤 Customer Login (Mobile + OTP)"); login.setOnClickListener(v->showLogin()); content.addView(login);
        Button orders=btn("📦 My Orders"); orders.setOnClickListener(v->showOrders()); content.addView(orders);
        Button admin=btn("🔐 Admin Panel"); admin.setOnClickListener(v->showAdminLogin()); content.addView(admin);
        Button call=btn("📞 Call 7979077199"); call.setOnClickListener(v->dial("7979077199")); content.addView(call);
        Button call2=btn("📞 Call 9608547799"); call2.setOnClickListener(v->dial("9608547799")); content.addView(call2);
        content.addView(tv("Address: Near DAB Public School, Bhisa Halt, Dumra Road, Sitamarhi",14));
    }

    void showLogin(){
        base("Customer Login");
        EditText phone=new EditText(this); phone.setHint("Mobile number"); phone.setInputType(2); content.addView(phone);
        Button send=btn("Send OTP"); content.addView(send);
        EditText otp=new EditText(this); otp.setHint("Enter OTP"); otp.setInputType(2); content.addView(otp);
        Button verify=btn("Verify & Login"); content.addView(verify);
        send.setOnClickListener(v->Toast.makeText(this,"Demo OTP: 123456 (real SMS gateway will be connected in production)",Toast.LENGTH_LONG).show());
        verify.setOnClickListener(v->{ if(otp.getText().toString().equals("123456")) {Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show(); showHome();} else Toast.makeText(this,"Enter demo OTP 123456",Toast.LENGTH_SHORT).show();});
    }

    void showProducts(){
        base("Products");
        for(Product p:products){
            LinearLayout row=new LinearLayout(this); row.setOrientation(LinearLayout.HORIZONTAL);
            TextView name=tv(p.name+"  |  ₹"+p.price+" / "+p.unit,15); row.addView(name,new LinearLayout.LayoutParams(0,70,1));
            Button add=btn("Add"); add.setOnClickListener(v->{cart.add(new CartItem(p,1)); Toast.makeText(this,p.name+" added",Toast.LENGTH_SHORT).show();}); row.addView(add,new LinearLayout.LayoutParams(120,70));
            content.addView(row);
        }
        Button cartBtn=btn("🛒 Cart ("+cart.size()+")"); cartBtn.setOnClickListener(v->showCart()); content.addView(cartBtn);
    }

    void showCart(){
        base("Cart / Order");
        double total=0;
        for(CartItem c:cart){ total += c.p.price*c.qty; content.addView(tv(c.p.name+" × "+c.qty+" = ₹"+(c.p.price*c.qty),16)); }
        EditText freight=new EditText(this); freight.setHint("Delivery / Freight ₹"); freight.setInputType(2); content.addView(freight);
        EditText labour=new EditText(this); labour.setHint("पल्लेदारी / Labour ₹"); labour.setInputType(2); content.addView(labour);
        totalView=tv("Subtotal: ₹"+total,18); content.addView(totalView);
        Button pay=btn("💳 Choose Payment Method"); content.addView(pay);
        pay.setOnClickListener(v->{
            double f=parseMoney(freight.getText().toString());
            double l=parseMoney(labour.getText().toString());
            showPayment(total+f+l);
        });
        Button order=btn("Place Order (COD)"); content.addView(order);
        order.setOnClickListener(v->{
            Toast.makeText(this,"Order submitted with Cash on Delivery. Admin can confirm and update status.",Toast.LENGTH_LONG).show();
        });
    }

    double parseMoney(String value){
        try { return value == null || value.trim().isEmpty() ? 0 : Double.parseDouble(value.trim()); }
        catch(Exception e){ return 0; }
    }

    void showPayment(double amount){
        base("Payment Method");
        content.addView(tv("Choose how you want to pay",20));
        content.addView(tv("Order total: ₹"+String.format(Locale.US,"%.2f",amount),18));

        Button cod=btn("💵 Cash on Delivery"); content.addView(cod);
        cod.setOnClickListener(v->{
            Toast.makeText(this,"COD selected. Place the order from the cart.",Toast.LENGTH_SHORT).show();
            showCart();
        });

        Button upi=btn("📲 Pay by UPI / QR"); content.addView(upi);
        upi.setOnClickListener(v->{
            base("UPI / QR Payment");
            content.addView(tv("Scan this QR with any UPI app",18));
            ImageView qr=new ImageView(this);
            qr.setImageResource(com.krishnamanitraders.app.R.drawable.upi_qr);
            qr.setAdjustViewBounds(true);
            qr.setPadding(30,20,30,20);
            content.addView(qr,new LinearLayout.LayoutParams(-1,520));
            content.addView(tv("UPI ID: eazypay.587035223@icici",17));
            content.addView(tv("Merchant: KRISHANAMANI TRADERS",16));

            Button copy=btn("📋 Copy UPI ID"); content.addView(copy);
            copy.setOnClickListener(x->{
                ClipboardManager cm=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("UPI ID","eazypay.587035223@icici"));
                Toast.makeText(this,"UPI ID copied",Toast.LENGTH_SHORT).show();
            });

            Button openUpi=btn("📲 Open UPI App to Pay"); content.addView(openUpi);
            openUpi.setOnClickListener(x->{
                String uri="upi://pay?pa=eazypay.587035223@icici&pn=KRISHANAMANI%20TRADERS&am="
                        +String.format(Locale.US,"%.2f",amount)+"&cu=INR";
                try { startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(uri))); }
                catch(Exception ex){ Toast.makeText(this,"No UPI app found. Scan the QR instead.",Toast.LENGTH_LONG).show(); }
            });

            content.addView(tv("After payment, send the payment reference/screenshot to the shop if requested. Payment is manually verified by admin; no payment gateway is used.",14));
            Button done=btn("✅ I Have Completed UPI Payment"); content.addView(done);
            done.setOnClickListener(x->Toast.makeText(this,"Payment marked for manual verification. Admin will verify it.",Toast.LENGTH_LONG).show());
        });

        content.addView(tv("No Razorpay/Stripe or other payment gateway is used. UPI payment is made directly to the shop's UPI ID/QR.",14));
    }

    void showOrders(){
        base("My Orders");
        content.addView(tv("No orders yet.",18));
        content.addView(tv("Order status flow: Pending → Confirmed → Processing → Out for Delivery → Delivered / Cancelled",15));
    }

    void showAdminLogin(){
        base("Admin Login");
        EditText user=new EditText(this); user.setHint("Username"); content.addView(user);
        EditText pass=new EditText(this); pass.setHint("Password"); pass.setInputType(129); content.addView(pass);
        Button login=btn("Login"); content.addView(login);
        login.setOnClickListener(v->{
            if(user.getText().toString().equals("admin") && pass.getText().toString().equals("admin123")) showAdmin();
            else Toast.makeText(this,"Demo login: admin / admin123",Toast.LENGTH_LONG).show();
        });
    }

    void showAdmin(){
        base("Admin Panel");
        content.addView(tv("Dashboard",22));
        content.addView(tv("Products, Brand/MM Prices, बालू/गिट्टी CFT Rates, Orders, Customers, Payments & Billing",16));
        Button prices=btn("💰 Manage Prices"); prices.setOnClickListener(v->showPriceEditor()); content.addView(prices);
        Button orders=btn("📦 Manage Orders"); orders.setOnClickListener(v->Toast.makeText(this,"Order management screen reserved for backend connection.",Toast.LENGTH_SHORT).show()); content.addView(orders);
        Button customers=btn("👥 Customers / Parties"); customers.setOnClickListener(v->Toast.makeText(this,"Customer/credit ledger will be connected to database.",Toast.LENGTH_SHORT).show()); content.addView(customers);
        Button bills=btn("🧾 Billing / Invoice"); bills.setOnClickListener(v->Toast.makeText(this,"Billing module: goods + freight + labour + balance.",Toast.LENGTH_SHORT).show()); content.addView(bills);
    }

    void showPriceEditor(){
        base("Price Management");
        for(Product p:products){
            EditText e=new EditText(this); e.setHint(p.name+" — ₹ per "+p.unit); e.setInputType(2); content.addView(e);
            Button save=btn("Save"); content.addView(save);
            save.setOnClickListener(v->{try{p.price=Double.parseDouble(e.getText().toString()); Toast.makeText(this,"Price updated",Toast.LENGTH_SHORT).show();}catch(Exception ex){Toast.makeText(this,"Enter price",Toast.LENGTH_SHORT).show();}});
        }
    }

    void dial(String n){ startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+n))); }
}
