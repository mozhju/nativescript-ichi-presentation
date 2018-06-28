# nativescript-ichi-presentation

Presentation for NativeScript.

## Supported platforms

- Android (any device with Android 4.4 and higher)

There is no support for iOS yet!

## Installing

```
tns plugin add nativescript-ichi-presentation
```

## Usage

Here is a TypeScript example:

```js
import {PresentationClient} from "nativescript-ichi-presentation";

// data for show
var order = {
    items: [],
    coupons: [],        
    finalFee: 0.0,
}
var orderItem = {
    name: "product",
    qty: 2,
    fee: 14,
}
var orderItem2 = {
    name: "product2",
    qty: 3,
    fee: 45,
}
var couponItem = {
    name: "coupon",
    fee: 5,
}
order.items.push(orderItem);
order.items.push(orderItem2);
order.coupons.push(couponItem);
order.finalFee = 54;


// new Presentation Client
var client = new PresentationClient();

if (client.generate())
{
    // show Presentation
    client.showPresentation();

    // set data whil showed
    client.setShowType(2);
    client.setOrder(order);

    // imgPath
    // client.setShowType(1);
    // client.setImage(imgPath);

    // videoPath
    // client.setVideo(videoPath);

    // black screan
    // client.setShowType(0);

    // close Presentation when Exit APP
    // client.closePresentation(videoPath);
}
else
{
    console.log("Presentation Client generate failed");
};



```



