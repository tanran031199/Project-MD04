package com.ecommerce.controller.customer;

import com.ecommerce.model.dto.response.CartItemResponseDTO;
import com.ecommerce.model.dto.response.ProductResponseDTO;
import com.ecommerce.model.dto.response.UserLoginResponseDTO;
import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.service.design.CartService;
import com.ecommerce.model.service.design.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @GetMapping("/cart")
    public String shopCart() {
        return "customer/cart/cart";
    }

    @GetMapping("/home-add-to-cart")
    public String homeAddToCart(
            @RequestParam("productId") String productId,
            @RequestParam(name = "quantity") Integer quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        List<CartItemResponseDTO> cartItemSession = (List<CartItemResponseDTO>) session.getAttribute("cartItemList");

        if (cartItemSession != null) {
            for (CartItemResponseDTO c : cartItemSession) {
                if (c.getProduct().getProductId().equals(productId)) {
                    quantity += c.getQuantity();
                    break;
                }
            }
        }

        List<CartItemResponseDTO> cartItemList = addToCart(session, productId, quantity);

        if (cartItemList == null) {
            redirectAttributes.addFlashAttribute("forgotLogin", "Bạn cần đăng nhập để mua hàng!");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("cartItemList", cartItemList);

        return "redirect:/";
    }

    @GetMapping("/product-add-to-cart")
    public String productAddToCart(
            @RequestParam("productId") String productId,
            @RequestParam(name = "quantity") Integer quantity,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        List<CartItemResponseDTO> cartItemSession = (List<CartItemResponseDTO>) session.getAttribute("cartItemList");

        if (cartItemSession != null) {
            for (CartItemResponseDTO c : cartItemSession) {
                if (c.getProduct().getProductId().equals(productId)) {
                    quantity += c.getQuantity();
                    break;
                }
            }
        }

        List<CartItemResponseDTO> cartItemList = addToCart(session, productId, quantity);

        if (cartItemList == null) {
            redirectAttributes.addFlashAttribute("forgotLogin", "Bạn cần đăng nhập để mua hàng!");
            return "redirect:/login";
        }

        redirectAttributes.addFlashAttribute("cartItemList", cartItemList);

        return "redirect:/product";
    }

    @GetMapping("/cart-plus")
    public String cartPlus(
            @RequestParam("id") String productId,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        List<CartItemResponseDTO> cartItemSession = (List<CartItemResponseDTO>) session.getAttribute("cartItemList");
        int quantity = 0;

        if (cartItemSession != null) {
            for (CartItemResponseDTO c : cartItemSession) {
                if (c.getProduct().getProductId().equals(productId)) {
                    quantity += c.getQuantity() + 1;
                    break;
                }
            }
        }

        List<CartItemResponseDTO> cartItemList = addToCart(session, productId, quantity);

        redirectAttributes.addFlashAttribute("cartItemList", cartItemList);

        return "redirect:/cart";
    }

    @GetMapping("/cart-minus")
    public String cartMinus(
            @RequestParam("id") String productId,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        List<CartItemResponseDTO> cartItemSession = (List<CartItemResponseDTO>) session.getAttribute("cartItemList");
        int quantity = 0;

        if (cartItemSession != null) {
            for (CartItemResponseDTO c : cartItemSession) {
                if (c.getProduct().getProductId().equals(productId)) {
                    quantity = c.getQuantity() - 1;

                    if (quantity == 0) {
                        redirectAttributes.addFlashAttribute("cantMinus", "Bạn có muốn gỡ sản phẩm này khỏi giỏ hàng?");
                        redirectAttributes.addFlashAttribute("cartItemId", c.getCartItemId());
                        return "redirect:/cart";
                    }
                }
            }
        }


        List<CartItemResponseDTO> cartItemList = addToCart(session, productId, quantity);

        redirectAttributes.addFlashAttribute("cartItemList", cartItemList);

        return "redirect:/cart";
    }

    @GetMapping("/home-remove-cart-item/{id}")
    public String homeRemoveCartItem(
            @PathVariable("id") Integer cartItemId,
            HttpSession session
    ) {
        removeCartItem(session, cartItemId);
        return "redirect:/";
    }

    @GetMapping("/product-remove-cart-item/{id}")
    public String productRemoveCartItem(
            @PathVariable("id") Integer cartItemId,
            HttpSession session
    ) {
        removeCartItem(session, cartItemId);
        return "redirect:/product";
    }

    @GetMapping("/cart-remove-cart-item")
    public String cartRemoveCartItem(
            @RequestParam("id") Integer cartItemId,
            HttpSession session
    ) {
        removeCartItem(session, cartItemId);
        return "redirect:/cart";
    }

    @GetMapping("/remove-all-cart-item")
    public String removeAllCartItem(
            HttpSession session
    ) {
        UserLoginResponseDTO customer = (UserLoginResponseDTO) session.getAttribute("customer");

        if (customer != null) {
            Cart cart = cartService.getCartByUserId(customer.getUserId());
            cartService.deleteAll(cart.getCartId());

            List<CartItemResponseDTO> cartItemList = cartService.getListCartItems(cart.getCartId());

            double allTotalPrice = 0;

            for (CartItemResponseDTO c : cartItemList) {
                allTotalPrice += c.getTotalPrice();
            }

            session.setAttribute("cartItemList", cartItemList);
            session.setAttribute("allTotalPrice", allTotalPrice);
        }

        return "redirect:/cart";
    }

    @GetMapping("/cart-check-out")
    public String cartCheckOut(
            RedirectAttributes attributes,
            HttpSession session
    ) {
        UserLoginResponseDTO customer = (UserLoginResponseDTO) session.getAttribute("customer");

        if (customer == null) {
            attributes.addFlashAttribute("forgotLogin", "Bạn cần đăng nhập để mua hàng");
            return "redirect:/login";
        }

        Cart cart = cartService.getCartByUserId(customer.getUserId());
        List<CartItemResponseDTO> cartItemList = cartService.getListCartItems(cart.getCartId());

        for (CartItemResponseDTO c : cartItemList) {
            int stock = c.getProduct().getStock();
            int quantity = c.getQuantity();

            if (stock - quantity < 0) {
                attributes.addFlashAttribute("outOfStock", "Sản phẩm " + c.getProduct().getProductName() + " không đủ hàng trong kho");
                return "redirect:/cart";
            }
        }

        if (!cartService.checkout(cartItemList, cart.getCartId())) {
            attributes.addFlashAttribute("checkoutFail", "Mua hàng không thành công");
        }
        ;

        List<CartItemResponseDTO> newCartItemList = new ArrayList<>();

        attributes.addFlashAttribute("checkOutSuccess", "Mua hàng thành công");
        session.setAttribute("cartItemList", newCartItemList);
        session.setAttribute("allTotalPrice", 0);

        return "redirect:/cart";
    }

    private List<CartItemResponseDTO> addToCart(HttpSession session, String productId, Integer quantity) {
        UserLoginResponseDTO userLoginDTO = (UserLoginResponseDTO) session.getAttribute("customer");
        List<CartItemResponseDTO> cartItemList;

        if (userLoginDTO == null) {
            return null;
        }

        Cart cart = cartService.getCartByUserId(userLoginDTO.getUserId());

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userLoginDTO.getUserId());
            cartService.create(cart);
        }


        cart = cartService.getCartByUserId(userLoginDTO.getUserId());
        ProductResponseDTO productResponseDTO = productService.findOne(productId);

        cartService.addToCart(cart.getCartId(), productResponseDTO, quantity);
        cartItemList = cartService.getListCartItems(cart.getCartId());

        double allTotalPrice = 0;

        for (CartItemResponseDTO c : cartItemList) {
            allTotalPrice += c.getTotalPrice();
        }

        session.setAttribute("cartItemList", cartItemList);
        session.setAttribute("allTotalPrice", allTotalPrice);

        return cartItemList;
    }

    private void removeCartItem(HttpSession session, Integer cartItemId) {
        UserLoginResponseDTO customer = (UserLoginResponseDTO) session.getAttribute("customer");
        Cart cart = cartService.getCartByUserId(customer.getUserId());

        cartService.delete(cartItemId);

        List<CartItemResponseDTO> cartItemList = cartService.getListCartItems(cart.getCartId());

        double allTotalPrice = 0;

        for (CartItemResponseDTO c : cartItemList) {
            allTotalPrice += c.getTotalPrice();
        }

        session.setAttribute("cartItemList", cartItemList);
        session.setAttribute("allTotalPrice", allTotalPrice);
    }
}
