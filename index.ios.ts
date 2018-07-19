

export class Order {
    constructor() {
        this.items = new Array<OrderItem>();
        this.coupons = new Array<OrderItem>();
        this.finalFee = 0.0;
    }

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

    public generateBlack(): boolean {
        throw "Not implemented";
    };

    public generate(): boolean {
        throw "Not implemented";
    }

    public downloadAndShow(downloadUrl: string): void {
        throw "Not implemented";
    }

    public cleanCacheFile(): void {
        throw "Not implemented";
    }

    public setShowType(showType: number): void {
        throw "Not implemented";
    }

    public setImageDisplayTime(imageDisplayTime: number): void {
        throw "Not implemented";
    }

    public ShowMenu(): void {
        throw "Not implemented";
    }

    public ShowMedia(): void {
        throw "Not implemented";
    }

    public ShowAdvertisement(): void {
        throw "Not implemented";
    }

    public setJsonOrder(jsonOrder: string): void {
        throw "Not implemented";
    }

    public setOrder(order: Order): void {
        throw "Not implemented";
    }

    public setImage(imgPath: string): void {
        throw "Not implemented";
    }

    public setVideo(videoPath: string): void {
        throw "Not implemented";
    }

    public showPresentation(): void {
        throw "Not implemented";
    }

    public closePresentation(): void {
        throw "Not implemented";
    }
}
