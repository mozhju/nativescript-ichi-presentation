"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var PresentationClient = (function () {
    function PresentationClient() {
        var self = this;

        this.client = new cn.ichi.android.presentation.MyPresentation();
    }
    PresentationClient.prototype.generateBlack = function () {
        return this.client.generateBlack();
    };
    PresentationClient.prototype.generate = function () {
        return this.client.generate();
    };
    PresentationClient.prototype.setJsonOrder = function (jsonOrder) {
        return this.client.setOrder(jsonOrder);
    };
    PresentationClient.prototype.setOrder = function (order) {
        var jsonOrder = JSON.stringify(order);
        return this.client.setOrder(jsonOrder);
    };
    PresentationClient.prototype.setImage = function (imgPath) {
        return this.client.setImage(imgPath);
    };
    PresentationClient.prototype.setVideo = function (videoPath) {
        return this.client.setVideo(videoPath);
    };
    PresentationClient.prototype.showPresentation = function () {
        return this.client.showPresentation();
    };
    PresentationClient.prototype.closePresentation = function () {
        return this.client.closePresentation();
    };
    return PresentationClient;
}());
exports.PresentationClient = PresentationClient;
//# sourceMappingURL=index.android.js.map