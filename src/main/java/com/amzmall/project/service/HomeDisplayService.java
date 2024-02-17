package com.amzmall.project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.amzmall.project.controller.dto.home.HomeDisplayDTO;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HomeDisplayService {

    public HomeDisplayDTO displayHome() {
    }
}
