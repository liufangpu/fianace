package com.example.demo.finance.controller;

import com.example.demo.finance.dto.AssetDTO;
import com.example.demo.finance.dto.response.R;
import com.example.demo.finance.service.AssetServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
public class AssetsController {
    @Autowired
    private AssetServiceI assetService;

    @GetMapping("/{account}")
    public R<List<AssetDTO>> findByAccount(@PathVariable String account) {
        List<AssetDTO>assetDTOS= assetService.getByAccount(account);
        return R.data(assetDTOS);
    }


}
