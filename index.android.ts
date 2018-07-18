
declare module cn {
    export module ichi {
        export module android {
            export module presentation {
                export class MyPresentation {
                    constructor();
                    generateBlack(): boolean;
                    generate(): boolean;
                    downloadAndShow(downloadUrl: string): void;
                    setShowType(showType: number): void;
                    setImageDisplayTime(imageDisplayTime: number): void;
                    ShowMenu(): void;
                    ShowMedia(): void;
                    ShowAdvertisement(): void;
                    setOrder(jsonOrder: string): void;
                    setImage(imgPath: string): void;
                    setVideo(videoPath: string): void;
                    showPresentation(): void;
                    closePresentation(): void;
                }
            }
        }
    }
}


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
    constructor() {
        this.fee = 0.0;
    }

    public name: string;
    public qty?: number;
    public fee: number;
    public price?: number;
}

export class PresentationClient {
    private client: cn.ichi.android.presentation.MyPresentation;

    constructor() {
        var self = this;
        this.client = new cn.ichi.android.presentation.MyPresentation();
    }

    public generateBlack(): boolean {
        return this.client.generateBlack();
    };

    public generate(): boolean {
        return this.client.generate();
    }

    public downloadAndShow(downloadUrl: string): void {
        return this.client.downloadAndShow(downloadUrl);
    }

    public setShowType(showType: number): void {
        return this.client.setShowType(showType);
    }

    public setImageDisplayTime(imageDisplayTime: number): void {
        return this.client.setImageDisplayTime(imageDisplayTime);
    }

    public ShowMenu(): void {
        return this.client.ShowMenu();
    }

    public ShowMedia(): void {
        return this.client.ShowMedia();
    }

    public ShowAdvertisement(): void {
        return this.client.ShowAdvertisement();
    }

    public setJsonOrder(jsonOrder: string): void {
        return this.client.setOrder(jsonOrder);
    }

    public setOrder(order: Order): void {
        var jsonOrder = JSON.stringify(order);
        return this.client.setOrder(jsonOrder);
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


