package com.ecommerce.controller.admin;

import com.ecommerce.config.Constant;
import com.ecommerce.config.Constant.UserRole;
import com.ecommerce.model.dto.request.UserLoginDTO;
import com.ecommerce.model.dto.request.UserRegisterDTO;
import com.ecommerce.model.dto.response.CartItemResponseDTO;
import com.ecommerce.model.dto.response.UserLoginResponseDTO;
import com.ecommerce.model.entity.Cart;
import com.ecommerce.model.service.design.CartService;
import com.ecommerce.model.service.design.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminAccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    /* ************************Login************************ */
    @GetMapping("/login")
    public String showLoginForm(Model model, HttpSession session) {
        UserLoginResponseDTO admin = (UserLoginResponseDTO) session.getAttribute("admin");
        UserLoginResponseDTO customer = (UserLoginResponseDTO) session.getAttribute("customer");

        if(admin != null) {
            return "redirect:/admin";
        }

        if(customer != null) {
            return "redirect:/";
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();

        model.addAttribute("user", userLoginDTO);

        return "admin/account/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserLoginDTO userLoginDTO,
                        BindingResult result,
                        HttpSession session,
                        ModelMap modelMap) {

        if (!result.hasErrors()) {
            String email = userLoginDTO.getEmail();
            String password = userLoginDTO.getPassword();
            UserLoginResponseDTO userLoginResponseDTO = userService.login(email, password);

            if (userLoginResponseDTO != null) {
                if (userLoginResponseDTO.getRole() == UserRole.ADMIN.getRole()) {
                    session.setAttribute("admin", userLoginResponseDTO);
                    return "redirect:/admin";
                } else {
                    Cart cart = cartService.getCartByUserId(userLoginResponseDTO.getUserId());
                    List<CartItemResponseDTO> cartItemList = cartService.getListCartItems(cart.getCartId());

                    double allTotalPrice = 0;

                    for (CartItemResponseDTO c : cartItemList) {
                        allTotalPrice += c.getTotalPrice();
                    }

                    session.setAttribute("customer", userLoginResponseDTO);
                    session.setAttribute("cartItemList", cartItemList);
                    session.setAttribute("allTotalPrice", allTotalPrice);
                    return "redirect:/";
                }
            } else {
                modelMap.addAttribute("notExistsUserMsg", "Tài khoản hoặc mật khẩu không chính xác");
            }
        }

        return "admin/account/login";
    }


    /* ************************Register************************ */
    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        UserLoginResponseDTO admin = (UserLoginResponseDTO) session.getAttribute("admin");
        UserLoginResponseDTO customer = (UserLoginResponseDTO) session.getAttribute("customer");

        if(admin != null) {
            return "redirect:/admin";
        }

        if(customer != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new UserRegisterDTO());
        return "admin/account/register";
    }

    @PostMapping("/register")
    public String newUser(@Valid @ModelAttribute("user") UserRegisterDTO userDTO, BindingResult result, ModelMap modelMap) {
        if (!result.hasErrors()) {
            if (userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                userDTO.setRole(UserRole.CUSTOMER.getRole());

                if (userService.register(userDTO)) {
                    return "redirect:/login";
                }
            } else {
                modelMap.addAttribute("wrongConfirmPass", "Password xác nhận không khớp");
            }
        }

        return "admin/account/register";
    }

    @GetMapping("/admin-logout")
    public String adminLogout(HttpSession session) {
        if (session.getAttribute("admin") != null) {
            session.removeAttribute("admin");
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("customer") != null) {
            session.removeAttribute("customer");
            session.removeAttribute("cartItemList");
            session.removeAttribute("allTotalPrice");
        }

        return "redirect:/login";
    }
}
