import  React, { Component } from 'react';
import CreditCardDataService from "../service/CreditCardDataService";
import { Formik, Form, Field, ErrorMessage } from 'formik';

class ListCreditCardsComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
            creditCards: []
        }
        this.refreshCreditCards = this.refreshCreditCards.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    componentDidMount() {
        this.refreshCreditCards();
    }

    refreshCreditCards() {
        CreditCardDataService.retrieveAllCreditCards()
            .then(
                response => {
                    console.log(response)
                    this.setState({creditCards: response.data})
                }
            )
    }

    onSubmit(values) {
        console.log(values);
        let creditCard = {
            cardOwnerName: values.cardOwnerName,
            creditCardNumber: values.creditCardNumber,
            accountLimit: {amount : values.accountLimit.amount, currency: '£'},
            accountBalance: {amount : 0, currency: '£'}
        }
        CreditCardDataService.createCreditCard(creditCard)
            .then(
                response => {
                    console.log(response)
                    this.refreshCreditCards()
                }
            )
    }

    validate(values) {
        let errors = {}
        if (!values.cardOwnerName || values.cardOwnerName.length < 3 || values.cardOwnerName.length > 255) {
            errors.description = 'Enter Name with length between 3 - 255 characters'
        } else if (!values.creditCardNumber || values.creditCardNumber.length < 11 || values.creditCardNumber.length > 19 ) {
            errors.description = 'Enter Credit Card number with length between 11 - 19 numbers'
        } else if ( !values.accountLimit.amount || values.accountLimit.amount < 0 ) {
            errors.description = 'Enter valid card limit with positive value only'
        }
        console.log('show errors')
        console.log(errors)
        return errors
    }

    render() {
        let { id, cardOwnerName, creditCardNumber, accountLimit} = this.state
        return (
            <div >
                <h3>Credit Card System</h3>
                <h4>Add</h4>
                <div className="container">
                    <Formik
                        initialValues={{ id, cardOwnerName,  creditCardNumber, accountLimit}}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                   <ErrorMessage name="cardOwnerName" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="creditCardNumber" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="accountLimit.amount" component="div"
                                                  className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Name</label>
                                        <Field className="form-control" type="text" name="cardOwnerName" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Card Number</label>
                                        <Field className="form-control" type="text" name="creditCardNumber" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Limit</label>
                                        <Field className="form-control" type="text" name="accountLimit.amount" />
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Add</button>
                                </Form>
                            )
                        }
                    </Formik>
                </div>
                <h3>Existing Credit Cards</h3>
                <div className="container">
                    <table className="table" border={1}>
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Card Number</th>
                            <th>Balance</th>
                            <th>Limit</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.creditCards.map(
                                creditCard =>
                                    <tr key={creditCard.id}>
                                        <td hidden={true}>{creditCard.id}</td>
                                        <td>{creditCard.cardOwnerName}</td>
                                        <td>{creditCard.creditCardNumber}</td>
                                        <td>{creditCard.accountBalance.currency} {creditCard.accountBalance.amount}</td>
                                        <td>{creditCard.accountLimit.currency} {creditCard.accountLimit.amount}</td>
                                    </tr>
                            )
                        }
                        </tbody>
                    </table>
                </div>
            </div>

        )
    }
}

export default ListCreditCardsComponent