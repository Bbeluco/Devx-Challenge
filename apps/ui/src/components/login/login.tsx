import React, { useState } from "react";
import FormEmail from "../formEmail/formEmail";
import { ResponseChallengesInterface } from "../../interfaces/responseChallengesInterface";
import Mfa from "../mfa/mfa";
import Password from "../password/password";
import { LoginInterface } from "../../interfaces/loginInterface";
import sytles from "./login.module.scss";

function Login({ handlerJwtToken }: LoginInterface): React.JSX.Element {
    const [response, setResponse] = useState<ResponseChallengesInterface>({ imageURI: "", challenge: ""});
    const [email, setEmail] = useState("");

    function handlerResponse(r: ResponseChallengesInterface) {
        setResponse(r);
    }

    function handlerEmail(email: string) {
        setEmail(email);
    }

    function Component(): React.JSX.Element {
        if(response.challenge == "") {
            return <FormEmail handlerEmail={handlerEmail} handlerResponse={handlerResponse}/>
        } else if(response.challenge == "VALIDATE_QR_CODE" || response.challenge == "SEND_OTP") {
            return <Mfa email={email} handlerResponse={handlerResponse} response={response}/>
        } else if(response.challenge == "SET_PASSWORD" || response.challenge == "SEND_PASSWORD") {
            return <Password email={email} response={response} handlerResponse={handlerResponse} handlerJwttoken={handlerJwtToken}/>
        }

        return (
            <div></div>
        )
    }

    return (
        <div className={sytles.form}>
            <Component />
        </div>
    )
}

export default Login;