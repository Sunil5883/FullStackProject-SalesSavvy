package com.example.demo.controllers;

import com.example.demo.entities.OrderItem;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.PaymentService;
import com.razorpay.RazorpayException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @SuppressWarnings("unused")
	@Autowired
    private UserRepository userRepository;

    /**
     * Create Razorpay Order
     * @param requestBody Map containing totalAmount and cartItems
     * @param request HttpServletRequest for authenticated user
     * @return ResponseEntity with Razorpay Order ID
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPaymentOrder(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) throws RazorpayException {
        try {
            // Fetch authenticated user
            User user = (User) request.getAttribute("authenticatedUser");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }

            // Extract totalAmount and cartItems from the request body
            BigDecimal totalAmount = new BigDecimal(requestBody.get("totalAmount").toString());
            @SuppressWarnings("unchecked")
			List<Map<String, Object>> cartItemsRaw = (List<Map<String, Object>>) requestBody.get("cartItems");

            // Convert cartItemsRaw to List<OrderItem>
            List<OrderItem> cartItems = cartItemsRaw.stream().map(item -> {
                OrderItem orderItem = new OrderItem();

                // Safe extraction with default fallbacks
                Integer productId = item.get("productId") != null ? ((Number) item.get("productId")).intValue() : 0;
                Integer quantity = item.get("quantity") != null ? ((Number) item.get("quantity")).intValue() : 1;
                BigDecimal pricePerUnit;

                if (item.get("price") instanceof Number) {
                    pricePerUnit = BigDecimal.valueOf(((Number) item.get("price")).doubleValue());
                } else {
                    pricePerUnit = BigDecimal.ZERO;
                }

                orderItem.setProductId(productId);
                orderItem.setQuantity(quantity);
                orderItem.setPricePerUnit(pricePerUnit);
                orderItem.setTotalPrice(pricePerUnit.multiply(BigDecimal.valueOf(quantity)));

                return orderItem;
            }).collect(Collectors.toList());


            // Call the payment service to create a Razorpay order
            String razorpayOrderId = paymentService.createOrder(user.getUserId(), totalAmount, cartItems);

            return ResponseEntity.ok(razorpayOrderId);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request data: " + e.getMessage());
        }
    }

    /**
     * Verify Razorpay Payment
     * @param requestBody Map containing Razorpay payment details
     * @param request HttpServletRequest for authenticated user
     * @return ResponseEntity with success or failure message
     */
    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
        try {
            // Fetch authenticated user
            User user = (User) request.getAttribute("authenticatedUser");
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
            }
            int userId=user.getUserId();
            // Extract Razorpay payment details from the request body
            String razorpayOrderId = (String) requestBody.get("razorpayOrderId");
            String razorpayPaymentId = (String) requestBody.get("razorpayPaymentId");
            String razorpaySignature = (String) requestBody.get("razorpaySignature");

            // Call the payment service to verify the payment
            boolean isVerified = paymentService.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature,userId);

            if (isVerified) {
                return ResponseEntity.ok("Payment verified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment verification failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error verifying payment: " + e.getMessage());
        }
    }
}
