package com.example.lakshminarayanabr.ilovezappos;

import java.util.List;

/**
 * Created by lakshminarayanabr on 2/5/17.
 */

public class Product {
    String brandName;
    String thumbnailImageUrl;
    String productId;
    String originalPrice;
    String styleId;
    String colorId;
    String price;
    String percentOff;
    String productUrl;
    String productName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getThumbnailImageURL() {
        return thumbnailImageUrl;
    }

    public void setThumbnailImageURL(String thumbnailImageURL) {
        this.thumbnailImageUrl = thumbnailImageURL;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrginalPrice() {
        return originalPrice;
    }

    public void setOrginalPrice(String orginalPrice) {
        this.originalPrice = orginalPrice;
    }

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPercentOff() {

        return percentOff;



    }

    public void setPercentOff(String percentOff) {
        this.percentOff = percentOff;
    }

    public String getProductURL() {
        return productUrl;
    }

    public void setProductURL(String productURL) {
        this.productUrl = productURL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (brandName != null ? !brandName.equals(product.brandName) : product.brandName != null)
            return false;
        if (!productId.equals(product.productId)) return false;
        return productName != null ? productName.equals(product.productName) : product.productName == null;

    }

    @Override
    public int hashCode() {
        int result = brandName != null ? brandName.hashCode() : 0;
        result = 31 * result + productId.hashCode();
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        return result;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "brandName='" + brandName + '\'' +
                ", thumbnailImageURL='" + thumbnailImageUrl + '\'' +
                ", productId='" + productId + '\'' +
                ", orginalPrice='" + originalPrice + '\'' +
                ", styleId='" + styleId + '\'' +
                ", colorId='" + colorId + '\'' +
                ", price='" + price + '\'' +
                ", percentOff='" + percentOff + '\'' +
                ", productURL='" + productUrl + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
