

export declare class Order {
    constructor();

    public items: Array<OrderItem>;
    public coupons: Array<OrderItem>;
    public finalFee: number;
}

export declare class OrderItem {
    constructor();
    
    public name: string;
    public qty?: number;
    public fee: number;
    public price?: number;
}

export declare class PresentationClient {
    private client;

    constructor();

    public generateBlack(): boolean;

    public generate(): boolean;

    public downloadAndShow(downloadUrl: string): void;

    public setMediaJsonAndShow(jsonResp: string): void;

    public cleanCacheFile(deleteAll: boolean): void;

    public setShowType(showType: number): void;

    public setImageDisplayTime(imageDisplayTime: number): void;

    public ShowMenu(): void;

    public ShowMedia(): void;

    public ShowAdvertisement(): void;

    public setJsonOrder(jsonOrder: string): void;

    public setOrder(order: Order): void;

    public setImage(imgPath: string): void;

    public setVideo(videoPath: string): void;

    public showPresentation(): void;

    public closePresentation(): void;
}


