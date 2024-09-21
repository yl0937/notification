package com.sparta.notification.controller;

import com.sparta.notification.common.response.ResponseUtil;
import com.sparta.notification.common.response.SuccessResponse;
import com.sparta.notification.domain.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class notificationController {
    private final ProductService productService;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public SuccessResponse<?> sendNotification(@RequestParam Long productId, @RequestBody Integer count) {
        productService.productRestock(productId,count);
        return ResponseUtil.success();
    }

    @PostMapping("/admin/products/{productId}/notifications/re-stock")
    public SuccessResponse<?> resendNotification(@RequestParam Long productId) {
        productService.manualRestock(productId);
        return ResponseUtil.success();
    }
}
