package bo.ucb.edu.ingsoft.bl;

import bo.ucb.edu.ingsoft.dao.UserDao;
import bo.ucb.edu.ingsoft.dao.TransactionDao;
import bo.ucb.edu.ingsoft.dao.AddressDao;

import bo.ucb.edu.ingsoft.dto.UserCreate;
import bo.ucb.edu.ingsoft.dto.Transaction;

import bo.ucb.edu.ingsoft.model.User;
import bo.ucb.edu.ingsoft.model.Address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBl {
    private UserDao userDao;
    private TransactionDao transactionDao;
    private AddressDao addressDao;

    @Autowired
    public UserBl(UserDao userDao, TransactionDao transactionDao, AddressDao addressDao){
        this.userDao = userDao;
        this.transactionDao = transactionDao;
        this.addressDao = addressDao;
    }

    public UserCreate createUser(UserCreate userCreate, User user, Transaction transaction, Address address){

        address.setNeighbour(userCreate.getNeighbour());
        address.setStreet(userCreate.getStreet());
        address.setNumber(userCreate.getNumber());
        address.setReference(userCreate.getReference());
        address.setTxDate(transaction.getTxDate());
        address.setTxId(transaction.getTxId());
        address.setTxHost(transaction.getTxHost());
        address.setTxUserId(transaction.getTxUserId());
        addressDao.create(address);
        Integer getLastId = transactionDao.getLastInsertId();
        address.setAddressId(getLastId);

        user.setFirstname(userCreate.getFirstname());
        user.setAddressId(address.getAddressId());
        user.setLastname(userCreate.getLastname());
        user.setPhone(userCreate.getPhone());
        user.setEmail(userCreate.getEmail());
        user.setPassword(userCreate.getPassword());
        user.setTxDate(transaction.getTxDate());
        user.setTxId(transaction.getTxId());
        user.setTxHost(transaction.getTxHost());
        user.setTxUserId(transaction.getTxUserId());
        userDao.create(user);
        user.setUserId(getLastId);

        return userCreate;
    }
}