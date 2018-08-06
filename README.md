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
    // client.setShowType(1);
    // client.setVideo(videoPath);

    // black screan
    // client.setShowType(0);

    // media from web
    // url response json string like: {"mediaFiles":["http://192.168.1.5/800.jpg"],"menus":["http://192.168.1.5/menu1.jpg"]} 
    // var url = "http://192.168.1.5/media/";
    // client.downloadAndShow(url);

    // var resp = "{\"mediaFiles\":[\"http://192.168.1.5/800.jpg\"],\"menus\":[\"http://192.168.1.5/menu1.jpg\"]}";
    // client.setMediaJsonAndShow(resp);

    // show "menus" by downloadAndShow/setMediaJsonAndShow
    // client.ShowMenu();

    // show "mediaFiles" by downloadAndShow/setMediaJsonAndShow
    // client.ShowMedia();

    // clean Files downloaded by downloadAndShow/setMediaJsonAndShow
    // var deleteAll = false;
    // client.cleanCacheFile(deleteAll);

    // close Presentation
    // client.closePresentation(videoPath);
}
else
{
    console.log("Presentation Client generate failed");
};



```



