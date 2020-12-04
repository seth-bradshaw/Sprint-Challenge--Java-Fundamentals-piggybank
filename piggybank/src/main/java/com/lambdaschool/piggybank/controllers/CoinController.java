package com.lambdaschool.piggybank.controllers;

import com.lambdaschool.piggybank.models.Coin;
import com.lambdaschool.piggybank.repositories.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CoinController
{
    @Autowired
    CoinRepository coinrepos;

    @GetMapping(value = "/total", produces = "application/json")
    public ResponseEntity<?> getTotal()
    {
        List<Coin> myList = new ArrayList<>();
        coinrepos.findAll().iterator().forEachRemaining(myList::add);

        DecimalFormat df = new DecimalFormat("$###.00");
        double total = 0;
        String output = "";

        for (Coin c : myList)
        {
            total += (c.getValue() * c.getQuantity());
            if (c.getQuantity() == 1)
            {
                output += c.getQuantity() + " " + c.getName() + "\n";
            }
            if (c.getQuantity() > 1)
            {
                output += c.getQuantity() + " " + c.getNameplural() + "\n";
            }
        }
        output += "The piggy bank holds " + df.format(total);
        System.out.println(output);

        return new ResponseEntity<>(df.format(total), HttpStatus.OK);
    }
}
