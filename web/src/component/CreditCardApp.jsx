import  React, { Component } from 'react';
import ListCreditCardsComponent from "./ListCreditCardsComponent";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

class CreditCardApp extends Component {
    render() {
        return (
            <Router>
                <>
                    <ListCreditCardsComponent/>
                </>
            </Router>
        )
    }
}

export default CreditCardApp