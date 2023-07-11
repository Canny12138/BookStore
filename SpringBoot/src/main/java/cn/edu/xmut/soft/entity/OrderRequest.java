package cn.edu.xmut.soft.entity;

import cn.edu.xmut.soft.entity.Cart;
import java.util.List;

public class OrderRequest {
    private String userId;
    private String addressId;
    private List<Cart> cartList;

    public OrderRequest() {
    }

    public OrderRequest(String userId, String addressId, List<Cart> cartList) {
        this.userId = userId;
        this.addressId = addressId;
        this.cartList = cartList;
    }

    // getter和setter方法
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}
