package com.amzmall.project.controller.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.amzmall.project.controller.cart.CartModelAttributeKeys;
import com.amzmall.project.controller.dto.SearchResultDTO;
import com.amzmall.project.domain.customer.CustomerDetail;
import com.amzmall.project.service.cart.CartService;
import com.amzmall.project.service.search.SearchService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final CartService cartService;
    private final static int ITEM_PER_ROW = 3;

    @PostMapping(value = "/search")
    public String search(@AuthenticationPrincipal CustomerDetail customerDetail, @RequestParam String keyword, Model model) {
        log.info("Search Keyword, {}", keyword);
        List<SearchResultDTO> searchResults = this.searchService.search(keyword);

        log.info(">>> Search Result, {}", searchResults);
        List<List<SearchResultDTO>> searchResultPartition = ListUtils.partition(searchResults, ITEM_PER_ROW);

        if (Objects.nonNull(customerDetail)) {
            int countCartProduct = cartService.countCartProduct(customerDetail.getCustomer().getCustomerId());
            model.addAttribute(CartModelAttributeKeys.CART_ITEM_COUNT_KEY, countCartProduct);
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("searchResultPartition", searchResultPartition);

        return "/search/search-results";
    }
}
