package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    
    /**
     * Constructor for SocialMediaController
     * @param accountService
     * @param messageService
     */
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }


    /**
     * Register a new Account. Fails to register if Account username already exists or fails to meet other conditions.
     * @param account
     * @return ResponseEntity<Account>
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        if (accountService.getAccountByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        Account registeredAccount = accountService.addAccount(account);
        
        if (registeredAccount==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
    }


    /**
     * Attempt login into an Account.
     * @param account
     * @return ResponseEntity<Account>
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account fetchedAccount = accountService.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());

        if (fetchedAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(fetchedAccount);
    }


    /**
     * Create and add a new Message.
     * @param message
     * @return ResponseEntity<Message>
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message addedMessage = messageService.addMessage(message);

        if (addedMessage==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(addedMessage);
    }


    /**
     * Get all Messages
     * @return ResponseEntity<List<Message>>
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages());
    }


    /**
     * Get Message with corresponding message_id
     * @param message_id
     * @return ResponseEntity<Message>
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageByID(@PathVariable int message_id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessageById(message_id));
    }


    /**
     * Delete Message with corresponding message_id.
     * @param message_id
     * @return ResponseEntity<Integer>
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        int result = messageService.deleteMessageById(message_id);
        
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    /**
     * Update Message text with corresponding message_id
     * @param message_id
     * @param message
     * @return ResponseEntity<Integer>
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        int result = messageService.updateMessageById(message_id, message);

        if (result > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    /**
     * Get all Messages from Account with corresponding account_id
     * @param account_id
     * @return ResponseEntity<List<Message>>
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromUser(@PathVariable int account_id) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessagesFromAccount(account_id));
    }
}
