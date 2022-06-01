package io.swagger.api.controller;

import io.swagger.api.model.DTO.AccountDTO;
import io.swagger.api.model.Entity.Account;
import io.swagger.api.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountsApiControllerTest extends ControllerTest{

    List<Account> accounts;
    Account account1;
    Account account2;

    AccountDTO accountDTO;

    @MockBean
    private AccountService accountService;

    @Before
    public void setUp() {
        accounts = new ArrayList<>();

        account1 = new Account(Account.AccountTypeEnum.CURRENT, user1, -500.00);
        account1.setIBAN("NL89INHO0749725281");
        account1.setBalance(200.00);
        accounts.add(account1);

        account2 = new Account(Account.AccountTypeEnum.SAVINGS, user1, 0.00);
        account2.setIBAN("NL37RABO8946051078");
        account2.setBalance(1000.0);
        accounts.add(account2);

        accountDTO = new AccountDTO();
        accountDTO.setAccountTypeEnum(account1.getAccountType());
        accountDTO.setAbsoluteLimit(account1.getAbsoluteLimit());
    }

    @Test
    public void getAllAccountsShouldReturnJsonArray() throws Exception {

        given(accountService.getAllAccounts()).willReturn(accounts);

        mockMvc.perform(get("/accounts").contentType("application/json").header("Authorization", "Bearer " + getEmployeeToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(accounts.size())))
                .andExpect(jsonPath("$[0].iban").value("NL89INHO0749725281"));


    }
}