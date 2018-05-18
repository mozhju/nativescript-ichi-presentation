

export class Order {
    constructor();

    public items: Array<OrderItem>;
    public coupons: Array<OrderItem>;
    public finalFee: number;
}

export class OrderItem {
    public name: string;
    public qty?: number;
    public fee: number;
    public price?: number;
}

export class PresentationClient {
    private client;

    constructor();

    public generateBlack(): boolean;

    public generate(): boolean;

    public setJsonOrder(jsonOrder: string): void;

    public setOrder(order: Order): void;

    public setImage(imgPath: string): void;

    public setVideo(videoPath: string): void;

    public showPresentation(): void;

    public closePresentation(): void;
}


