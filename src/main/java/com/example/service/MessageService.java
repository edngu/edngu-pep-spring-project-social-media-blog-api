package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;


    /**
     * Constructor for MessageService
     * @param messageRepository
     * @param accountRepository
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


    /**
     * Add Message to database. Returns null of Message text is blank or greater than 254 characters or corresponding account does not exist.
     * @param message
     * @return Message
     */
    public Message addMessage(Message message) {
        if (message.getMessageText() == "") {
            return null;
        } else if (message.getMessageText().length() >= 255) {
            return null;
        } else if (accountRepository.findById(message.getPostedBy()).isEmpty()) {
            return null;
        }
        
        return messageRepository.save(message);
    }


    /**
     * Get all messages
     * @return List<Message>
     */
    public List<Message> getAllMessages() {
        return (List<Message>) messageRepository.findAll();
    }


    /**
     * Get Message with corresponding id
     * @param id
     * @return Message
     */
    public Message getMessageById(int id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        
        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }
        return null;
    }


    /**
     * Delete with corresponding id
     * @param id
     * @return int (1 if successful, 0 if failed)
     */
    public int deleteMessageById(int id) {
        Message message = getMessageById(id);
        
        if (message != null) {
            messageRepository.deleteById(id);
            return 1;
        }

        return 0;
    }


    /**
     * Update Message text. Fails if new Message text is blank or greater than 254 characters or corresponding account does not exist.
     * @param id
     * @param message
     * @return int (1 if successful, 0 if failed)
     */
    public int updateMessageById(int id, Message message) {
        if (message.getMessageText() == "") {
            return 0;
        } else if (message.getMessageText().length() >= 255) {
            return 0;
        }

        Optional<Message> optionalMessage = messageRepository.findById(id);

        if (optionalMessage.isPresent()) {
            Message updatedMessage = optionalMessage.get();
            updatedMessage.setMessageText(message.getMessageText());
            messageRepository.save(updatedMessage);
            return 1;
        }
        return 0;
    }


    /**
     * Get all Messages from Account with corresponding account_id
     * @param account_id
     * @return List<Message>
     */
    public List<Message> getAllMessagesFromAccount(int account_id) {
        return messageRepository.findAllByPostedBy(account_id);
    }
}
