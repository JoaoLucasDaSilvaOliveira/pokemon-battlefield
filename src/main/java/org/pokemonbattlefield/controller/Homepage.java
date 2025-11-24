package org.pokemonbattlefield.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Homepage {

    @GetMapping
    public String homepage(){
        return """
                <style>
                    * {
                        color: #000;
                        text-decoration: none;
                        font-family: arial;
                    }
                    
                    body{
                        display: flex;
                        justify-content: center;
                        align-items: center;
                        font-size: 50px;
                    }
                    
                    .main-content
                    
                    .main-content a{
                        transition: color 0.4s cubic-bezier(.25,.8,.25,1);
                    }
                    
                    .main-content a:hover {
                        color: #003ECC;
                        transition: color 0.4s cubic-bezier(.25,.8,.25,1);
                    }
                </style>
                <div class="main-content">
                    <a href= "https://whimsical.com/pokemonbattlefield-api-7TSsj4pfKyj29tYidostUY">Acesse a documentação do projeto</a>
                </div>
                """;
    }
}
