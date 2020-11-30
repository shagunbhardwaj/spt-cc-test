import axios from 'axios'

const CREDITCARD_API_URL = 'http://localhost:8080/creditcards'

class CreditCardDataService {

    createCreditCard(creditCard) {
        console.log("Calling api to create card ... ")
        return axios.post(`${CREDITCARD_API_URL}`, creditCard);
    }

    retrieveAllCreditCards() {
        return axios.get(`${CREDITCARD_API_URL}`);
    }

}

export default new CreditCardDataService()
