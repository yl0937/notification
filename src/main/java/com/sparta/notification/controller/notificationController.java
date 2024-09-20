package com.sparta.notification.controller;

import com.sparta.notification.common.response.ResponseUtil;
import com.sparta.notification.common.response.SuccessResponse;
import com.sparta.notification.domain.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class notificationController {
    private final ProductService productService;

    @PostMapping("products/{productId}/notifications/re-stock")
    SuccessResponse<?> sendNotification(@RequestParam Long productId, @RequestBody Integer count) {
        productService.productRestock(productId,count);
        return ResponseUtil.success();
    }

}
