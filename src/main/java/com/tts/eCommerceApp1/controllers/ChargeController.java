package com.tts.eCommerceApp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.tts.eCommerceApp1.Model.ChargeRequest;
import com.tts.eCommerceApp1.Model.ChargeRequest.Currency;
import com.tts.eCommerceApp1.Model.User;
import com.tts.eCommerceApp1.service.StripeService;
import com.tts.eCommerceApp1.service.UserService;

@Controller
public class ChargeController 
{
	@Autowired
    UserService userService;
	
	@Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
      throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.USD);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        User user = userService.getLoggedInUser();
        user.getCart().clear();
        userService.saveExisting(user);
        return "result";
    }
}
