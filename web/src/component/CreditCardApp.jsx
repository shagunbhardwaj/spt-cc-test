import  React, { Component } from 'react';
import ListCreditCardsComponent from "./ListCreditCardsComponent";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'

class CreditCardApp extends Component {
    render() {
        return (
            <Router>
                <>
                    <Switch>
                        <Route path="/" exact component={ListCreditCardsComponent} />
                        <Route path="/creditcards" exact component={ListCreditCardsComponent} />
                    </Switch>
                </>
            </Router>
        )
    }
}

export default CreditCardApp