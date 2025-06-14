package com.nasipa.tictactoewebsocket.controller;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/")
public class TicTacToeController {


    @GetMapping
    public ModelAndView index() {
        return ticTacToe();
    }

    
    @RequestMapping("/index")
    public ModelAndView ticTacToe() {
        ModelAndView modelAndView = new ModelAndView("index");
        String[][] board = new String[3][3];
        Arrays.stream(board).forEach(row -> Arrays.fill(row, " "));
        modelAndView.addObject("board", board);
        return modelAndView;
    }
}
