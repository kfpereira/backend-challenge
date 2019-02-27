package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.config.exceptions.CnpjCpfInvalidException;
import com.invillia.acme.config.exceptions.CreditCardInvalidException;
import com.invillia.acme.domain.model.CreditCard;
import com.invillia.acme.domain.repositories.CreditCardRepository;
import com.invillia.acme.domain.utils.CnpjCpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;

import static com.invillia.acme.domain.utils.DateUtils.toDate;

@Service
public class CreditCardService {

    private final CreditCardRepository repository;
    private final Clock clock;

    @Autowired
    public CreditCardService(CreditCardRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public CreditCard save(String cnpjCpf, String name, String number, Date validateDate, String securityCode) throws CnpjCpfInvalidException, CreditCardInvalidException {
        CreditCard creditCard = CreditCard.builder()
                .cnpjCpf(cnpjCpf)
                .name(getName(name))
                .number(number)
                .validateDate(validateDate)
                .securityCode(securityCode)
                .build();

        validate(creditCard);

        CreditCard card = find(cnpjCpf, number, securityCode);
        return card == null? repository.saveAndFlush(creditCard) : card;
    }

    public CreditCard find(String cnpjCpf, String number, String securityCode) {
        return repository.findByCnpjCpfAndNumberAndSecurityCode(cnpjCpf, number, securityCode);
    }

    private void validate(CreditCard creditCard) throws CnpjCpfInvalidException, CreditCardInvalidException {
        if (!CnpjCpf.validate(creditCard.getCnpjCpf()))
            throw new CnpjCpfInvalidException(ErrorMessages.INVALID_CNPJ_CPF.toString());

        if (creditCard.getName() == null || creditCard.getName().isEmpty())
            throw new CreditCardInvalidException(ErrorMessages.CREDIT_CARD_INVALID.toString());

        if (creditCard.getNumber() == null || creditCard.getNumber().length() != 16)
            throw new CreditCardInvalidException(ErrorMessages.CREDIT_CARD_INVALID.toString());

        if (creditCard.getSecurityCode() == null || creditCard.getSecurityCode().length() != 3)
            throw new CreditCardInvalidException(ErrorMessages.CREDIT_CARD_INVALID.toString());

        if (creditCard.getValidateDate().before(currentDate()))
            throw new CreditCardInvalidException(ErrorMessages.CREDIT_CARD_INVALID.toString());
    }

    private Date currentDate() {
        return toDate(LocalDate.now(clock));
    }

    private String getName(String name) {
        return name == null? null : name.toUpperCase();
    }

}
