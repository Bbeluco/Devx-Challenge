import React, { useState } from "react";
import FormEmail from "../formEmail/formEmail";
import { ResponseChallengesInterface } from "../../interfaces/responseChallengesInterface";
import Mfa from "../mfa/mfa";
import Password from "../password/password";

function Login(): React.JSX.Element {
    const [response, setResponse] = useState<ResponseChallengesInterface>({ imageURI: "", challenge: ""});
    const [email, setEmail] = useState("");
    const [jwtToken, setJwtToken] = useState("");

    function handlerResponse(r: ResponseChallengesInterface) {
        setResponse(r);
    }

    function handlerEmail(email: string) {
        setEmail(email);
    }

    function handlerJwtToken(token: string) {
        setJwtToken(token);
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
        <div>
            <Component />
        </div>
    )
}

export default Login;