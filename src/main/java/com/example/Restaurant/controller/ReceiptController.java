package com.example.Restaurant.controller;

import com.example.Restaurant.repository.MenuRepository;
import com.example.Restaurant.repository.ReceiptRepository;
import com.example.Restaurant.entity.Menu;
import com.example.Restaurant.entity.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@Controller
public class ReceiptController {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping ( "/receipt-create" )
    public String showReceiptForm(Receipt receipt, Model model) {
        model.addAttribute("receipt", receipt);
        return "receipt-create";
    }

    @PostMapping ( "/receipt-create" )
    @ResponseBody
    public String addProducts(@Valid Receipt receipt, BindingResult bindingResult, Long menuId,
                              Long orderId) {

        double total = 0;
        try {
            if (bindingResult.hasErrors()) {
                return "receipt-list";
            }

            List<Menu> elements = menuRepository.findByMenuId(menuId);
            for (int i = 0; i < elements.size(); i++) {
                if (Objects.equals(elements.get(i).getId(), menuId)) {
                    double totalSum = receipt.getQuantity() * elements.get(i).getPrice();
                    receipt.setTotalAmount(totalSum);
                    receiptRepository.save(receipt);
                }
            }

            List<Receipt> receipts = receiptRepository.findByOrderId(orderId);
            for (int i = 0; i < receipts.size(); i++) {
                total += receipts.get(i).getTotalAmount();
            }
            receipt.setId(orderId);
            receiptRepository.save(receipt);
            return String.format("The total amount of order %d is %.2f.", orderId, total);
        } catch (Exception e) {

            return String.format("The total amount of order %d is %.2f.", orderId, total);
        }
    }

    @GetMapping ( "/receipt-edit/{id}" )
    public String receiptEdit(@PathVariable long id, Model model) {
        Receipt product = receiptRepository.getById(id);
        model.addAttribute("receipt", product);
        return "receipt-edit";
    }

    @PostMapping ( "/receipt-update/{id}" )
    @ResponseBody
    public String receiptUpdate(@PathVariable long id, Receipt receipt) {

        receipt.setId(id);
        receiptRepository.save(receipt);

        return "redirect:/receipt-list";

    }

    @PostMapping ( "/receipt-delete/{id}" )
    public String receiptDelete(@PathVariable long id) {
        Receipt receipt = receiptRepository.getById(id);
        receiptRepository.delete(receipt);
        return "redirect:/receipt-list";
    }

    @GetMapping ( "/receipt-list" )
    public String allReceipts(Model model) {
        List<Receipt> allReceipts = receiptRepository.findAll();
        model.addAttribute("receipt", allReceipts);
        return "redirect:/receipt-list";
    }

    @PostMapping ( "/summary-receipt/{orderId}" )
    public String summaryReceipt(@PathVariable long orderId, Model model) {
        List<Receipt> receipt = receiptRepository.findByOrderId(orderId);
        model.addAttribute("receipt", receipt);
        return "receipt-list";
    }
}
