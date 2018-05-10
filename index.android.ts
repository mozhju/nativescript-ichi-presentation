
declare module cn {
    export module ichi {
        export module android {
            export class Order {
                constructor();
                public items: Array<OrderItem>;
                public coupons: Array<OrderItem>;
                public finalFee: number;
            }

            export class OrderItem {
                constructor();
                public name: string;
                public qty: number;
                public fee: number;
                public price: number;
            }

            export class MyPresentation {
                constructor();
                generate(): boolean;
                setOrder(order: Order): void;
                setImage(imgPath: string): void;
                setVideo(videoPath: string): void;
                showPresentation(): void;
                closePresentation(): void;
            }
        }
    }
}


export class PresentationClient {
    private client: cn.ichi.android.MyPresentation;

    constructor() {
        var self = this;
        this.client = new cn.ichi.android.MyPresentation();
    }

    public generate(): boolean {
        return this.client.generate();
    }

    /**
     * order: cn.ichi.android.Order
     */
    public setOrder(order): void {
        return this.client.setOrder(order);
    }

    public setImage(imgPath: string): void {
        return this.client.setImage(imgPath);
    }

    public setVideo(videoPath: string): void {
        return this.client.setVideo(videoPath);
    }

    public showPresentation(): void {
        return this.client.showPresentation();
    }

    public closePresentation(): void {
        return this.client.closePresentation();
    }
}


